package underworld;

import java.io.IOException;

import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import environnement.Element;
import game.Controller;
import game.Coord;
import game.Model;
import player.Character;

public class PlayerSoul extends Character {

	public static final int SIZE = (int) Element.SIZE;

	int m_width, m_height = SIZE;

	boolean hidden;
	Image m_images[];
	long m_imageElapsed;
	long m_dashTimer;
	long m_lureTimer;

	int m_centerX, m_centerY;


	boolean qPressed, zPressed, dPressed, sPressed, vPressed, spacePressed;
	boolean leftOrientation;
	boolean dashAvailable, lureAvailable;
	Lure lure;

	public PlayerSoul(Automaton automaton, int x, int y, Direction dir, Model model) throws IOException {
		super(automaton, x, y, dir, model, 100, 100, 0, 0, 0);
		m_width = SIZE;
		m_height = SIZE;
		hidden = false;
		m_dashTimer = 0;
		qPressed = false;
		zPressed = false;
		dPressed = false;
		sPressed = false;
		vPressed = false;
		spacePressed = false;
		leftOrientation = false;
		dashAvailable = true;
		lureAvailable = true;
		hitBox = new Rectangle(m_coord.X(), m_coord.Y(), SIZE, SIZE);
		loadImage();
	}

	private void loadImage() {
		m_images = new Image[UnderworldParam.playerSoulImage.length];
		File imageFile;
		m_image_index = 0;
		m_imageElapsed = 0;
		try {
			for (int i = 0; i < 4; i++) {
				imageFile = new File(UnderworldParam.playerSoulImage[i]);
				m_images[i] = ImageIO.read(imageFile);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int centerX() {
		return m_centerX;
	}

	public int centerY() {
		return m_centerY;
	}

	public boolean setVisibility(boolean visibility) {
		hidden = visibility;
		return true;
	}
	
	public void Pressed() {
		vPressed = true;
	}
	
	public void Released() {
		vPressed = false;
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
		case Controller.K_SPACE:
			spacePressed = pressed;
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
		default:
			return false;
		}
	}

	@Override
	public boolean turn(Direction dir) {
		return super.turn(dir);
	}

	public static final int DISTANCE = 3;
	public static final int DASH = 500;

	@Override
	public boolean move(Direction dir) {
		turn(dir);
		switch (m_direction.toString()) {
		case "N":
			getCoord().translate(0, -DISTANCE);
			break;
		case "S":
			getCoord().translate(0, DISTANCE);
			break;
		case "E":
			getCoord().translate(DISTANCE, 0);
			break;
		case "W":
			getCoord().translate(-DISTANCE, 0);
			break;
		default:
			return false;
		}
		hitBox.setLocation(m_coord.X(), m_coord.Y());
		return true;
	}

	@Override
	public boolean mydir(Direction dir) {
		return dir.toString().equals(m_direction.toString());
	}

	@Override
	public boolean cell(Direction dir, Category cat) {
		switch (dir.toString()) {
		case "N":
			int xUp = hitBox.x + (SIZE / 2);
			if (checkBlock(xUp, hitBox.y)) {
				getCoord().setY(getBlockCoord(xUp, hitBox.y).Y() + Element.SIZE);
				return true;
			}
			return false;
		case "S":
			int xDown = hitBox.x + (SIZE / 2);
			if (checkBlock(xDown, hitBox.y + SIZE)) {
				getCoord().setY(getBlockCoord(xDown, hitBox.y + SIZE).Y() - Element.SIZE);
				return true;
			}
			return false;
		case "E":
			int yRight = hitBox.y + (SIZE/ 2);
			if (checkBlock(hitBox.x + SIZE, yRight)) {
				getCoord().setX(getBlockCoord(hitBox.x + SIZE, yRight).X() - Element.SIZE);
				return true;
			}
			return false;
		case "W":
			int yLeft = hitBox.y + (SIZE/ 2);
			if (checkBlock(hitBox.x, yLeft)) {
				getCoord().setX(getBlockCoord(hitBox.x, yLeft).X() + Element.SIZE);
				return true;
			}
			return false;
		default:
			return true;
		}
	}

	@Override
	public boolean jump(Direction dir) {
		if (dashAvailable) {
			switch (m_direction.toString()) {
			case "N":
				getCoord().translate(0, -DASH);
				dashAvailable = false;
				return true;
			case "S":
				getCoord().translate(0, DASH);
				dashAvailable = false;
				return true;
			case "E":
				getCoord().translate(DASH, 0);
				dashAvailable = false;
				return true;
			case "W":
				getCoord().translate(-DASH, 0);
				dashAvailable = false;
				return true;
			default:
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean egg(Direction dir) {
		if (lureAvailable) {
			int x = m_model.m_mouseCoord.X() - m_model.getXDecalage();
			int y = m_model.m_mouseCoord.Y() - m_model.getYDecalage();
			if (checkBlock(x,y))
				return false;
			lure = new Lure(m_model.lureAutomaton, x, y, 0, this, m_model);
			lureAvailable = false;
			return true;
		}
		return false;
	}

	public boolean checkBlock(int x, int y) {
		return m_model.m_underworld.isBlocked(x, y);
	}

	public Coord getBlockCoord(int x, int y) {
		return m_model.m_underworld.blockBot(x, y);
	}

	public void paint(Graphics g) {
		if (m_images != null) {
			if (m_image_index >= 4) {
				m_image_index = 0;
			}
			if (leftOrientation)
				g.drawImage(m_images[m_image_index], m_coord.X() + SIZE, m_coord.Y(), -SIZE, SIZE, null);
			else
				g.drawImage(m_images[m_image_index], m_coord.X(), m_coord.Y(), SIZE, SIZE, null);
			if (lure != null)
				lure.paint(g);
			g.setColor(Color.blue);
			g.drawRect(hitBox.x, hitBox.y, m_width, m_height);
		}
	}

	public void tick(long elapsed) {
		m_imageElapsed += elapsed;
		m_dashTimer += elapsed;
		if (m_imageElapsed > 200) {
			m_imageElapsed = 0;
			m_image_index++;
		}
		if (m_dashTimer > 5000) {
			dashAvailable = true;
			m_dashTimer = 0;
		}
		if (!(lureAvailable)) {
			lure.tick(elapsed);
			m_lureTimer += elapsed;
			if (lure.isDestroying()) {
				// On ne fait rien
			} else if (lure.isDestroyed()) {
				m_lureTimer = 0;
				lure = null;
				lureAvailable = true;
			} else if (m_lureTimer >= 10000) {
				m_lureTimer = 0;
				lure.elapsed();
			}
		}
		m_automaton.step(this);
	}

}
