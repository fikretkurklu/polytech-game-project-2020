package underworld;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import environnement.Element;
import game.Controller;
import game.Coord;
import game.Model;
import player.Character;

public class PlayerSoul extends Character {
	
	public static final int SIZE = (int) (1.5 * Element.SIZE);
	
	int m_width, m_height = SIZE;
	
	boolean hidden;
	Image m_images[];
	long m_imageElapsed;
	long m_dashTimer;
	long m_lureTimer;
	
	int xHitbox[];
	int yHitbox[];
	int m_centerX, m_centerY;
	
	int hitboxRad; // Unité utilisée afin de centrer la hitbox
	
	boolean qPressed, zPressed, dPressed, sPressed, vPressed, spacePressed;
	boolean leftOrientation;
	boolean dashAvailable;
	
	boolean setN, setS, setE, setW;
	
	Lure lure;
	
	public PlayerSoul(Automaton automaton, int x, int y, Direction dir, Model model) throws IOException {
		super(automaton, x, y, dir, model, 100, 100, 0, 0, 0);
		m_width = SIZE; 
		m_height = SIZE;
		hitboxRad = SIZE / 7;
		hidden = false;
		m_dashTimer = 0;
		qPressed = false;
		zPressed = false;
		dPressed = false;
		sPressed = false;
		vPressed = false;
		spacePressed = false;
		leftOrientation = false;
		dashAvailable = false;
		loadImage();
		xHitbox = new int[4];
		yHitbox = new int[4];
		calculateHitbox();
	}
	
