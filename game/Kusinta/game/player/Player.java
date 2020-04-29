package player;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import automaton.*;
import game.Controller;
import game.Model;
import projectile.Arrow;
import projectile.Projectile;
import environnement.Element;
import equipment.Equipment;
import equipment.EquipmentManager.Stuff;
import equipment.Stat.Stats;

public class Player extends Character {

	public static final int SIZE = (int) (1.5 * Element.SIZE);

	double G = 9.81;
	double ACCELERATION_JUMP = 1.8;

	int SPEED_WALK = 1;

	int DIMENSION;

	boolean qPressed, zPressed, dPressed, espPressed, aPressed, ePressed, vPressed;
	boolean falling, jumping, shooting, moving;

	int y_gravity;
	int dt_y;
	double speed_y;

	long m_ratio_x, m_ratio_y;

	long m_time, m_shot_time;

	BufferedImage[] bIShooting;
	long m_imageElapsed;

	public HashMap<Stats, Integer> m_default_stat_map;

	public Player(Automaton automaton, int x, int y, Direction dir, Model model) throws Exception {
		super(automaton, x, y, dir, model, 100, 100, 1000, 0, 0);
		bI = m_model.loadSprite("resources/Player/spritePlayer.png", 16, 7);
		bIShooting = m_model.loadSprite("resources/Player/spriteArcher.png", 4, 4);

		DIMENSION = SIZE / (bI[0].getHeight());
		float ratio = (float) (bI[0].getWidth() * 2) / (float) (5 * bI[0].getHeight());

		m_height = DIMENSION * bI[0].getHeight();
		m_width = (int) (m_height * ratio);

		int m_x = m_coord.X();
		int m_y = m_coord.Y();

		hitBox = new Rectangle(m_x - (m_width / 2 + 3 * DIMENSION), m_y - (m_height + 3 * DIMENSION),
				2 * (m_width / 2 + 3 * DIMENSION), m_height + 3 * DIMENSION);

		m_shot_time = System.currentTimeMillis();

		m_imageElapsed = 0;

		qPressed = false;
		zPressed = false;
		dPressed = false;
		espPressed = false;
		aPressed = false;
		ePressed = false;
		vPressed = false;

		m_slowness = 10;

		m_default_stat_map = new HashMap<>();

		Stats[] statsTable = Stats.values();

		for (int i = 0; i < statsTable.length; i++) {
			m_default_stat_map.put(statsTable[i], 0);
		}

		setStat();
	}

	@Override
	public boolean move(Direction dir) { // bouger
		int random = (int) (Math.random() * 10);
		if (random < m_slowness) {

			moving = true;

			if (shooting) {
				if (m_image_index <= 5)
					m_image_index = m_image_index + 6;
			}

			int m_x = m_coord.X();
			int m_y = m_coord.Y();

			if (!dir.toString().equals(m_direction.toString()) && !shooting) {
				turn(dir);
			}

			if (dir.toString().equals("E")) {
				if (!checkBlock((hitBox.x + hitBox.width), m_y - 1)
						&& !checkBlock((hitBox.x + hitBox.width), m_y - m_height)
						&& !checkBlock((hitBox.x + hitBox.width), m_y - m_height / 2)) {
					m_x += SPEED_WALK;
					m_coord.setX(m_x);
					hitBox.translate(SPEED_WALK, 0);
				}
			} else if (dir.toString().equals("W")) {
				if (!checkBlock(hitBox.x, m_y - 1) && !checkBlock(hitBox.x, m_y - m_height)
						&& !checkBlock(hitBox.x, m_y - m_height / 2)) {
					m_x -= SPEED_WALK;
					m_coord.setX(m_x);
					hitBox.translate(-SPEED_WALK, 0);
				}
			}
		}
		return true;
	}

	@Override
	public boolean jump(Direction dir) { // sauter
		if (!checkBlock(m_coord.X(), m_coord.Y() - m_height) && !falling) {

			y_gravity = m_coord.Y();
			jumping = true;
			falling = true;
			if (shooting) {
				if (m_image_index <= 5)
					m_image_index = m_image_index + 6;
			}
			if (!shooting)
				m_image_index = 16;
			m_time = m_ratio_y;
			gravity(m_time);
		}
		return true;
	}

