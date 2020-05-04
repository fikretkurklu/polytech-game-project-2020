package underworld;

import java.io.IOException;

import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import environnement.Element;
import game.Controller;
import game.Coord;
import game.Game;
import game.Model;
import player.Character;

public class PlayerSoul extends Character {

	public static final int SIZE = (int) Element.SIZE;

	int m_width, m_height = SIZE;

	Image m_images[];
	long m_imageElapsed;
	long m_dashTimer;
	long m_lureTimer;

	int sizeAnimation = UnderworldParam.playerSoulImage.length;
	int sizeDashAnimation = UnderworldParam.lureApparitionImage.length + sizeAnimation;
	int sizeEscapeAnimation = sizeAnimation + sizeDashAnimation;
	int sizeDeathAnimation = sizeEscapeAnimation + 34;

	int fragmentsPicked = 0;

	boolean qPressed, zPressed, dPressed, sPressed, vPressed, ePressed, spacePressed;
	boolean leftOrientation;

	public static final int NORMAL = 1;
	public static final int DASH = 2;
	public static final int ESCAPE = 3;
	public static final int ESCAPED = 4;
	public static final int DAMAGE = 5;
	public static final int DEAD = 6;
	public static final int HIDDEN = 7;

	int animationMode = NORMAL;

	boolean hidden, escape, escapedOrDead;
	boolean dashAvailable, lureAvailable, moveAvailable;
	Lure lure;

	public PlayerSoul(Automaton automaton, int x, int y, Direction dir, Model model) throws IOException {
		super(automaton, x, y, dir, model, 100, 100, 0, 0, 0);
		m_width = SIZE;
		m_height = SIZE;
		m_dashTimer = 0;
		qPressed = false;
		zPressed = false;
		dPressed = false;
		sPressed = false;
		vPressed = false;
		ePressed = false;
		spacePressed = false;
		leftOrientation = false;
		dashAvailable = true;
		lureAvailable = true;
		moveAvailable = true;
		hidden = false;
		escape = false;
		escapedOrDead = false;
		animationMode = NORMAL;
		hitBox = new Rectangle(m_coord.X(), m_coord.Y(), SIZE, SIZE);
		loadImage();
	}

