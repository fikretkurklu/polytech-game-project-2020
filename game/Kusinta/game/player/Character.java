package player;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import automaton.Automaton;
import automaton.Direction;
import automaton.Entity;
import game.Coord;
import game.Model;
import projectile.Projectile;

public abstract class Character extends Entity {

	protected Model m_model;
	protected Direction m_direction;

	int MAX_LIFE = 100;
	int m_life;
	int m_resistance, m_strength, m_attackSpeed;
	
	protected LinkedList<Projectile> m_projectiles;

	BufferedImage[] bI;
	int m_image_index;

	// Sprite m_character;

	public Character(Automaton automaton, int x, int y, Direction dir, Model model, int maxLife, int life, int attackSpeed, int resistance, int strength) throws IOException {
		super(automaton);
		
		m_coord = new Coord(x,y);
		
		m_direction = dir;
		
		MAX_LIFE = maxLife;

		m_life = life;
		m_resistance = resistance;
		m_strength = strength;
		m_attackSpeed = attackSpeed;
		
		m_projectiles = new LinkedList<Projectile>();
		
		m_model = model;
		
		m_image_index = 0;
	}
	
	public Coord getCoord() {
		return m_coord;
	}
	
	public Direction getDirection() {
		return m_direction;
	}
	
	public Model getModel() {
		return m_model;
	}
	
	public LinkedList<Projectile> getProjectiles(){
		return m_projectiles;
	}

	@Override
	public boolean turn(Direction dir) {
		if (m_direction != dir)
			m_direction = dir;
		return true;
	}

	@Override
	public boolean gotpower() { // mort
		if (m_life > 0) {
			return true;
		}
		return false;
	}

	public void setLife(int l) {
		if (l > MAX_LIFE) {
			m_life = MAX_LIFE;
		} else {
			m_life = l;
		}
	}
	
	public void setResistance(int resistance) {
			m_resistance= resistance;
	}
	
	public void setStrength(int strength) {
			m_strength = strength;
	}

}
