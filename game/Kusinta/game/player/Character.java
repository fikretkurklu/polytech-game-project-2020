package player;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import automaton.Automaton;
import automaton.Direction;
import automaton.Entity;
import automaton.Entity.Action;
import entityFactory.Factory.Type;
import equipment.Equipment;
import equipment.EquipmentManager;
import equipment.EquipmentManager.Stuff;
import equipment.Stat.Stats;
import game.Controller;
import game.Coord;
import game.Game;
import game.Model;
import projectile.Arrow;
import projectile.MagicProjectile;
import projectile.Projectile;
import projectile.Projectile.proj;
import underworld.Lure;

public abstract class Character extends Entity {

	double G = 9.81;
	double ACCELERATION_JUMP = 1.8;
	protected long m_moveElapsed;

	public static enum CurrentStat {
		Resistance, Strength, Attackspeed, MaxLife, Life
	};

	public HashMap<CurrentStat, Integer> m_currentStatMap;

	protected LinkedList<Projectile> m_projectiles;

	protected int m_money;
	HashMap<EquipmentManager.Stuff, Equipment> m_equipments;

	public HashMap<Stats, Integer> m_defaultStatMap;

	protected boolean m_key;
	protected boolean m_bossKey;

	protected boolean falling, jumping;
	protected long m_ratio_x, m_ratio_y, m_time;
	protected int y_gravity;

	protected boolean shooting;

	public Character(Automaton automaton, Coord C, Direction dir, Model model, int maxLife, int life, int attackSpeed,
			int resistance, int strength, Image[] bImages, HashMap<Action, int[]> indiceAction) throws IOException {
		super(automaton, bImages, indiceAction);

		setStat(attackSpeed, maxLife, resistance, strength);
		setCurrentStat(attackSpeed, life, resistance, strength);

		m_coord = new Coord(C);

		m_direction = dir;

		m_projectiles = new LinkedList<Projectile>();

		setM_model(model);

		m_key = false;


		collidingWith = null;

		m_equipments = new HashMap<>();

		Stuff[] stuffTable = Stuff.values();

		for (int i = 0; i < stuffTable.length; i++) {
			m_equipments.put(stuffTable[i], null);
		}
	}