	private void loadImage() {
		m_images = new Image[sizeDeathAnimation];
		File imageFile;
		m_image_index = 0;
		m_imageElapsed = 0;
		try {
			for (int i = 0; i < sizeAnimation; i++) {
				imageFile = new File(UnderworldParam.playerSoulImage[i]);
				m_images[i] = ImageIO.read(imageFile);
			}
			for (int j = sizeAnimation; j < sizeDashAnimation; j++) {
				imageFile = new File(UnderworldParam.lureApparitionImage[j - sizeAnimation]);
				m_images[j] = ImageIO.read(imageFile);
			}
			for (int k = sizeDashAnimation; k < sizeEscapeAnimation; k++) {
				imageFile = new File(UnderworldParam.playerSoulEscapeImage[k - sizeDashAnimation]);
				m_images[k] = ImageIO.read(imageFile);
			}
			for (int l = sizeEscapeAnimation; l < 6 + sizeEscapeAnimation; l++) {
				imageFile = new File(UnderworldParam.playerSoulDeathImage[l - sizeEscapeAnimation]);
				m_images[l] = ImageIO.read(imageFile);
			}
			BufferedImage[] deathTmp = m_model.loadSprite(UnderworldParam.deathSprite, 7, 7);
			int index = 6 + sizeEscapeAnimation;
			for (int m = 14; m < 42; m++) {
				m_images[index] = deathTmp[m];
				index++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setPressed(int keyCode, boolean pressed) {
		switch (keyCode) {
		case Controller.K_Z:
			zPressed = pressed;
			break;
		case Controller.K_Q:
			qPressed = pressed;
			if (qPressed)
				leftOrientation = true;
			break;
		case Controller.K_S:
			sPressed = pressed;
			break;
		case Controller.K_D:
			dPressed = pressed;
			if (dPressed)
				leftOrientation = false;
			break;
		case Controller.K_E:
			ePressed = pressed;
			break;
		case Controller.K_SPACE:
			spacePressed = pressed;
			break;
		case Controller.K_V:
			vPressed = pressed;
			break;
		}
	}

	@Override
	public boolean key(int keycode) {
		switch (keycode) {
		case Controller.K_Z:
			return zPressed;
		case Controller.K_Q:
			return qPressed;
		case Controller.K_S:
			return sPressed;
		case Controller.K_D:
			return dPressed;
		case Controller.K_SPACE:
			return spacePressed;
		case Controller.K_V:
			return vPressed;
		case Controller.K_E:
			return ePressed;
		default:
			return false;
		}
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
				if ((m_model.m_underworld.m_clouds[i].contains(xCenter, yCenter))){
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
			if (checkBlock(xUp, hitBox.y)) {
				m_coord.setY(getBlockCoord(xUp, hitBox.y).Y() + Element.SIZE);
				DISTANCE = NORMALSPEED;
				return true;
			}
			return false;
		case Direction.Ss:
			int xDown = hitBox.x + (SIZE / 2);
			if (checkBlock(xDown, hitBox.y + SIZE)) {
				m_coord.setY(getBlockCoord(xDown, hitBox.y + SIZE).Y() - Element.SIZE);
				DISTANCE = NORMALSPEED;
				return true;
			}
			return false;
		case Direction.Es:
			int yRight = hitBox.y + (SIZE / 2);
			if (checkBlock(hitBox.x + SIZE, yRight)) {
				m_coord.setX(getBlockCoord(hitBox.x + SIZE, yRight).X() - Element.SIZE);
				DISTANCE = NORMALSPEED;
				return true;
			}
			return false;
		case Direction.Ws:
			int yLeft = hitBox.y + (SIZE / 2);
			if (checkBlock(hitBox.x, yLeft)) {
				m_coord.setX(getBlockCoord(hitBox.x, yLeft).X() + Element.SIZE);
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
			if (qPressed) {
				m_coord.translate(-DISTANCE, -DISTANCE);
				break;
			}
			if (dPressed) {
				m_coord.translate(DISTANCE, -DISTANCE);
				break;
			}
			m_coord.translate(0, -DISTANCE);
			break;
		case Direction.Ss:
			if (qPressed) {
				m_coord.translate(-DISTANCE, DISTANCE);
				break;
			}
			if (dPressed) {
				m_coord.translate(DISTANCE, DISTANCE);
				break;
			}
			m_coord.translate(0, DISTANCE);
			break;
		case Direction.Es:
			if (zPressed) {
				m_coord.translate(DISTANCE, -DISTANCE);
				break;
			}
			if (sPressed) {
				m_coord.translate(DISTANCE, DISTANCE);
				break;
			}
			m_coord.translate(DISTANCE, 0);
			break;
		case Direction.Ws:
			if (zPressed) {
				m_coord.translate(-DISTANCE, -DISTANCE);
				break;
			}
			if (sPressed) {
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
		m_image_index = sizeDashAnimation;
		return true;
	}

	@Override
	public boolean pop(Direction dir) {
		switch (dir.toString()) {
		case Direction.Ns:
			animationMode = ESCAPED;
			m_image_index = sizeAnimation;
			return true;
		case Direction.Ss:
			animationMode = DEAD;
			m_image_index = sizeEscapeAnimation;
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
			m_image_index = sizeAnimation;
			return true;
		}
		return false;
	}

	@Override
	public boolean egg(Direction dir) {
		if (lureAvailable) {
			int x = m_model.m_mouseCoord.X() - m_model.getXDecalage();
			int y = m_model.m_mouseCoord.Y() - m_model.getYDecalage();
			if (checkBlock(x, y))
				return false;
			lure = new Lure(m_model.lureAutomaton, x, y, 0, this, m_model);
			lureAvailable = false;
			return true;
		}
		return false;
	}
	
	public void setVisibility(boolean bool) {
		hidden = bool;
	}

	public boolean checkBlock(int x, int y) {
		return m_model.m_underworld.isBlocked(x, y);
	}

	public Coord getBlockCoord(int x, int y) {
		return m_model.m_underworld.blockBot(x, y);
	}

	public void getDamage() {
		loseLife(20);
	}

	public static final int HEALTHHEIGHT = SIZE / 8;

	public void paint(Graphics g) {
		if (hidden)
			return;
		if (escapedOrDead)
			return;
		if (m_images != null) {
			if (leftOrientation)
				g.drawImage(m_images[m_image_index], m_coord.X() + SIZE, m_coord.Y(), -SIZE, SIZE, null);
			else
				g.drawImage(m_images[m_image_index], m_coord.X(), m_coord.Y(), SIZE, SIZE, null);
			if (lure != null)
				lure.paint(g);
			g.setColor(Color.green);
			g.fillRect(m_coord.X(), m_coord.Y() - 2 * HEALTHHEIGHT, (int) ((m_width * getLife()) / 100), HEALTHHEIGHT);
			g.setColor(Color.blue);
			g.drawRect(hitBox.x, hitBox.y, m_width, m_height);
		}
	}

	public void tick(long elapsed) {
		if (escapedOrDead)
			return;
		m_imageElapsed += elapsed;
		if (m_imageElapsed > 200) {
			m_imageElapsed = 0;
			m_image_index++;
			switch (animationMode) {
			case DEAD:
				if (m_image_index >= sizeDeathAnimation) {
					escapedOrDead = true;
					Game.game.gameOver = true;
				}
				return;
			case ESCAPED:
				if (m_image_index >= sizeDashAnimation)
					escapedOrDead = true;
				return;
			case NORMAL:
				if (m_image_index >= sizeAnimation) {
					m_image_index = 0;
				}
				break;
			case ESCAPE:
				if (m_image_index >= sizeEscapeAnimation) {
					m_image_index = sizeDashAnimation;
				}
				break;
			case DASH:
				if (m_image_index >= sizeDashAnimation) {
					if (escape) {
						animationMode = ESCAPE;
						m_image_index = sizeDashAnimation;
					} else {
						animationMode = NORMAL;
						m_image_index = 0;
					}
					DISTANCE = NORMALSPEED;
				}
				break;
			}
		}
		if (!(lureAvailable)) {
			lure.tick(elapsed);
			m_lureTimer += elapsed;
			if (!lure.isDestroying()) {
				if (lure.isDestroyed()) {
					m_lureTimer = 0;
					lure = null;
					lureAvailable = true;
				} else if (m_lureTimer >= 5000) {
					m_lureTimer = 0;
					lure.elapsed();
				}
			}
		}
		if (!dashAvailable) {
			m_dashTimer += elapsed;
			if (m_dashTimer > 5000) {
				dashAvailable = true;
				m_dashTimer = 0;
			}
		}
		m_automaton.step(this);
	}

}
