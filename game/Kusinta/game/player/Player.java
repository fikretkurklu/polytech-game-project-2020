package player;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import com.sun.xml.internal.fastinfoset.algorithm.DoubleEncodingAlgorithm;

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
	int SPEED_WALK_TICK = 4;

	boolean invincible, paintInvincible;
	long m_invincibleElapsed;

	protected BossKey m_bossKey;

	public Player(Automaton automaton, Coord C, Direction dir, Model model, Image[] bImages, HashMap<Action, int[]> hmActions) throws Exception {
		super(automaton, C, dir, model, 100, 100, 1000, 0, 0, bImages, hmActions);

		m_height = SIZE;
		m_width = (int) (m_height * ratio);

		hitBox = new Rectangle(m_coord.X() - (m_width / 4) + 5, m_coord.Y() - (m_height - 15), m_width / 2 - 10,
				m_height - 16);

		m_imageElapsed = 0;
		m_moveElapsed = 0;
		m_invincibleElapsed = 0;

		X_MOVE = 2;
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
			currentAction = Action.MOVE;
		}
		super.move(dir);
		if (dir != m_direction && !shooting) {
			turn(dir);
		}

		return true;
	}

	@Override
	public boolean jump(Direction dir) { // sauter
		if (!checkBlock(m_coord.X(), m_coord.Y() - m_height) && !falling) {
			if (shooting) {
				if (isMoving())
					currentAction = Action.SHOTMOVE;
				else
					currentAction = Action.SHOT;
			} else {
				currentAction = Action.JUMP;
			}
			resetAnim();
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
			shooting = true;
			m_imageElapsed = m_imageTick;
			m_imageIndex = 0;
			return true;
		}
		return false;
	}

	private void checkDoor() {
		boolean door;
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
			m_imageIndex += 1;
			if (!gotpower()) {
				if (m_imageIndex == indiceAction.get(Action.DEATH).length && getM_model().getDiametre() == 0) {
					getM_model().setDiametre(1);
				}
			} else {
				if (shooting) {
					if (m_imageIndex == indiceAction.get(currentAction).length) {
						super.shoot(getM_model().m_mouseCoord.X(), getM_model().m_mouseCoord.Y(), proj.ARROW);
					}
				}
				if (!shooting && !falling && !isMoving()) {
					currentAction = Action.DEFAULT;
				}
			}
			if (m_imageIndex >= indiceAction.get(currentAction).length) {
				m_imageIndex = 0;
			}
		}

		m_moveElapsed += elapsed;
		if (m_moveElapsed > SPEED_WALK_TICK)

		{
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

		Image img;

		img = getImage();

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
		if (!gotpower()) {
			resetAnim();
		}
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

}