package opponent;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;

import javax.imageio.ImageIO;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import automaton.Entity;
import game.Coord;
import game.Model;
import environnement.Element;

public class Key extends Entity {

	public static final int SIZE = (int) (1.5 * Element.SIZE);

	private static final double G = 9.81;

	Image image;
	public Model m_model;

	int m_width, m_height;

	protected boolean falling;
	long m_time;
	private int y_gravity;

	public Key(Automaton automaton, int x, int y, Model model) throws Exception {
		super(automaton);

		m_coord = new Coord();

		m_coord.setX(x);
		m_coord.setY(y);

		image = loadImage("resources/Room/dropable/Golden_Key.png");

		m_width = image.getWidth(null);
		m_height = image.getHeight(null);

		m_model = model;

		m_time = 0;
		falling = false;
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
		m_model.m_player.setKey(this);
		m_model.setKey(null);

		return false;
	}

	public void paint(Graphics g) {
		int w = m_width / 4;
		int h = m_height / 4;

		g.drawImage(image, m_coord.X(), m_coord.Y() - h, -w, h, null);
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

	public void tick(long elapsed) {
		m_automaton.step(this);

		if (!m_model.m_room.isBlocked(m_coord.X(), m_coord.Y() + 5) && elapsed < 10) {

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

	private void gravity(long t) {

		int newY = (int) ((0.5 * G * Math.pow(t, 2) * 0.0005)) + y_gravity;
		m_coord.setY(newY);
	}

}
