package player;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import automaton.Entity;
import equipment.Equipment;
import equipment.EquipmentManager;
import equipment.EquipmentManager.Stuff;
import game.Coord;
import game.Model;
import projectile.Projectile;

public abstract class Character extends Entity {

	public Model m_model;
	protected Direction m_direction;

	int MAX_LIFE = 100;
	protected int m_life;
	protected int m_resistance, m_strength, m_attackSpeed;
	protected int m_slowness;

	int m_width, m_height;

	protected LinkedList<Projectile> m_projectiles;

	BufferedImage[] bI;
	protected int m_image_index;

	protected Rectangle hitBox;

	protected int m_money;
	HashMap<EquipmentManager.Stuff, Equipment> m_equipments;

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
		
		m_equipments = new HashMap<>();
		
		Stuff[] stuffTable = Stuff.values();
		
		for(int i = 0; i < stuffTable.length; i++) {
			m_equipments.put(stuffTable[i], null);
		}
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

	public LinkedList<Projectile> getProjectiles() {
		return m_projectiles;
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
		m_resistance = resistance;
	}

	public void setStrength(int strength) {
		m_strength = strength;
	}

	public int getMoney() {
		return m_money;
	}
	
	public abstract void tick(long elapsed);

	public abstract void paint(Graphics gp);

	public abstract void setPressed(int keyChar, boolean b);
	

}