	@Override
	public boolean pop(Direction dir) {
		try {
			m_model.setVillageEnv();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	private void gravity(long t) {
		if (!checkBlock(m_coord.X(), m_coord.Y()) && !checkBlock((hitBox.x + hitBox.width) - 1, m_coord.Y())
				&& !checkBlock(hitBox.x - 2, m_coord.Y()) || falling) {

			falling = true;

			if (checkBlock(m_coord.X(), m_coord.Y() - m_height)
					|| checkBlock((hitBox.x + hitBox.width) - 2, m_coord.Y() - m_height)
					|| checkBlock(hitBox.x + 2, m_coord.Y() - m_height)) {
				int botBlock = m_model.m_room.blockBot(m_coord.X(), m_coord.Y() - m_height) + m_height;
				hitBox.translate(0, -(m_coord.Y() - botBlock));
				m_coord.setY(botBlock);
				y_gravity = m_coord.Y();
				jumping = false;
				t = (long) 0.1;
				m_time = t;
			}

			if (!jumping && !shooting)
				m_image_index = 23;

			double C;
			if (jumping) {
				C = ACCELERATION_JUMP;
			} else {
				C = 0;
			}

			int newY = (int) ((0.5 * G * Math.pow(t, 2) * 0.0005 - C * t)) + y_gravity;
			hitBox.translate(0, -(m_coord.Y() - newY));
			m_coord.setY(newY);
		} else {
			m_time = 0;
		}
	}

	@Override
	public boolean egg(Direction dir) { // tir

		long now = System.currentTimeMillis();

		if (now - m_shot_time > m_attackSpeed && !shooting) {

			shooting = true;

			if (jumping || falling || moving) {
				m_image_index = 9;
			} else {
				m_image_index = 2;
			}

			m_shot_time = now;

			return true;
		}

		return false;
	}

	public void setPressed(int keyCode, boolean pressed) {
		if (keyCode == Controller.K_Q) {
			qPressed = pressed;
			if (pressed) {
				if (!shooting && !falling && !moving)
					m_image_index = 8;
				moving = true;
			} else {
				moving = false;
				if (!falling && shooting && m_image_index > 7)
					m_image_index = m_image_index - 6;
			}
		}
		if (keyCode == Controller.K_Z) {
			zPressed = pressed;
			if (pressed && !falling) {
				jumping = true;
			}
		}
		if (keyCode == Controller.K_D) {
			dPressed = pressed;
			if (pressed) {
				if (!shooting && !falling && !moving)
					m_image_index = 8;
				moving = true;
			} else {
				moving = false;
				if (!falling && shooting && m_image_index > 7)
					m_image_index = m_image_index - 6;
			}
		}
		if (keyCode == Controller.K_SPACE) {
			espPressed = pressed;
			if (pressed) {
				if (!shooting) {
					if (jumping || falling || moving) {
						m_image_index = 9;
					} else {
						m_image_index = 2;
					}
				}
				shooting = true;
			}
		}
		if (keyCode == Controller.K_A)
			aPressed = pressed;
		if (keyCode == Controller.K_E)
			ePressed = pressed;
		if (keyCode == Controller.K_V)
			vPressed = pressed;
	}

	@Override
	public boolean key(int keyCode) {
		if (keyCode == Controller.K_Q) {
			return qPressed;
		} else if (keyCode == Controller.K_Z) {
			return zPressed;
		} else if (keyCode == Controller.K_D) {
			return dPressed;
		} else if (keyCode == Controller.K_SPACE) {
			return espPressed;
		} else if (keyCode == Controller.K_A) {
			return aPressed;
		} else if (keyCode == Controller.K_E) {
			return ePressed;
		} else if (keyCode == Controller.K_V)
			return vPressed;
		return false;
	}

	public void tick(long elapsed) {
		m_ratio_x = elapsed;
		m_ratio_y = elapsed;
		if (!checkBlock(m_coord.X(), m_coord.Y()) && !checkBlock((hitBox.x + hitBox.width) - 1, m_coord.Y())
				&& !checkBlock(hitBox.x + 1, m_coord.Y())) {
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
		} else {
			jumping = false;
			falling = false;
			int botBlock = m_model.m_room.blockTop(m_coord.X(), m_coord.Y());
			hitBox.translate(0, -(m_coord.Y() - botBlock));
			m_coord.setY(botBlock);
		}

		if (!moving && !falling) {
			int topBlock = m_model.m_room.blockTop(m_coord.X(), m_coord.Y());
			m_coord.setY(topBlock);
		}
		if (m_model.m_room.isBlocked(m_coord.X(), m_coord.Y() - m_height / 2)) {
			int topBlock = m_model.m_room.blockTop(m_coord.X(), m_coord.Y() - m_height / 2);
			hitBox.translate(0, -(m_coord.Y() - topBlock));
			m_coord.setY(topBlock);
		}

		if (shooting) {
			int mouse_x = m_model.m_mouseCoord.X() - m_model.getXDecalage();
			Direction direc;

			if (mouse_x > m_coord.X()) {
				direc = new Direction("E");
			} else {
				direc = new Direction("W");
			}

			turn(direc);
		}

		m_imageElapsed += elapsed;
		if (m_imageElapsed > 200) {
			m_imageElapsed = 0;

			if (shooting) {
				m_image_index++;
				if ((moving || falling || jumping) && m_image_index > 12) {
					shoot();
					shooting = false;
				} else if (m_image_index > 6 && !falling && !moving) {
					shoot();
					shooting = false;
				}
				if (m_image_index == 6 || m_image_index == 7 || m_image_index == 12 || m_image_index == 13) {
					shoot();
					shooting = false;
				}
			} else if (jumping || falling) {
				m_image_index = (m_image_index - 15 + 1) % 9 + 15;
				if (falling && !jumping)
					m_image_index = 23;
				if (m_image_index == 18)
					m_image_index = 22;
				if (m_image_index >= 22)
					m_image_index = 22;
			} else if (moving) {
				m_image_index = (m_image_index - 8 + 1) % 6 + 8;
				if (m_image_index < 8)
					m_image_index = 8;
			} else {
				m_image_index = (m_image_index + 1) % 4;
			}
		}
		m_automaton.step(this);

		for (int i = 0; i < m_projectiles.size(); i++) {
			((Arrow) m_projectiles.get(i)).tick(elapsed);
		}
	}

	public void paint(Graphics g) {
//		System.out.println(m_coord.X());
//		System.out.println(m_coord.Y());
		if (bI != null) {
			int m_x = m_coord.X();
			int m_y = m_coord.Y();

			BufferedImage img;
			if (shooting) {
				if (m_image_index > 12)
					m_image_index = 9;
				img = bIShooting[m_image_index];
			} else {
				img = bI[m_image_index];
			}

			int w = DIMENSION * m_width;
			int h = m_height;
			if (m_direction.toString().equals("E")) {
				g.drawImage(img, m_x - (w / 2), m_y - h, w, h, null);
			} else {
				g.drawImage(img, m_x + (w / 2), m_y - h, -w, h, null);
			}
//			g.setColor(Color.blue);
//			g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
		}

		for (int i = 0; i < m_projectiles.size(); i++) {

			((Arrow) m_projectiles.get(i)).paint(g);

		}
	}

	public boolean checkBlock(int x, int y) {
		return m_model.m_room.isBlocked(x, y);
	}

	public void setSlowness(int s) {
		if (s < 6) {
			m_slowness = 6;
		} else {
			m_slowness = s;
		}
	}

	public void setGravity(int g) {
		G = g;
	}

	public void shoot() {
		if (shooting) {
			int m_x = m_coord.X();
			int m_y = m_coord.Y() - m_height / 2;

			Direction direc;
			float angle;
			double r;
			int mouse_x = m_model.m_mouseCoord.X() - m_model.getXDecalage();
			int mouse_y = m_model.m_mouseCoord.Y() - m_model.getYDecalage();

			int x = mouse_x - m_x;
			int y = m_y - mouse_y;

			if (mouse_x > m_x) {
				direc = new Direction("E");
			} else {
				direc = new Direction("W");
			}

			r = (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
			angle = (float) Math.asin(Math.abs(y) / r);

			if (mouse_y > m_y) {
				angle = -angle;
			}

			shooting = false;

			try {
				addProjectile(m_x, m_y, angle, this, direc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void addProjectile(int x, int y, double angle, Player player, Direction direction) throws Exception {
		m_projectiles.add(new Arrow(m_model.arrowAutomaton, x, y, angle, player, direction));
	}

	public void removeProjectile(Projectile projectile) {
		m_projectiles.remove(projectile);
	}

	public Equipment addEquipment(Equipment equipment) {
		Stuff stuff = equipment.toStuff();
		Equipment res = null;
		
		if (m_equipments.get(stuff) != null) {
			res = m_equipments.get(stuff);
		}
		
		m_equipments.put(stuff, equipment);
		
		Stuff[] stuffTable = Stuff.values();

		m_attackSpeed = m_default_stat_map.get(Stats.AttackSpeed);
		m_resistance = m_default_stat_map.get(Stats.Resistance);
		m_strength = m_default_stat_map.get(Stats.Strengh);
		MAX_LIFE = m_default_stat_map.get(Stats.Health);

		for (int i = 0; i < stuffTable.length; i++) {
			Equipment tmpEquipment = m_equipments.get(stuffTable[i]);
			if (tmpEquipment != null) {
				m_attackSpeed += tmpEquipment.getModification(Stats.AttackSpeed);
				m_resistance += tmpEquipment.getModification(Stats.Resistance);
				m_strength += tmpEquipment.getModification(Stats.Strengh);
				MAX_LIFE += tmpEquipment.getModification(Stats.Health);
			}
		}
		
		return res;
	}
	
	public void setMoney(int money) {
		m_money += money;
	}

	public void setStat() {
		m_default_stat_map.put(Stats.AttackSpeed, 1000);
		m_default_stat_map.put(Stats.Health, 100);
		m_default_stat_map.put(Stats.Resistance, 0);
		m_default_stat_map.put(Stats.Strengh, 1);
	}

}
