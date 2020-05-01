package player;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import automaton.*;
import game.Controller;
import game.Model;
import projectile.Arrow;
import environnement.Element;

public class Player extends Character {

	public static final int SIZE = (int) (1.5 * Element.SIZE);
	private final String PATH_ARROW = "resources/Player/spriteArrow.png";
	private final String PATH_SPRITE_PLAYER = "resources/Player/spritePlayer.png";
	private final String PATH_SPRITE_TIR = "resources/Player/spriteArcher.png";

	double G = 9.81;
	double ACCELERATION_JUMP = 1.8;

	int SPEED_WALK = 2;
	int SPEED_WALK_TICK = 4;

	int DIMENSION;

	boolean qPressed, zPressed, dPressed, espPressed, aPressed, ePressed, vPressed;
	boolean falling, jumping, shooting, invincible, paintInvincible;

	int y_gravity;
	int dt_y;
	double speed_y;

	long m_ratio_x, m_ratio_y;

	long m_time;

	BufferedImage[] bIShooting;
	long m_imageElapsed;
	long m_moveElapsed, m_invincibleElapsed;

	public Player(Automaton automaton, int x, int y, Direction dir, Model model) throws Exception {
		super(automaton, x, y, dir, model, 100, 100, 1000, 0, 0);
		bI = m_model.loadSprite(PATH_SPRITE_PLAYER, 16, 7);
		bIShooting = m_model.loadSprite(PATH_SPRITE_TIR, 4, 4);

		loadImageProjectile(PATH_ARROW);

		DIMENSION = SIZE / (bI[0].getHeight());
		float ratio = (float) (bI[0].getWidth() * 2) / (float) (5 * bI[0].getHeight());

		m_height = DIMENSION * bI[0].getHeight();
		m_width = (int) (m_height * ratio);

		int m_x = m_coord.X();
		int m_y = m_coord.Y();

		hitBox = new Rectangle(m_x - (m_width / 2 + 3 * DIMENSION), m_y - (m_height), 2 * (m_width / 2 + 3 * DIMENSION),
				m_height);

		m_imageElapsed = 0;
		m_moveElapsed = 0;
		m_invincibleElapsed = 0;

		reset();
		setMoney(10000);

		m_key = null;
	}

	public void reset() {
		qPressed = false;
		zPressed = false;
		dPressed = false;
		espPressed = false;
		aPressed = false;
		ePressed = false;
		vPressed = false;
		m_imageElapsed = 0;
		moving = false;
		jumping = false;
		falling = false;
		shooting = false;
		invincible = false;
		paintInvincible = true;
	}

