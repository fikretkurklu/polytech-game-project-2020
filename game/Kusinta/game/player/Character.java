package player;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import automaton.Automaton;
import automaton.Direction;
import automaton.Entity;
import environnement.Element;
import equipment.Equipment;
import equipment.EquipmentManager;
import equipment.EquipmentManager.Stuff;
import equipment.Stat.Stats;
import game.Coord;
import game.Model;
import projectile.Projectile;

public abstract class Character extends Entity {

	public Model m_model;
	protected Direction m_direction;
	protected enum CurrentStat { m_resistance, m_strength, m_attackspeed, m_maxLife, m_life };

	protected HashMap<CurrentStat, Integer> m_current_stat_map;
	
	int m_width, m_height;

	protected LinkedList<Projectile> m_projectiles;

	BufferedImage[] bI;
	protected int m_image_index;

	Rectangle hitBox;

	protected int m_money;
	HashMap<EquipmentManager.Stuff, Equipment> m_equipments;
	
	public HashMap<Stats, Integer> m_default_stat_map;

	Image imageProjectile;
	
	public Character(Automaton automaton, int x, int y, Direction dir, Model model, int maxLife, int life, int attackSpeed, int resistance, int strength) throws IOException {
		super(automaton);
		
		setStat(attackSpeed, maxLife, resistance, strength);
		setCurrentStat(attackSpeed, maxLife, resistance, strength);
		
		m_coord = new Coord(x,y);
		
		m_direction = dir;
		
		m_projectiles = new LinkedList<Projectile>();
		
		m_model = model;
		
		m_image_index = 0;
		
		m_equipments = new HashMap<>();
		
		Stuff[] stuffTable = Stuff.values();
		
		for(int i = 0; i < stuffTable.length; i++) {
			m_equipments.put(stuffTable[i], null);
		}
	}
	
	public Rectangle getHitBox() {
		return hitBox;
	}
	public abstract void reset();
	
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
		if (m_direction != dir)
			m_direction = dir;
		return true;
	}

	@Override
	public boolean gotpower() { // mort
		if (m_current_stat_map.get(CurrentStat.m_life) > 0) {
			return true;
		}
		return false;
	}

	public void setLife(int l) {
		int maxLife = m_current_stat_map.get(CurrentStat.m_maxLife);
		if (l > maxLife) {
			m_current_stat_map.put(CurrentStat.m_life, maxLife);
		} else {
			m_current_stat_map.put(CurrentStat.m_life, l);
		}
	}

	public void setResistance(int resistance) {
		m_current_stat_map.put(CurrentStat.m_resistance, resistance);
	}

	public void setStrength(int strength) {
		m_current_stat_map.put(CurrentStat.m_strength, strength);
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
	
	public Equipment addEquipment(Equipment equipment) {
		Stuff stuff = equipment.toStuff();
		Equipment res = null;

		if (m_equipments.get(stuff) != null) {
			res = m_equipments.get(stuff);
		}

		m_equipments.put(stuff, equipment);
		Stuff[] stuffTable = Stuff.values();
		m_current_stat_map.put(CurrentStat.m_attackspeed, m_default_stat_map.get(Stats.AttackSpeed));
		m_current_stat_map.put(CurrentStat.m_resistance, m_default_stat_map.get(Stats.Resistance));
		m_current_stat_map.put(CurrentStat.m_strength, m_default_stat_map.get(Stats.Strengh));
		m_current_stat_map.put(CurrentStat.m_maxLife, m_default_stat_map.get(Stats.Health));
		m_current_stat_map.put(CurrentStat.m_life, m_default_stat_map.get(Stats.Health));
		

		for (int i = 0; i < stuffTable.length; i++) {
			Equipment tmpEquipment = m_equipments.get(stuffTable[i]);
			if (tmpEquipment != null) {
				int attackSpeed = m_current_stat_map.get(CurrentStat.m_attackspeed);
				int resistance = m_current_stat_map.get(CurrentStat.m_resistance);
				int strength = m_current_stat_map.get(CurrentStat.m_strength);
				int maxlife = m_current_stat_map.get(CurrentStat.m_maxLife);
				m_current_stat_map.put(CurrentStat.m_attackspeed, attackSpeed+tmpEquipment.getModification(Stats.AttackSpeed));
				m_current_stat_map.put(CurrentStat.m_resistance, resistance+tmpEquipment.getModification(Stats.Resistance));
				m_current_stat_map.put(CurrentStat.m_strength, strength+tmpEquipment.getModification(Stats.Strengh));
				m_current_stat_map.put(CurrentStat.m_maxLife, maxlife+tmpEquipment.getModification(Stats.Health));
			}
			m_current_stat_map.put(CurrentStat.m_life, m_current_stat_map.get(CurrentStat.m_maxLife));
		}
		return res;
	}
	
	public void removeEquipment(Equipment equipment) {
		Stuff stuff = equipment.toStuff();
		m_equipments.put(stuff, null);
	}
	
	public void setStat(int attackspeed, int health, int resistance, int strength) {
		m_default_stat_map = new HashMap<>();
		m_default_stat_map.put(Stats.AttackSpeed, attackspeed);
		m_default_stat_map.put(Stats.Health, health);
		m_default_stat_map.put(Stats.Resistance, resistance);
		m_default_stat_map.put(Stats.Strengh, strength);
	}
	
	public void setCurrentStat(int attackspeed, int health, int resistance, int strength) {
		m_current_stat_map = new HashMap<>();
		int life = health;
		m_current_stat_map.put(CurrentStat.m_maxLife, health);
		m_current_stat_map.put(CurrentStat.m_life, life);
		m_current_stat_map.put(CurrentStat.m_resistance, resistance);
		m_current_stat_map.put(CurrentStat.m_strength, strength);
		m_current_stat_map.put(CurrentStat.m_attackspeed, attackspeed);
	}
	
	public void setMoney(int money) {
		m_money += money;
	}
	
	public void loadImageProjectile(String path) throws Exception {
		File imageFile = new File(path);
		if (imageFile.exists()) {
			imageProjectile = ImageIO.read(imageFile);
			double ratio = (double)imageProjectile.getHeight(null)/(double)imageProjectile.getWidth(null);
			imageProjectile = imageProjectile.getScaledInstance((int)(1.5*Element.SIZE*ratio), (int)(1.5*Element.SIZE), 0);
		} else {
			throw new Exception("Error while loading image: path = " + path);
		}
	}
	
}
