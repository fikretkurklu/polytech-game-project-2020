package player;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import automaton.Entity;
import game.Coord;
import game.Model;
import projectile.Arrow;

public abstract class Character extends Entity {

	Coord m_coord;
	public Model m_model;
	public Direction m_direction;

	int MAX_LIFE = 100;
	int MAX_RESISTANCE = 100;
	int MAX_FORCE = 200;
	int m_life;
	int m_resistance, m_strength, m_attackSpeed;

	BufferedImage[] bI;
	int m_image_index, last_image_index;

	// Sprite m_character;

	public Character(Automaton automaton, int x, int y, Direction dir, Model model) throws IOException {
		super(automaton);
		
		m_automaton = automaton;
		
		m_coord = new Coord(x,y);
		
		m_direction = dir;

		m_life = MAX_LIFE;
		m_attackSpeed = 1000;
		m_resistance = 0;
		m_strength = 0;
		m_model = model;
		
		m_image_index = 0;
		last_image_index = 0;
	}

	protected BufferedImage[] loadSprite(String filename, int nrows, int ncols) throws IOException {
		File imageFile = new File(filename);
		if (imageFile.exists()) {
			BufferedImage image = ImageIO.read(imageFile);
			int width = image.getWidth(null) / ncols;
			int height = image.getHeight(null) / nrows;

			BufferedImage[] images = new BufferedImage[nrows * ncols];
			for (int i = 0; i < nrows; i++) {
				for (int j = 0; j < ncols; j++) {
					int x = j * width;
					int y = i * height;
					images[(i * ncols) + j] = image.getSubimage(x, y, width, height);
				}
			}
			return images;
		}
		return null;
	}
	
	public Coord getCoord() {
		return m_coord;
	}
	
	@Override
	public boolean wizz(Direction dir) { // activer un levier
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean power() { // Collision et perte de vie
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pick(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean turn(Direction dir) {
		if (m_direction != dir)
			m_direction = dir;
		return true;
	}

	@Override
	public boolean get() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean explode() {
		return true;
	}

	@Override
	public boolean hit(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mydir(Direction m_dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cell(Direction direction, Category category) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closest(Category category, Direction direction) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean gotstuff() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean gotpower() { // mort
		if (m_life > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean store() {
		return true;
	}

	public void setLife(int l) {
		if (l > MAX_LIFE) {
			m_life = MAX_LIFE;
		} else {
			m_life = l;
		}
	}
	
	public void setResistance(int resistance) {
		if (resistance > MAX_RESISTANCE) {
			m_resistance = MAX_RESISTANCE;
		} else {
			m_resistance= resistance;
		}
	}
	
	public void setStrength(int strength) {
		if (strength > MAX_FORCE) {
			m_strength = MAX_FORCE;
		} else {
			m_strength = strength;
		}
	}

}
