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
	
	Image image;
	public Model m_model;
	
	int m_width, m_height;
	
	public Key(Automaton automaton, int x, int y, Model model) throws Exception {
		super(automaton);
		
		m_coord = new Coord();
		
		m_coord.setX(x);
		m_coord.setY(y);
		
		image = loadImage("resources/Room/dropable/Golden_Key.png");
	
		m_width = image.getWidth(null);
		m_height = image.getHeight(null);
		
		m_model = model;
	}
	
	@Override
	public boolean cell(Direction dir, Category cat) {
		Rectangle playerHitBox = m_model.m_player.getHitBox();
		int w = m_width / 4;
		int h = m_height / 4;
		
		
		if(playerHitBox.contains(m_coord.X() + (w/2) , m_coord.Y() + (h/2))) {
			System.out.println("hello");
		}
		
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

	
}
