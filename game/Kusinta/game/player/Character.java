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
import opponent.Key;
import projectile.Projectile;

public abstract class Character extends Entity {

	public Model m_model;
	protected Direction m_direction;
	public static enum CurrentStat { Resistance, Strength, Attackspeed, MaxLife, Life };

	int MAX_LIFE = 100;

	protected HashMap<CurrentStat, Integer> m_currentStatMap;
	
	int m_width, m_height;

	protected LinkedList<Projectile> m_projectiles;

	BufferedImage[] bI;
	protected int m_image_index;

	protected Character collidingWith;

	protected Rectangle hitBox;
	
	protected boolean moving;

	protected int m_money;
	HashMap<EquipmentManager.Stuff, Equipment> m_equipments;
	
	public HashMap<Stats, Integer> m_defaultStatMap;

	protected Image imageProjectile;
	protected Image[] imageProjectiles;
	
	public Key m_key;
	
	public Character(Automaton automaton, int x, int y, Direction dir, Model model, int maxLife, int life, int attackSpeed, int resistance, int strength) throws IOException {
		super(automaton);
		
		setStat(attackSpeed, maxLife, resistance, strength);
		setCurrentStat(attackSpeed, maxLife, resistance, strength);
		
		m_coord = new Coord(x,y);
		
		m_direction = dir;
		
		m_projectiles = new LinkedList<Projectile>();

		m_model = model;

		m_image_index = 0;

		collidingWith = null;

		m_equipments = new HashMap<>();

		Stuff[] stuffTable = Stuff.values();

		for (int i = 0; i < stuffTable.length; i++) {
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
		if (!m_direction.toString().equals(dir.toString()))
			m_direction = dir;
		return true;
	}

	public boolean power() {
		collidingWith.loseLife((m_currentStatMap.get(CurrentStat.Strength)));
		return false;
	}

	public void loseLife(int l) {
		m_currentStatMap.put(CurrentStat.Life, (m_currentStatMap.get(CurrentStat.Life) - l));
	}

	@Override
	public boolean gotpower() { // mort
		if (m_currentStatMap.get(CurrentStat.Life) > 0) {
			return true;
		}
		return false;
	}

	public void setLife(int l) {
		int maxLife = m_currentStatMap.get(CurrentStat.MaxLife);
		if (l > maxLife) {
			m_currentStatMap.put(CurrentStat.Life, maxLife);
		} else {
			m_currentStatMap.put(CurrentStat.Life, l);
		}
	}

	public int getMoney() {
		return m_money;
	}
	
	public int getStat(CurrentStat stat) {
		return m_currentStatMap.get(stat);
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

	public int getLife() {
		return (m_currentStatMap.get(CurrentStat.Life));
	}
	
	public Equipment addEquipment(Equipment equipment) {
		Stuff stuff = equipment.toStuff();
		Equipment res = null;

		if (m_equipments.get(stuff) != null) {
			res = m_equipments.get(stuff);
		}

		m_equipments.put(stuff, equipment);
		Stuff[] stuffTable = Stuff.values();
		m_currentStatMap.put(CurrentStat.Attackspeed, m_defaultStatMap.get(Stats.AttackSpeed));
		m_currentStatMap.put(CurrentStat.Resistance, m_defaultStatMap.get(Stats.Resistance));
		m_currentStatMap.put(CurrentStat.Strength, m_defaultStatMap.get(Stats.Strengh));
		m_currentStatMap.put(CurrentStat.MaxLife, m_defaultStatMap.get(Stats.Health));
		m_currentStatMap.put(CurrentStat.Life, m_defaultStatMap.get(Stats.Health));
		

		for (int i = 0; i < stuffTable.length; i++) {
			Equipment tmpEquipment = m_equipments.get(stuffTable[i]);
			if (tmpEquipment != null) {
				int attackSpeed = m_currentStatMap.get(CurrentStat.Attackspeed);
				int resistance = m_currentStatMap.get(CurrentStat.Resistance);
				int strength = m_currentStatMap.get(CurrentStat.Strength);
				int maxlife = m_currentStatMap.get(CurrentStat.MaxLife);
				m_currentStatMap.put(CurrentStat.Attackspeed, attackSpeed+tmpEquipment.getModification(Stats.AttackSpeed));
				m_currentStatMap.put(CurrentStat.Resistance, resistance+tmpEquipment.getModification(Stats.Resistance));
				m_currentStatMap.put(CurrentStat.Strength, strength+tmpEquipment.getModification(Stats.Strengh));
				m_currentStatMap.put(CurrentStat.MaxLife, maxlife+tmpEquipment.getModification(Stats.Health));
			}
			m_currentStatMap.put(CurrentStat.Life, m_currentStatMap.get(CurrentStat.MaxLife));
		}
		return res;
	}
	
	public void removeEquipment(Equipment equipment) {
		Stuff stuff = equipment.toStuff();
		m_equipments.put(stuff, null);
	}
	
	public void setStat(int attackspeed, int health, int resistance, int strength) {
		m_defaultStatMap = new HashMap<>();
		m_defaultStatMap.put(Stats.AttackSpeed, attackspeed);
		m_defaultStatMap.put(Stats.Health, health);
		m_defaultStatMap.put(Stats.Resistance, resistance);
		m_defaultStatMap.put(Stats.Strengh, strength);
	}
	
	public void setCurrentStat(int attackspeed, int health, int resistance, int strength) {
		m_currentStatMap = new HashMap<>();
		int life = health;
		m_currentStatMap.put(CurrentStat.MaxLife, health);
		m_currentStatMap.put(CurrentStat.Life, life);
		m_currentStatMap.put(CurrentStat.Resistance, resistance);
		m_currentStatMap.put(CurrentStat.Strength, strength);
		m_currentStatMap.put(CurrentStat.Attackspeed, attackspeed);
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

	public Image getProjectileImage() {
		return imageProjectile;
	}

	public Image[] getProjectileImages() {
		return imageProjectiles;
	}

	public void removeProjectile(Projectile projectile) {
		m_projectiles.remove(projectile);
	}

	public boolean isMoving() {
		return moving;
	}
	
	public Key getKey() {
		return m_key;
	}
	
	public void setKey(Key key) {
		m_key = key;
	}
}
