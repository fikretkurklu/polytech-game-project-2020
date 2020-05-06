package underworld;

import java.io.IOException;
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
import player.Character;

public class PlayerSoul extends Character {

	public static final int SIZE = (int) Element.SIZE;
	public static final int REFRESH_TICK = 200;
	public static final int STEP_TICK = 4;
	public static final int MAX_LURE = 2;

	int m_width, m_height = SIZE;
	int nbLure;
	Image m_images[];
	long m_imageElapsed, m_stepElapsed, m_lureGenerationElapsed;
	long m_dashTimer;
	long m_lureTimer;

	int fragmentsPicked = 0;
	boolean leftOrientation;

	public static final int NORMAL = 1;
	public static final int DASH = 2;
	public static final int ESCAPE = 3;
	public static final int ESCAPED = 4;
	public static final int DAMAGE = 5;
	public static final int DEAD = 6;
	public static final int HIDDEN = 7;

	int animationMode;
	int m_lifePaint, m_dashPaint, m_lurePaint;

	boolean hidden, escape, escapedOrDead;
	boolean dashAvailable, lureAvailable, moveAvailable;
	Lure lure;

	public PlayerSoul(Automaton automaton, Coord c, Direction dir, Image[] images, Model model) throws IOException {
		super(automaton, c, dir, 100, 100, 0, 0, 0, model);
		m_width = SIZE;
		m_height = SIZE;
		m_dashTimer = 0;
		nbLure = 0;
		leftOrientation = false;
		dashAvailable = true;
		lureAvailable = true;
		moveAvailable = true;
		hidden = false;
		escape = false;
		escapedOrDead = false;
		animationMode = DASH;
		hitBox = new Rectangle(m_coord.X(), m_coord.Y(), SIZE, SIZE);
		m_image_index = UnderworldParam.sizePlayerAnimation;
		m_imageElapsed = 0;
		m_stepElapsed = 0;
		m_images = images;
		m_model = model;
	}
	
	public boolean lureActive () {
		return m_projectiles.size() > 0;
	}

