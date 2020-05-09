package underworld;

import java.io.IOException;


import java.util.HashMap;

import projectile.Projectile;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import environnement.Element;
import game.Coord;
import game.Game;
import game.Model;
import game.Model.mode;
import player.Character;

public class PlayerSoul extends Character {

	public static final int SIZE = (int) Element.SIZE;
	public static final int MAX_LURE = 2;

	long m_lureGenerationElapsed;
	long m_dashElapsed;
	long m_invincibleElapsed;

	int nbLure = 0;
	int fragmentsPicked = 0;
	
	int m_lifePaint, m_dashPaint, m_lurePaint;

	boolean hidden, escape, escaped, invincible, dead, paint;
	boolean dashAvailable, lureAvailable;
	boolean leftOrientation;

	public PlayerSoul(Automaton automaton, Coord c, Direction dir, Image[] images, Model model,
			HashMap<Action, int[]> hmAction) throws IOException {
		super(automaton, c, dir, model, 100, 100, 0, 0, 0, images, hmAction);
		m_width = SIZE;
		m_height = SIZE;
		dashAvailable = true;
		lureAvailable = true;
		invincible = true;
		paint = false;
		hidden = false;
		escape = false;
		dead = false;
		leftOrientation = false;
		currentAction = Action.JUMP;
		resetAnim();
		hitBox = new Rectangle(m_coord.X(), m_coord.Y(), SIZE, SIZE);
		m_model = model;
		m_dashPaint = m_width;
		m_lifePaint = m_width;
		m_lurePaint = m_width;
		m_invincibleElapsed = 0;
	}

	public boolean lureActive() {
		return nbLure > 0;
	}