	@Override
	public boolean move(Direction dir) { // bouger

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
			if (!checkBlock((hitBox.x + hitBox.width) + SPEED_WALK, m_y - 1)
					&& !checkBlock((hitBox.x + hitBox.width) + SPEED_WALK, m_y - m_height)
					&& !checkBlock((hitBox.x + hitBox.width) + SPEED_WALK, m_y - m_height / 2)) {
				m_x += SPEED_WALK;
				m_coord.setX(m_x);
				hitBox.translate(SPEED_WALK, 0);
			}
		} else if (dir.toString().equals("W")) {
			if (!checkBlock(hitBox.x - SPEED_WALK, m_y - 1) && !checkBlock(hitBox.x - SPEED_WALK, m_y - m_height)
					&& !checkBlock(hitBox.x - SPEED_WALK, m_y - m_height / 2)) {
				m_x -= SPEED_WALK;
				m_coord.setX(m_x);
				hitBox.translate(-SPEED_WALK, 0);
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
		if (falling) {
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
		if (!shooting) {
			shooting = true;

			if (jumping || falling || moving) {
				m_image_index = 9;
			} else {
				m_image_index = 2;
			}
			return true;
		}
		return false;
	}

	public void setPressed(int keyCode, boolean pressed) {
		if (gotpower()) {
			switch (keyCode) {
			case Controller.K_Q:
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
				break;
			case Controller.K_Z:
				zPressed = pressed;
				break;
			case Controller.K_D:
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
				break;
			case Controller.K_SPACE:
				espPressed = pressed;
				if (pressed) {
					if (!shooting) {
						if (jumping || falling || moving) {
							m_image_index = 9;
						} else {
							m_image_index = 2;
						}
					}
				}
				break;
			case Controller.K_A:
				aPressed = pressed;
				break;
			case Controller.K_E:
				ePressed = pressed;
				break;
			case Controller.K_V:
				vPressed = pressed;
				break;
			}
		}
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
		}

		if (invincible) {
			m_invincibleElapsed += elapsed;
			if (m_invincibleElapsed > 1000) {
				invincible = false;
				m_invincibleElapsed = 0;
			}
		}

		m_imageElapsed += elapsed;
		float attackspeed = 200;
		if (shooting) {
			attackspeed = m_currentStatMap.get(CurrentStat.Attackspeed);
			attackspeed = 200 / (attackspeed / 1000);
		}

		if (m_imageElapsed > attackspeed) {
			m_imageElapsed = 0;

			if (!gotpower()) {
				m_image_index = (m_image_index - 66 + 1) % 3 + 66;
				if (m_image_index == 68 && m_model.getDiametre() == 0) {
					m_model.setDiametre(1);
				}
			} else if (shooting) {
				m_image_index++;
				if ((moving || falling || jumping) && m_image_index > 12) {
					shoot();
					shooting = false;
				} else if (m_image_index > 6 && !falling && !moving) {
					shoot();
					shooting = false;
				} else if (m_image_index == 6 || m_image_index == 7 || m_image_index == 12 || m_image_index == 13) {
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

		m_moveElapsed += elapsed;
		if (m_moveElapsed > SPEED_WALK_TICK) {
			m_moveElapsed -= SPEED_WALK_TICK;
			if (shooting) {
				int mouse_x = m_model.m_mouseCoord.X() - m_model.getXDecalage();
				if (mouse_x > m_coord.X()) {
					turn(new Direction("E"));
				} else {
					turn(new Direction("W"));
				}
			}
			m_automaton.step(this);
		}

		for (int i = 0; i < m_projectiles.size(); i++) {
			m_projectiles.get(i).tick(elapsed);
		}
	}

	public void paint(Graphics g) {
		int m_x = m_coord.X();
		int m_y = m_coord.Y();

		BufferedImage img;
		if (shooting && gotpower()) {
			if (m_image_index > 12)
				m_image_index = 9;
			img = bIShooting[m_image_index];
		} else {
			img = bI[m_image_index];
		}

		int w = DIMENSION * m_width;
		int h = m_height;
		if (!invincible) {
			if (m_direction.toString().equals("E")) {
				g.drawImage(img, m_x - (w / 2), m_y - h, w, h, null);
			} else {
				g.drawImage(img, m_x + (w / 2), m_y - h, -w, h, null);
			}
		} else {
			if (paintInvincible) {
				if (m_direction.toString().equals("E")) {
					g.drawImage(img, m_x - (w / 2), m_y - h, w, h, null);
				} else {
					g.drawImage(img, m_x + (w / 2), m_y - h, -w, h, null);
				}
			}
			paintInvincible = !paintInvincible;
		}

		for (int i = 0; i < m_projectiles.size(); i++) {
			m_projectiles.get(i).paint(g);
		}
	}

	public boolean checkBlock(int x, int y) {
		return m_model.m_room.isBlocked(x, y);
	}

	public void setGravity(int g) {
		G = g;
	}

	public void setMoney(int money) {
		m_money = money;
		System.out.println(m_money);
	}

	public int getMoney() {
		return m_money;
	}

	public void shoot() {
		if (shooting) {
			int m_x = m_coord.X() + hitBox.width / 2;
			int m_y = m_coord.Y() - m_height / 2;
			Direction direc;
			double angle;
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
			angle = Math.asin(Math.abs(y) / r);

			if (mouse_y > m_y) {
				angle = -angle;
			}

			shooting = false;

			try {
				if (direc.toString().equals("E")) {
					addProjectile(m_x + hitBox.width / 2, m_y, angle, this, direc);
				} else {
					addProjectile(m_x - hitBox.width / 2, m_y, angle, this, direc);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void addProjectile(int x, int y, double angle, Player player, Direction direction) throws Exception {
		m_projectiles.add(new Arrow(m_model.arrowAutomaton, x, y, angle, player, direction));
	}

	@Override
	public boolean explode() {
		if (!gotpower())
			m_image_index = 66;
		return true;
	}

	public void loseLife(int l) {
		if (!invincible) {
			invincible = true;
			paintInvincible = true;
			m_currentStatMap.put(CurrentStat.Life, (m_currentStatMap.get(CurrentStat.Life) - l));
		}
	}
}