	@Override
	public boolean closest(Category cat, Direction dir) {
		int xCenter = m_coord.X() + (m_width / 2);
		int yCenter = m_coord.Y() + (m_height / 2);
		if ((escape) && (cat == Category.G)) {
			return m_model.m_underworld.m_gate.contains(hitBox);
		}
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

	public static int DISTANCE = 2;
	public static final int NORMALSPEED = 2;
	public static final int DASHSPEED = 4;

	@Override
	public boolean cell(Direction dir, Category cat) {
		switch (dir.toString()) {
		case Direction.Ns:
			int xUp = hitBox.x + (SIZE / 2);
			if (m_model.m_underworld.isBlocked(xUp, hitBox.y)) {
				m_coord.setY(m_model.m_underworld.blockCoord(xUp, hitBox.y).Y() + Element.SIZE);
				DISTANCE = NORMALSPEED;
				return true;
			}
			return false;
		case Direction.Ss:
			int xDown = hitBox.x + (SIZE / 2);
			if (m_model.m_underworld.isBlocked(xDown, hitBox.y + SIZE)) {
				m_coord.setY(m_model.m_underworld.blockCoord(xDown, hitBox.y + SIZE).Y() - Element.SIZE);
				DISTANCE = NORMALSPEED;
				return true;
			}
			return false;
		case Direction.Es:
			int yRight = hitBox.y + (SIZE / 2);
			if (m_model.m_underworld.isBlocked(hitBox.x + SIZE, yRight)) {
				m_coord.setX(m_model.m_underworld.blockCoord(hitBox.x + SIZE, yRight).X() - Element.SIZE);
				DISTANCE = NORMALSPEED;
				return true;
			}
			return false;
		case Direction.Ws:
			int yLeft = hitBox.y + (SIZE / 2);
			if (m_model.m_underworld.isBlocked(hitBox.x, yLeft)) {
				m_coord.setX(m_model.m_underworld.blockCoord(hitBox.x, yLeft).X() + Element.SIZE);
				DISTANCE = NORMALSPEED;
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
	public boolean wizz(Direction dir) {
		fragmentsPicked = -1;
		escape = true;
		m_model.m_underworld.activateGate();
		animationMode = ESCAPE;
		m_image_index = UnderworldParam.sizePlayerDashAnimation;
		return true;
	}

	@Override
	public boolean pop(Direction dir) {
		switch (dir.toString()) {
		case Direction.Ns:
			animationMode = ESCAPED;
			m_image_index = UnderworldParam.sizePlayerAnimation;
			return true;
		case Direction.Ss:
			animationMode = DEAD;
			m_image_index = UnderworldParam.sizePlayerEscapeAnimation;
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
			dashAvailable = false;
			animationMode = DASH;
			m_image_index = UnderworldParam.sizePlayerAnimation;
			return true;
		}
		return false;
	}

	@Override
	public boolean egg(Direction dir) {
 	  if ((lureAvailable) && (m_projectiles.size() < MAX_LURE)) {
			try {
				int x = m_model.m_mouseCoord.X() - Lure.SIZE / 2;
				int y = m_model.m_mouseCoord.Y() - Lure.SIZE / 2;
				if (m_model.m_underworld.checkPosition(x, y, 0, Lure.SIZE))
					return false;
				addProjectile(Projectile.proj.LURE, new Coord(x, y), 0, this, m_direction);
				lureAvailable = false;
				m_lureGenerationElapsed = 0;
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	public void setVisibility(boolean bool) {
		hidden = bool;
	}

	public void getDamage() {
		loseLife(20);
	}

	public static final int HEALTHHEIGHT = SIZE / 8;

	public void paint(Graphics g) {
		if (m_projectiles.size() > 0) {
			for (int i = 0; i < m_projectiles.size(); i++) {
				m_projectiles.get(i).paint(g);
			}
		}
		if (hidden)
			return;
		if (escapedOrDead)
			return;
		if (m_images != null) {
			int x = m_coord.X();
			int y = m_coord.Y();
			if (leftOrientation)
				g.drawImage(m_images[m_image_index], x + SIZE, y, -SIZE, SIZE, null);
			else
				g.drawImage(m_images[m_image_index], x, y, SIZE, SIZE, null);
			g.setColor(Color.green);
			g.fillRect(m_coord.X(), m_coord.Y() - 2 * HEALTHHEIGHT, m_lifePaint, HEALTHHEIGHT);
			g.setColor(Color.blue);
			g.drawRect(hitBox.x, hitBox.y, m_width, m_height);
		}
	}

	public void tick(long elapsed) {
		if (escapedOrDead)
			return;
		m_imageElapsed += elapsed;
		if (m_imageElapsed > REFRESH_TICK) {
			m_imageElapsed = 0;
			m_image_index++;
			m_lifePaint = (int) ((m_width * getLife()) / 100);
			switch (animationMode) {
			case DEAD:
				if (m_image_index >= UnderworldParam.sizePlayerDeathAnimation) {
					escapedOrDead = true;
					Game.game.gameOver = true;
				}
				return;
			case ESCAPED:
				if (m_image_index >= UnderworldParam.sizePlayerDashAnimation)
					escapedOrDead = true;
				return;
			case NORMAL:
				if (m_image_index >= UnderworldParam.sizePlayerAnimation) {
					m_image_index = 0;
				}
				break;
			case ESCAPE:
				if (m_image_index >= UnderworldParam.sizePlayerEscapeAnimation) {
					m_image_index = UnderworldParam.sizePlayerDashAnimation;
				}
				break;
			case DASH:
				if (m_image_index >= UnderworldParam.sizePlayerDashAnimation) {
					if (escape) {
						animationMode = ESCAPE;
						m_image_index = UnderworldParam.sizePlayerDashAnimation;
					} else {
						animationMode = NORMAL;
						m_image_index = 0;
					}
					DISTANCE = NORMALSPEED;
				}
				break;
			}
		}
		m_stepElapsed += elapsed;
		if (m_stepElapsed > STEP_TICK) {
			m_stepElapsed -= STEP_TICK;
			if (!dashAvailable) {
				m_dashTimer += elapsed;
				if (m_dashTimer > 5000) {
					dashAvailable = true;
					m_dashTimer = 0;
				}
			}
			m_automaton.step(this);
		}
		m_lureGenerationElapsed += elapsed;
		if (m_lureGenerationElapsed > 1000) {
			lureAvailable = true;
			m_lureGenerationElapsed = 0;
		}
		if (m_projectiles.size() > 0) {
			for (int i = 0; i < m_projectiles.size(); i++) {
				m_projectiles.get(i).tick(elapsed);
			}
		}
	}
}
