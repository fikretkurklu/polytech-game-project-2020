package player;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import automaton.*;
import game.Coord;
import game.ImageLoader;
import game.Model;
import game.Model.mode;
import opponent.BossKey;
import projectile.Projectile.proj;
import room.Door;
import environnement.Element;

public class Player extends Character {

	public static final int SIZE = (int) (1.5 * Element.SIZE);
	private final String PATH_ARROW = "resources/Player/spriteArrow.png";
	private final String PATH_SPRITE_PLAYER = "resources/Player/spritePlayer.png";

	int SPEED_WALK_TICK = 4;

	boolean invincible, paintInvincible;

	long m_imageElapsed;
	long m_moveElapsed, m_invincibleElapsed;

	int min_image_index, max_image_index;

	protected BossKey m_bossKey;

	public Player(Automaton automaton, Coord C, Direction dir, Model model) throws Exception {
		super(automaton, C, dir, model, 100, 100, 1000, 0, 0);
		bI = ImageLoader.loadBufferedSprite(PATH_SPRITE_PLAYER, 18, 7);

		imageProjectile = ImageLoader.loadImageProjectile(PATH_ARROW);

		float ratio = (float) ((float) bI[0].getWidth()) / (float) (bI[0].getHeight());

		m_height = SIZE;
		m_width = (int) (m_height * ratio);

		hitBox = new Rectangle(m_coord.X() - (m_width / 4) + 5, m_coord.Y() - (m_height - 15), m_width / 2 - 10, m_height - 16);

		m_imageElapsed = 0;
		m_moveElapsed = 0;
		m_invincibleElapsed = 0;

		min_image_index = 0;
		max_image_index = 3;
		
		X_MOVE = 2;

		reset();
		setMoney(10000);

		m_bossKey = null;
	}

	public void reset() {
		m_imageElapsed = 0;
		jumping = false;
		falling = false;
		shooting = false;
		invincible = true;
		paintInvincible = true;
	}

	@Override
	public boolean move(Direction dir) { // bouger
		if (!shooting && !jumping) {
			setImageIndex(8, 13);
		}
		super.move(dir);
		if (dir !=  m_direction && !shooting) {
			turn(dir);
		}

		return true;
	}

	@Override
	public boolean jump(Direction dir) { // sauter
		if (!checkBlock(m_coord.X(), m_coord.Y() - m_height) && !falling) {
			if (shooting) {
				setImageIndex(120, 123);
				if (isMoving())
					setImageIndex(114, 117);
				else
					setImageIndex(120, 123);
			} else {
				setImageIndex(15, 23);
				m_image_index = 16;
			}
			super.jump(dir);
		}

		return true;
	}

	@Override
	public boolean pop(Direction dir) {
		reset();
		getM_model().switchEnv(mode.VILLAGE);
		return true;
	}

	public boolean pick(Direction dir) {
		checkDoor();
		return true;
	}

	@Override
	public boolean egg(Direction dir) { // tir
		if (!shooting) {
			if (jumping || falling || isMoving()) {
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
		System.out.println("deçu");
		Door d = getM_model().m_room.getDoor();
		Rectangle h = d.getHitBox();
		int y1 = hitBox.y + 3 * hitBox.height / 4;
		int y2 = hitBox.y + hitBox.height / 4;
		door = h.contains(hitBox.x, y1) || h.contains(hitBox.x + hitBox.width, y1) || h.contains(hitBox.x, y2)
				|| h.contains(hitBox.x + hitBox.width, y2);
		if (door && m_key != false) {
			d.setM_model(getM_model());
			d.activate();
		}
	}

	public void tick(long elapsed) {
		super.tick(elapsed);

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
				if (m_image_index == 68 && getM_model().getDiametre() == 0) {
					getM_model().setDiametre(1);
				}
			} else {
				if (shooting && (m_image_index == 117 || m_image_index == 123)) {
					super.shoot(getM_model().m_mouseCoord.X(), getM_model().m_mouseCoord.Y(), proj.ARROW);
				} else if (jumping && !shooting && m_image_index == 17) {
					m_image_index = 22;
				} else if (!shooting && ((falling && !jumping) || (jumping && m_image_index == 23))) {
					setImageIndex(23, 23);
				} else {
					m_image_index++;
				}

				if (!shooting && !falling && !isMoving()) {
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
				if (getM_model().m_mouseCoord.X() > m_coord.X()) {
					turn(Direction.E);
				} else {
					turn(Direction.W);
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
			if (m_direction == Direction.E) {
				g.drawImage(img, m_x - (w / 2), m_y - h + H, w, h, null);
			} else {
				g.drawImage(img, m_x + (w / 2), m_y - h + H, -w, h, null);
			}
		} else {
			if (paintInvincible) {
				if (m_direction == Direction.E) {
					g.drawImage(img, m_x - (w / 2), m_y - h, w, h, null);
				} else {
					g.drawImage(img, m_x + (w / 2), m_y - h, -w, h, null);
				}
			}
			paintInvincible = !paintInvincible;
		}
		g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
		for (int i = 0; i < m_projectiles.size(); i++) {
			m_projectiles.get(i).paint(g);
		}
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
	
	public void setInvincibility() {
		invincible = true;
		paintInvincible = true;
	}
	
	public void setImageIndex(int min, int max) {
		if (min_image_index != min || max_image_index != max) {
			m_imageElapsed = 200;
		}
		min_image_index = min;
		max_image_index = max;
	}
}