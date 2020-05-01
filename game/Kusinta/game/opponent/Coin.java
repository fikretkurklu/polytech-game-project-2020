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

	protected int m_imageIndex;
	protected long m_imageElapsed;
	protected Image[] m_images;
	public Model m_model;
	int m_value;
	
	private String COIN_ICO_SPRITE = "resources/HUD/AnimatedCoin.png";

	Image m_image;
	
	int m_width, m_height;

	public Coin(Automaton automaton, int x, int y, int value, Model model) throws Exception {
		super(automaton);

		m_coord = new Coord();

		m_coord.setX(x);
		m_coord.setY(y);
		
		m_value = value;
		
		try {
			m_images = HUD.loadSprite(COIN_ICO_SPRITE, 1, 6);
			m_image = m_images[0];

			m_width = m_image.getWidth(null);
			m_height = m_image.getHeight(null);
			
			/*int size = m_width / 4;
			for (int i = 0; i< m_images.length; i++) {
				m_images[i] = m_images[i].getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
			}*/
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
		m_model.m_player.setMoney(m_model.m_player.getMoney() + m_value);
		m_model.removeCoin(this);
		
		return false;
	}

	public void paint(Graphics g) {
		int w = m_width / 4;
		int h = m_height / 4;

		g.drawImage(m_image, m_coord.X(), m_coord.Y() - h, w, h, null);
	}
	
	public void tick(long elapsed) {
		m_imageElapsed += elapsed;
		if (m_imageElapsed > 200) {
			m_imageElapsed = 0;
			m_imageIndex = (m_imageIndex + 1) % m_images.length;
			m_image = m_images[m_imageIndex];
		}
		m_automaton.step(this);
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
