package player;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import automaton.Automaton;
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

	protected Character collidingWith;

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
		
		collidingWith = null;
		
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
	public boolean turn(Direction dir) {
		if (!m_direction.toString().equals(dir.toString()))
			m_direction = dir;
		return true;
	}
	
	public boolean power(){
//		collidingWith.loseLife(m_strength);
		return false;
	}
	
	public void loseLife(int l) {
		m_life-=l;
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
		m_resistance = resistance;
	}

	public void setStrength(int strength) {
		m_strength = strength;
	}

	public int getMoney() {
		return m_money;
	}
	
	public HashMap<EquipmentManager.Stuff, Equipment> getEquipment(){
		return m_equipments;
	}
	
	public abstract void tick(long elapsed);

	public abstract void paint(Graphics gp);

	public abstract void setPressed(int keyChar, boolean b);
	
	public int getHeight() {
		return m_height;
	}
	
	public Rectangle getHitBox() {
		return hitBox;
	}
	

}
