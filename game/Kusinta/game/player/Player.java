package player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import automaton.*;
import game.Model;
import game.Model.mode;
import opponent.BossKey;
import projectile.Arrow;
import room.Door;
import environnement.Element;

public class Player extends Character {

	public static final int SIZE = (int) (1.5 * Element.SIZE);
	private final String PATH_ARROW = "resources/Player/spriteArrow.png";
	private final String PATH_SPRITE_PLAYER = "resources/Player/spritePlayer.png";

	int SPEED_WALK = 2;
	int SPEED_WALK_TICK = 4;

	int DIMENSION;

	boolean shooting, invincible, paintInvincible;

	long m_imageElapsed;
	long m_moveElapsed, m_invincibleElapsed;

	int min_image_index, max_image_index;

	protected BossKey m_bossKey;

	public Player(Automaton automaton, int x, int y, Direction dir, Model model) throws Exception {
		super(automaton, x, y, dir, model, 100, 100, 1000, 0, 0);
		bI = m_model.loadSprite(PATH_SPRITE_PLAYER, 18, 7);

		loadImageProjectile(PATH_ARROW);

		DIMENSION = SIZE / (bI[0].getHeight());
		float ratio = (float) ((float) bI[0].getWidth()) / (float) (bI[0].getHeight());

		m_height = SIZE;
		m_width = (int) (m_height * ratio);

		int m_x = m_coord.X();
		int m_y = m_coord.Y();

		hitBox = new Rectangle(m_x - (m_width / 4) + 5, m_y - (m_height - 15), m_width / 2 - 10, m_height - 15);

		m_imageElapsed = 0;
		m_moveElapsed = 0;
		m_invincibleElapsed = 0;

		min_image_index = 0;
		max_image_index = 3;

		reset();
		setMoney(10000);

		m_bossKey = null;
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
		jumping = false;
		falling = false;
		shooting = false;
		invincible = false;
		paintInvincible = true;
	}

	@Override
	public boolean move(Direction dir) { // bouger

		if (!shooting && !jumping) {
			setImageIndex(8, 13);
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
			if (shooting) {
				setImageIndex(120, 123);
				if ((qPressed || dPressed) && m_image_index <= 123)
					setImageIndex(114, 117);
				m_image_index = m_image_index + 6;
			} else {
				setImageIndex(15, 23);
				m_image_index = 16;
			}
			y_gravity = m_coord.Y();
			jumping = true;
			falling = true;

			m_time = m_ratio_y;
			super.jump(dir);
		}

		return true;
	}

	@Override
	public boolean pop(Direction dir) {
		reset();
		m_model.switchEnv(mode.VILLAGE);
		return true;
	}

	public boolean pick(Direction dir) {
		checkDoor();
		return true;
	}

	@Override
	public boolean egg(Direction dir) { // tir
		boolean moving = qPressed || dPressed;

		if (!shooting) {
			if (jumping || falling || moving) {
				m_image_index = 120;
				setImageIndex(120, 123);
			} else {
				m_image_index = 114;
				setImageIndex(114, 117);
			}

			shooting = true;

			return true;
		}
		return false;
	}

	private void checkDoor() {
		boolean door;
		Door d = m_model.m_room.getDoor();
		Rectangle h = d.getHitBox();
		int y1 = hitBox.y + 3 * hitBox.height / 4;
		int y2 = hitBox.y + hitBox.height / 4;
		door = h.contains(hitBox.x, y1) || h.contains(hitBox.x + hitBox.width, y1) || h.contains(hitBox.x, y2)
				|| h.contains(hitBox.x + hitBox.width, y2);
		if (door && m_key != null) {
			d.activate();
		}
	}

	public void tick(long elapsed) {
		super.tick(elapsed);

		boolean moving = qPressed || dPressed;

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
			} else {
				if (shooting && (m_image_index == 117 || m_image_index == 123)) {
					shoot();
				} else if (jumping && !shooting && m_image_index == 17) {
					m_image_index = 22;
				} else if ((falling && !jumping) || (jumping && m_image_index == 23)) {
					setImageIndex(23, 23);
				} else {
					m_image_index++;
				}

				if (!shooting && !falling && !moving) {
					setImageIndex(0, 3);
				}

				if (m_image_index < min_image_index || m_image_index > max_image_index) {
					m_image_index = min_image_index;
				}
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
		img = bI[m_image_index];

		int w = m_width;
		int h = m_height;

		int H;
		if (shooting && !jumping) {
			H = 17;
		} else {
			H = 0;
		}
		if (!invincible) {
//			g.setColor(Color.blue);
//			g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
			if (m_direction.toString().equals("E")) {
				g.drawImage(img, m_x - (w / 2), m_y - h + H, w, h, null);
			} else {
				g.drawImage(img, m_x + (w / 2), m_y - h + H, -w, h, null);
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

	public void setGravity(int g) {
		G = g;
	}

	public void setMoney(int money) {
		m_money = money;
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

	public void setBossKey(BossKey key) {
		m_bossKey = key;
	}

	public void setImageIndex(int min, int max) {
		if (min_image_index != min || max_image_index != max) {
			m_imageElapsed = 200;
		}
		min_image_index = min;
		max_image_index = max;
	}
}