	@Override
	public boolean closest(Category cat, Direction dir) {
		if ((escape) && (cat == Category.G)) {
			return m_model.m_underworld.m_gate.contains(hitBox);
		}
		if (cat == Category.C) {
			int x = m_coord.X();
			int y = m_coord.Y();
			return ((x + m_width < 0) || (y + m_height < 0) || (x > Underworld.BORDERX) || (y > Underworld.BORDERY));
		}
		int xCenter = m_coord.X() + (m_width / 2);
		int yCenter = m_coord.Y() + (m_height / 2);
		if (cat == Category.O) {
			for (int i = 0; i < m_model.m_underworld.m_clouds.length; i++) {
				if ((m_model.m_underworld.m_clouds[i].contains(xCenter, yCenter))) {
					setVisibility(true);
					return true;
				}
			}
			setVisibility(false);
			return false;
		}
		if (cat == Category.P) {
			for (int i = 0; i < m_model.m_underworld.m_fragments.length; i++) {
				if ((!m_model.m_underworld.m_fragments[i].picked)
						&& (m_model.m_underworld.m_fragments[i].contains(xCenter, yCenter))) {
					m_model.m_underworld.m_fragments[i].setPicked();
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean gotstuff() {
		return (fragmentsPicked == m_model.m_underworld.MAX_FRAGMENTS);
	}

	public static int DISTANCE = 3;
	public static final int NORMALSPEED = 3;
	public static final int DASHSPEED = 5;

	@Override
	public boolean cell(Direction dir, Category cat) {
		switch (dir.toString()) {
		case Direction.Ns:
			if  ((m_model.m_underworld.isBlocked(hitBox.x, hitBox.y)) || (m_model.m_underworld.isBlocked(hitBox.x + m_width, hitBox.y))) {
				DISTANCE = NORMALSPEED;
				int yMiddle = hitBox.y + (m_height/2);
				if ((m_model.dPressed) && (!m_model.m_underworld.isBlocked(hitBox.x + m_width, yMiddle))) {
					m_coord.translateX(DISTANCE);
				} else if ((m_model.qPressed) && (!m_model.m_underworld.isBlocked(hitBox.x, yMiddle))) {
					m_coord.translateX(-DISTANCE);
				} else if (!m_model.m_underworld.isBlocked(hitBox.x + m_width/2, hitBox.y)) {
					m_coord.translateY(-DISTANCE);
				}
				hitBox.setLocation(m_coord.X(), m_coord.Y());
				return true;
			}
			return false;
		case Direction.Ss:
			if  ((m_model.m_underworld.isBlocked(hitBox.x, hitBox.y + m_height)) ||
			(m_model.m_underworld.isBlocked(hitBox.x + m_width, hitBox.y + m_height))) {
				DISTANCE = NORMALSPEED;
				int yMiddle = hitBox.y + (m_height/2);
				if ((m_model.dPressed) && (!m_model.m_underworld.isBlocked(hitBox.x + m_width, yMiddle))) {
					m_coord.translateX(DISTANCE);
				} else if ((m_model.qPressed) && (!m_model.m_underworld.isBlocked(hitBox.x, yMiddle))) {
					m_coord.translateX(-DISTANCE);
					
				} else if (!m_model.m_underworld.isBlocked(hitBox.x + m_width/2, hitBox.y + m_height)) {
					m_coord.translateY(DISTANCE);
				}
				hitBox.setLocation(m_coord.X(), m_coord.Y());
				return true;
			}
			return false;
		case Direction.Es:
			if ((m_model.m_underworld.isBlocked(hitBox.x + m_width, hitBox.y)) ||
			(m_model.m_underworld.isBlocked(hitBox.x + m_width, hitBox.y + m_height))) {
				DISTANCE = NORMALSPEED;
				int xMiddle = hitBox.x + (m_width/2);
				if ((m_model.zPressed) && (!m_model.m_underworld.isBlocked(xMiddle, hitBox.y))) {
					m_coord.translateY(-DISTANCE);
				} else if ((m_model.sPressed) && (!m_model.m_underworld.isBlocked(xMiddle, hitBox.y + m_height))) {
					m_coord.translateY(DISTANCE);
				} else if (!m_model.m_underworld.isBlocked(hitBox.x + m_width, hitBox.y + m_height/2)) {
					m_coord.translateX(DISTANCE);
				}
				hitBox.setLocation(m_coord.X(), m_coord.Y());
				return true;
			}
			return false;
		case Direction.Ws:
			int yLeft = hitBox.y + (SIZE / 2);
			if ((m_model.m_underworld.isBlocked(hitBox.x, yLeft)) || (m_model.m_underworld.isBlocked(hitBox.x, hitBox.y)) ||
			(m_model.m_underworld.isBlocked(hitBox.x, hitBox.y + m_height))) {
				DISTANCE = NORMALSPEED;
				int xMiddle = hitBox.x + (m_width/2);
				if ((m_model.zPressed) && (!m_model.m_underworld.isBlocked(xMiddle, hitBox.y))) {
					m_coord.translateY(-DISTANCE);
				} else if ((m_model.sPressed) && (!m_model.m_underworld.isBlocked(xMiddle, hitBox.y + m_height))) {
					m_coord.translateY(DISTANCE);
				} else if (!m_model.m_underworld.isBlocked(hitBox.x, hitBox.y + m_height/2)) {
					m_coord.translateX(-DISTANCE);
				}
				hitBox.setLocation(m_coord.X(), m_coord.Y());
				return true;
			}
			return false;
		default:
			return true;
		}
	}

	@Override
	public boolean move(Direction dir) {
		turn(dir);
		switch (m_direction.toString()) {
		case Direction.Ns:
			if (m_model.qPressed) {
				leftOrientation = true;
				m_coord.translate(-DISTANCE, -DISTANCE);
				break;
			}
			if (m_model.dPressed) {
				leftOrientation = false;
				m_coord.translate(DISTANCE, -DISTANCE);
				break;
			}
			m_coord.translate(0, -DISTANCE);
			break;
		case Direction.Ss:
			if (m_model.qPressed) {
				leftOrientation = true;
				m_coord.translate(-DISTANCE, DISTANCE);
				break;
			}
			if (m_model.dPressed) {
				leftOrientation = false;
				m_coord.translate(DISTANCE, DISTANCE);
				break;
			}
			m_coord.translate(0, DISTANCE);
			break;
		case Direction.Es:
			leftOrientation = false;
			if (m_model.zPressed) {
				m_coord.translate(DISTANCE, -DISTANCE);
				break;
			}
			if (m_model.sPressed) {
				m_coord.translate(DISTANCE, DISTANCE);
				break;
			}
			m_coord.translate(DISTANCE, 0);
			break;
		case Direction.Ws:
			leftOrientation = true;
			if (m_model.zPressed) {
				m_coord.translate(-DISTANCE, -DISTANCE);
				break;
			}
			if (m_model.sPressed) {
				m_coord.translate(-DISTANCE, DISTANCE);
				break;
			}
			m_coord.translate(-DISTANCE, 0);
			break;
		default:
			return false;
		}
		hitBox.setLocation(m_coord.X(), m_coord.Y());
		return true;
	}

	@Override
	public boolean hit(Direction dir) {
		fragmentsPicked = -1;
		escape = true;
		m_model.m_underworld.activateGate();
		currentAction = Action.MOVE;
		resetAnim();
		return true;
	}

	@Override
	public boolean wizz(Direction dir) {
		int x = m_coord.X();
		int y = m_coord.Y();
		if (x < 0) {
			m_coord.setX(Underworld.BORDERX);
		} else if (y < 0) {
			m_coord.setY(Underworld.BORDERY);
		} else if (x > Underworld.BORDERX) {
			m_coord.setX(0 - m_width);
		} else {
			m_coord.setY(0 - m_height);
		}
		return true;
	}

	@Override
	public boolean pop(Direction dir) {
		switch (dir.toString()) {
		case Direction.Ns:
			currentAction = Action.JUMP;
			resetAnim();
			escaped = true;
			m_imageIndex = currentIndex.length - 1;
			return true;
		case Direction.Ss:
			currentAction = Action.DEATH;
			resetAnim();
			dead = true;
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean pick(Direction dir) {
		fragmentsPicked = fragmentsPicked + 1;
		return true;
	}

	@Override
	public boolean jump(Direction dir) {
		if (dashAvailable) {
			DISTANCE = DASHSPEED;
			m_dashPaint = 0;
			dashAvailable = false;
			currentAction = Action.JUMP;
			m_dashElapsed = 0;
			resetAnim();
			return true;
		}
		return false;
	}

	@Override
	public boolean egg(Direction dir) {
		if ((lureAvailable) && (nbLure < MAX_LURE)) {
			try {
				int x = m_model.m_mouseCoord.X() - Lure.SIZE / 2;
				int y = m_model.m_mouseCoord.Y() - Lure.SIZE / 2;
				if (m_model.m_underworld.checkPosition(x, y, 0, Lure.SIZE))
					return false;
				addProjectile(Projectile.proj.LURE, new Coord(x, y), 0, this, m_direction);
				nbLure++;
				lureAvailable = false;
				m_lurePaint = 0;
				m_lureGenerationElapsed = 0;
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public void removeProjectile(Projectile projectile) {
		super.removeProjectile(projectile);
		nbLure--;
	}

	public void setVisibility(boolean bool) {
		hidden = bool;
	}

	public void getDamage() {
		if (!invincible)
			loseLife(20);
			m_lifePaint = (int) ((m_width * getLife()) / 100);
			invincible = true;
			m_invincibleElapsed = 0;
	}

	public static final int HEALTHHEIGHT = SIZE / 8;

	public void paint(Graphics g) {
		if (nbLure > 0) {
			for (int i = 0; i < nbLure; i++) {
				m_projectiles.get(i).paint(g);
			}
		}
		int x = m_coord.X();
		int y = m_coord.Y();
		if ((paint) || (dead)) {
			if (leftOrientation)
				g.drawImage(bImages[currentIndex[m_imageIndex]], x + SIZE, y, -SIZE, SIZE, null);
			else
				g.drawImage(bImages[currentIndex[m_imageIndex]], x, y, null);
		}
		if (invincible) {
			paint = !paint;
		}
		if (!dead) {
			g.setColor(Color.green);
			g.fillRect(m_coord.X(), m_coord.Y() - 2 * HEALTHHEIGHT, m_lifePaint, HEALTHHEIGHT);
			g.setColor(Color.cyan);
			g.fillRect(m_coord.X(), m_coord.Y() - (int) (3.5 * HEALTHHEIGHT), m_lurePaint, HEALTHHEIGHT);
			g.setColor(Color.blue);
			g.fillRect(m_coord.X(), m_coord.Y() - 5 * HEALTHHEIGHT, m_dashPaint, HEALTHHEIGHT);
			g.drawRect(hitBox.x, hitBox.y, m_width, m_height);
		}
	}

	public void tick(long elapsed) {
		m_imageElapsed += elapsed;
		if (m_imageElapsed > m_imageTick) {
			m_imageElapsed = 0;
			switch (currentAction) {
			case DEATH:
				m_imageIndex++;
				if (m_imageIndex >= currentIndex.length) {
					Game.game.gameOver = true;
				}
				return;
			case JUMP:
				if (escaped) {
					m_imageIndex--;
					if (m_imageIndex <= 0) {
						m_model.switchEnv(mode.ROOM);
						return;
					}
				} else {
					m_imageIndex++;
					if (escape) {
						if (m_imageIndex >= currentIndex.length) {
							currentAction = Action.MOVE;
							resetAnim();
							DISTANCE = NORMALSPEED;
						}
					} else {
						if (m_imageIndex >= currentIndex.length) {
							currentAction = Action.DEFAULT;
							resetAnim();
							DISTANCE = NORMALSPEED;
						}
					}
				}
				break;
			case DEFAULT:
			case MOVE:
				m_imageIndex++;
				if (m_imageIndex >= currentIndex.length) {
					m_imageIndex = 0;
				}
				break;
			default:
				break;
			}
		}
		m_stepElapsed += elapsed;
		if (m_stepElapsed > m_stepTick) {
			m_stepElapsed -= m_stepTick;
			if (invincible) {
				m_invincibleElapsed += elapsed;
				if (m_invincibleElapsed > 500) {
					invincible = false;
					paint = true;
				}
			}
			if (!dashAvailable) {
				m_dashElapsed += elapsed;
				m_dashPaint = (int) ((m_width * m_dashElapsed) / 2000);
				if (m_dashElapsed > 2000) {
					dashAvailable = true;
					m_dashElapsed = 0;
					m_dashPaint = m_width;
				}
				
			}
			if (!lureAvailable) {
				m_lureGenerationElapsed += elapsed;
				m_lurePaint = (int) ((m_width * m_lureGenerationElapsed) / 500);
				if (m_lureGenerationElapsed > 500) {
					lureAvailable = true;
					m_lureGenerationElapsed = 0;
					m_lurePaint = m_width;
				}
			}
			m_automaton.step(this);
		}
		if (nbLure > 0) {
			for (int i = 0; i < nbLure; i++) {
				m_projectiles.get(i).tick(elapsed);
			}
		}
	}
}