	@Override
	public boolean move(Direction dir) { // bouger
		if (dir == Direction.F) {
			dir = m_direction;
		}
		if (dir == Direction.E) {
			m_coord.translateX(X_MOVE);
			hitBox.translate(X_MOVE, 0);
		} else if (dir == Direction.W) {
			m_coord.translateX(-X_MOVE);
			hitBox.translate(-X_MOVE, 0);
		} else if (dir == Direction.N) {
			m_coord.translateY(-X_MOVE);
			hitBox.translate(0, -X_MOVE);
		} else if (dir == Direction.S) {
			m_coord.translateY(X_MOVE);
			hitBox.translate(0, X_MOVE);
		}
		return true;
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

	public void setCoord(Coord coord) {
		m_coord = coord;
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
		if (dir == Direction.F)
			return false;
		if (dir != m_direction)
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

	public HashMap<EquipmentManager.Stuff, Equipment> getEquipment() {
		return m_equipments;
	}

	@Override
	public boolean jump(Direction dir) { // sauter
		if (!falling) {
			y_gravity = m_coord.Y();
			jumping = true;
			falling = true;
			m_time = m_ratio_y;
			gravity(m_time);
			if(shooting) {
				int tmp = m_imageIndex;
				currentAction = Action.SHOTMOVE;
				resetAnim();
				m_imageIndex = tmp;
			}
		}

		return true;
	}

	public void tick(long elapsed) {
		m_ratio_x = elapsed;
		m_ratio_y = elapsed;

		if (!checkBlock((hitBox.x + hitBox.width) - 1, m_coord.Y()) && !checkBlock(hitBox.x + 1, m_coord.Y())) {
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
			int topBlock = m_model.m_room.blockTop(m_coord.X(), m_coord.Y());
			hitBox.translate(0, -(m_coord.Y() - topBlock));
			m_coord.setY(topBlock);
			falling = false;
			jumping = false;
			if (currentAction != Action.DEFAULT && !shooting) {
				currentAction = Action.DEFAULT;
				resetAnim();
			}
		}
		if (!falling) {
			if (m_model.m_room.isBlocked(m_coord.X(), m_coord.Y())) {
				int blockTop = m_model.m_room.blockTop(m_coord.X(), m_coord.Y());
				hitBox.translate(0, -(m_coord.Y() - blockTop));
				m_coord.setY(blockTop);
			}
		}
	}

	private void gravity(long t) {
		if (falling) {
			if (checkBlock(m_coord.X(), hitBox.y) || checkBlock((hitBox.x + hitBox.width) - 2, hitBox.y)
					|| checkBlock(hitBox.x + 2, hitBox.y)) {
				int botBlock = m_model.m_room.blockBot(m_coord.X(), m_coord.Y() - m_height) + m_height;
				hitBox.translate(0, -(m_coord.Y() - botBlock));
				m_coord.setY(botBlock);
				y_gravity = m_coord.Y();
				jumping = false;
				t = (long) 0.1;
				m_time = t;
			}
			if(!jumping && !shooting) {
				currentAction = Action.FALLING;
				resetAnim();
			}

			double C;
			if (jumping) {
				C = ACCELERATION_JUMP;
			} else {
				C = 0;
			}

			int newY = (int) ((0.5 * G * Math.pow(t, 2) * 0.0005 - C * t)) + y_gravity;
			hitBox.translate(0, -(m_coord.Y() - newY));
			//m_model.m_mouseCoord.translateY(-(m_coord.Y() - newY));
			m_coord.setY(newY);
		} else {
			m_time = 0;
		}
	}

	public abstract void paint(Graphics gp);

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
				m_currentStatMap.put(CurrentStat.Attackspeed,
						attackSpeed + tmpEquipment.getModification(Stats.AttackSpeed));
				m_currentStatMap.put(CurrentStat.Resistance,
						resistance + tmpEquipment.getModification(Stats.Resistance));
				m_currentStatMap.put(CurrentStat.Strength, strength + tmpEquipment.getModification(Stats.Strengh));
				m_currentStatMap.put(CurrentStat.MaxLife, maxlife + tmpEquipment.getModification(Stats.Health));
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

	public void removeProjectile(Projectile projectile) {
		m_projectiles.remove(projectile);
	}

	public boolean isMoving() {
		boolean moving = m_model.qPressed || m_model.dPressed;
		return moving;
	}

	public boolean getKey() {
		return m_key;
	}

	public void setKey(boolean key) {
		m_key = key;
	}

	public boolean getBossKey() {
		return m_bossKey;
	}

	public void setBossKey(boolean key) {
		m_bossKey = key;
	}

	@Override
	public boolean key(int keyCode) {
		if (keyCode == Controller.K_Q) {
			return m_model.qPressed;
		} else if (keyCode == Controller.K_Z) {
			return m_model.zPressed;
		} else if (keyCode == Controller.K_D) {
			return m_model.dPressed;
		} else if (keyCode == Controller.K_S) {
			return m_model.sPressed;
		} else if (keyCode == Controller.K_SPACE) {
			return m_model.espPressed;
		} else if (keyCode == Controller.K_A) {
			return m_model.aPressed;
		} else if (keyCode == Controller.K_E) {
			return m_model.ePressed;
		} else if (keyCode == Controller.K_V)
			return m_model.vPressed;
		return false;
	}

	public boolean checkBlock(int x, int y) {
		return m_model.m_room.isBlocked(x, y);
	}

	public void shoot(int baseX, int baseY, proj type) {
		if (shooting) {
			shooting = false;
			if (jumping || falling) {
				currentAction = Action.JUMP;
			} else {
				currentAction = Action.DEFAULT;
			}
			resetAnim();
			int m_x = m_coord.X();
			int m_y = hitBox.y + hitBox.height / 2;
			Direction direc;
			float angle;
			double r;

			int x = baseX - m_x;
			int y = m_y - baseY;

			if (baseX > m_x) {
				direc = Direction.E;
			} else {
				direc = Direction.W;
			}

			r = (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
			angle = (float) Math.asin(Math.abs(y) / r);

			if (baseY > m_y) {
				angle = -angle;
			}
			try {
				if (direc == Direction.E) {
					addProjectile(type, new Coord(m_x + hitBox.width / 2, m_y), angle, this, direc);
				} else {
					addProjectile(type, new Coord(m_x - hitBox.width / 2, m_y), angle, this, direc);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void addProjectile(proj type, Coord c, double angle, Character shooter, Direction direction)
			throws Exception {
		switch (type) {
		case ARROW:
			Arrow arrow = (Arrow) Game.m_factory.newEntity(Type.Arrow, direction, c, null, angle, shooter);
			m_projectiles.add(arrow);
			break;
		case MAGIC_PROJECTILE:
			MagicProjectile proj = (MagicProjectile) Game.m_factory.newEntity(Type.MagicProjectile, direction, c, null,
					angle, shooter);
			m_projectiles.add(proj);
			break;
		case LURE:
			Lure lure = (Lure) Game.m_factory.newEntity(Type.Lure, direction, c, m_model, 0, shooter);
			m_projectiles.add(lure);
			break;
		default:
			break;
		}

	}

}