	private void loadImage() {
		m_images = new Image[UnderworldParam.playerSoulImage.length];
		File imageFile;
		Image image;
		m_image_index = 0;	
		m_imageElapsed = 0;
		try {
			for (int i = 0 ; i < 4 ; i++) {
				imageFile = new File(UnderworldParam.playerSoulImage[i]);
				image = ImageIO.read(imageFile);
			    m_images[i] = image.getScaledInstance(SIZE, SIZE, 0);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void calculateHitbox() {
			
			int x = getCoord().X();
			int y = getCoord().Y();
			
			xHitbox[0] = x - m_width + hitboxRad;
			xHitbox[1] = x - hitboxRad;
			xHitbox[2] = x - hitboxRad;
			xHitbox[3] = x - m_width + hitboxRad;
			
			yHitbox[0] = y + hitboxRad;
			yHitbox[1] = y + hitboxRad;
			yHitbox[2] = y + m_height - hitboxRad;
			yHitbox[3] = y + m_height - hitboxRad;
			
			m_centerX = xHitbox[0] + (xHitbox[1] - xHitbox[0]) / 2;
			m_centerY = yHitbox[0] + (yHitbox[1] - yHitbox[0]) / 2;
			
			
		
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
	
	public void setPressed(int keyCode, boolean pressed) {
		switch(keyCode) {
		case Controller.K_Z :
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
		case Controller.K_V:
			vPressed = pressed;
			break;
		}
	}
	
	@Override
	public boolean key(int keycode) {
		switch(keycode) {
		case Controller.K_Z :
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
		default :
			return false;
		}
	}
	
	@Override
	public boolean turn(Direction dir) {
		return super.turn(dir);
	}
	
	public static final int DISTANCE = 2;
	public static final int DASH = 500;
	
	@Override
	public boolean move(Direction dir) {
		turn(dir);
		switch (m_direction.toString()) {
			case "N" : 
				getCoord().translate(0, - DISTANCE);
				break;
			case "S" : 
				getCoord().translate(0, DISTANCE);
				break;
			case "E" :
				getCoord().translate(DISTANCE, 0);
				break;
			case "W" :
				getCoord().translate(-DISTANCE, 0);
				break;
			default :
				return false;
		}
		calculateHitbox();
		return true;
	}
	
	@Override
	public boolean cell(Direction dir, Category cat) {
		switch (dir.toString()) {
		case "N" :
			int xUp = (xHitbox[1] + xHitbox[0])/2;
			if (!setN) {
				if (checkBlock(xUp, yHitbox[0] - DISTANCE)) {
					getCoord().setY(getBlockCoord(xUp, yHitbox[0] - DISTANCE).Y() + Element.SIZE - hitboxRad);
					setN = true;
					return true;
				}
				return false;
			} else {
				if (!(checkBlock(xUp, yHitbox[0] - DISTANCE))) {
					setN = false;
					return false;
				} 
				return true;
			}
		case "S" :
			int xDown = (xHitbox[2] + xHitbox[3])/2;
			if (!setS) {
				if (checkBlock(xDown, yHitbox[2] + DISTANCE)) {
					getCoord().setY(getBlockCoord(xDown, yHitbox[2] + DISTANCE).Y() - Element.SIZE - hitboxRad);
					setS = true;
					return true;
				}
				return false;
			} else {
				if (!(checkBlock(xDown, yHitbox[2] + DISTANCE)))  {
					setS = false;
					return false;
				}
				return true;
			}
		case "E" :
			int yRight = (yHitbox[1] + yHitbox[2])/2;
			if (!setE) {
				if (checkBlock(xHitbox[1] + DISTANCE, yRight)) {
					getCoord().setX(getBlockCoord(xHitbox[1] + DISTANCE, yRight).X() + hitboxRad);
					setE = true;
					return true;
				}
				return false;
			} else {
				if (!(checkBlock(xHitbox[1] + DISTANCE, yRight))) {
					setE = false;
					return false;
				}
				return true;
			}
		case "W" :
			int yLeft = (yHitbox[0] + yHitbox[3])/2;
			if (!setW) {
				if (checkBlock(xHitbox[0] - DISTANCE, yLeft)) {
					getCoord().setX(getBlockCoord(xHitbox[0] - DISTANCE, yLeft).X() + 2 * Element.SIZE + hitboxRad);
					setW = true;
					return true;
				}
				return false;
			} else {
				if (!(checkBlock(xHitbox[0] - DISTANCE, yLeft))) {
					setW = false;
					return false;
				}
				return true;
			}
		default :
			return true;
		}
	}
	
	@Override
	public boolean jump(Direction dir) {
		if (dashAvailable) {
			dashAvailable = false;
			switch (m_direction.toString()) {
			case "N" : 
				getCoord().translate(0, -DASH);
				return true;
			case "S" : 
				getCoord().translate(0, DASH);
				return true;
			case "E" :
				getCoord().translate(DASH, 0);
				return true;
			case "W" :
				getCoord().translate(-DASH, 0);
				return true;
			default :
				return false;
			}
		}
		return false;
	}
	
	@Override
	public boolean egg(Direction dir) {
		if (lure == null) {
			int x = getCoord().X() - SIZE;
			int y = getCoord().Y();
			switch (m_direction.toString()) {
			case "N":
				y = y - 500;
				break;
			case "S" :
				y = y + 500;
				break;
			case "E" :
				x = x + 500;
				break;
			case "W" :
				x = x - 500;
				break;
			default :
				return false;
			}
			lure = new Lure(m_model.lureAutomaton, x, y, 0, this, m_model);
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
				g.drawImage(m_images[m_image_index], m_coord.X(), m_coord.Y(), -SIZE, SIZE, null);
			else
				g.drawImage(m_images[m_image_index], m_coord.X() - SIZE , m_coord.Y(), SIZE, SIZE, null);
			if (lure != null) 
				lure.paint(g);
			g.setColor(Color.blue);
			g.drawPolygon(xHitbox, yHitbox, 4);
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
		if (lure != null) {
			lure.tick(elapsed);
			m_lureTimer += elapsed;
			if (lure.isDestroying()) {
				// On ne fait rien
			} else if (lure.isDestroyed()) {
				m_lureTimer = 0;
				lure = null;
			} else if (m_lureTimer >= 10000) {
				m_lureTimer = 0;
				lure.elapsed();
			}
		}
		m_automaton.step(this);
	}
	
}
