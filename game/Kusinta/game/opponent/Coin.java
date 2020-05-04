package opponent;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import automaton.Entity;
import game.Coord;
import game.Model;
import hud.HUD;
import environnement.Element;

public class Coin extends Entity {

	public static final int SIZE = (int) (1.5 * Element.SIZE);

	private static final double G = 9.81;

	protected int m_imageIndex;
	protected long m_imageElapsed;
	protected Image[] m_images;

	public Model m_model;
	int m_value;

	int position, aller;

	protected boolean falling;
	long m_time;
	private int y_gravity;

	private String COIN_ICO_SPRITE = "resources/HUD/AnimatedCoin.png";

	Image m_image;

	int m_width, m_height;

	public Coin(Automaton automaton, int x, int y, int value, Model model) {
		super(automaton);

		m_coord = new Coord();

		m_coord.setX(x);
		m_coord.setY(y);

		m_value = value;

		m_time = 0;
		falling = false;

		position = 10;
		aller = -1;

		try {
			m_images = HUD.loadSprite(COIN_ICO_SPRITE, 1, 6);
			m_image = m_images[0];

			m_width = m_image.getWidth(null);
			m_height = m_image.getHeight(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		m_model = model;
	}

	@Override
	public boolean cell(Direction dir, Category cat) {
		Rectangle playerHitBox = m_model.m_player.getHitBox();
		int w = m_width / 4;
		int h = m_height / 4;

		if (playerHitBox.contains(m_coord.X() + (w / 2), m_coord.Y() - (h / 2))) {
			return true;
		}

		return false;
	}

	@Override
	public boolean wizz(Direction dir) {
		m_model.m_player.setMoney(m_value);
		m_model.removeCoin(this);

		return false;
	}

	public void paint(Graphics g) {
		int w = m_width / 4;
		int h = m_height / 4;

		if (!falling) {
			if (position >= 10) {
				aller = -1;
			} else if (position <= 0) {
				aller = 1;
			}
			position = (position + aller);
		}
		
		g.drawImage(m_image, m_coord.X(), m_coord.Y() - h + position, w, h, null);
	}

	public void tick(long elapsed) {
		m_imageElapsed += elapsed;
		if (m_imageElapsed > 200) {
			m_imageElapsed = 0;
			m_imageIndex = (m_imageIndex + 1) % m_images.length;
			m_image = m_images[m_imageIndex];
		}
		m_automaton.step(this);

		if (elapsed < 10) {
			if (!m_model.m_room.isBlocked(m_coord.X(), m_coord.Y() + 5)) {

				if (!falling) {
					y_gravity = m_coord.Y();
					m_time = 0;
				} else {
					m_time += elapsed;
				}
				falling = true;
				if (m_time >= 10)
					gravity(m_time);
			} else if (falling) {
				int topBlock = m_model.m_room.blockTop(m_coord.X(), m_coord.Y() + 6);
				m_coord.setY(topBlock - 5);
				m_time = 0;
				falling = false;
			}
		}
	}

	private void gravity(long t) {

		int newY = (int) ((0.5 * G * Math.pow(t, 2) * 0.0005)) + y_gravity;
		m_coord.setY(newY);
	}

	public Image loadImage(String path) throws Exception {
		File imageFile = new File(path);
		Image image;
		if (imageFile.exists()) {
			image = ImageIO.read(imageFile);
			image = image.getScaledInstance(SIZE, SIZE, 0);
			return image;
		} else {
			throw new Exception("Error while loading image: path = " + path);
		}
	}

}
