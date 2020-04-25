package underworld;

import java.io.IOException;

import javax.imageio.ImageIO;

import java.io.File;
import java.awt.Graphics;
import java.awt.Image;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import environnement.Element;
import game.Controller;
import game.Model;
import player.Character;

public class PlayerSoul extends Character {
	
	public static final int SIZE = (int) (1.5 * Element.SIZE);
	
	boolean hidden;
	Image m_images[];
	long m_imageElapsed;
	
	boolean qPressed, zPressed, dPressed, sPressed, vPressed, spacePressed;
	boolean leftOrientation;
	
	/* A faire :
	 * Animation dash (Jump)
	 * Animation leurre (Egg)
	 */
	
	public PlayerSoul(Automaton automaton, int x, int y, Direction dir, Model model) throws IOException {
		super(automaton, x, y, dir, model, 100, 100, 0, 0, 0);
		hidden = false;
		loadImage();
	}
	
	private void loadImage() {
		m_images = new Image[4];
		File imageFile;
		Image image;
		m_image_index = 0;	
		m_imageElapsed = 0;
		qPressed = false;
		zPressed = false;
		dPressed = false;
		sPressed = false;
		vPressed = false;
		spacePressed = false;
		leftOrientation = false;
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
	
	private boolean contains(Cloud cloud) {
		return ((getCoord().X() >= cloud.getCoord().X()) && (getCoord().X() <= cloud.xMax) && (getCoord().Y() <= cloud.getCoord().Y()) && (getCoord().Y() >= cloud.yMax));
	}
	
	@Override
	public boolean cell(Direction dir, Category cat) {
		if ((dir.toString().equals("H")) && (cat.toString().equals("O"))) {
			Cloud clouds[] = getModel().m_underworld.m_clouds;
			for (int i = 0 ; i < clouds.length ; i++) {
				if  (contains(clouds[i]))
					return true;
			}
		}
		return false;
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
	public boolean pop(Direction dir) {
		hidden = true;
		return true;
	}
	
	@Override
	public boolean wizz(Direction dir) {
		hidden = false;
		return true;
	}
	
	@Override
	public boolean turn(Direction dir) {
		return super.turn(dir);
	}
	
	public static final int DISTANCE = 1;
	
	@Override
	public boolean move(Direction dir) {
		turn(dir);
		if ((sPressed) && (zPressed) && (qPressed) && (dPressed)) {
			return false;
		}
		switch (m_direction.toString()) {
			case "N" : 
				if ((sPressed) || ((qPressed) && (dPressed)))
					return false;
				if (qPressed)
					getCoord().translate(-DISTANCE, - DISTANCE);
				else if (dPressed)
					getCoord().translate(DISTANCE, - DISTANCE);
				else 
					getCoord().translate(0, - DISTANCE);
				return true;
			case "S" : 
				if ((zPressed) || ((qPressed) && (dPressed)))
					return false;
				if (qPressed)
					getCoord().translate(-DISTANCE, DISTANCE);
				else if (dPressed)
					getCoord().translate(DISTANCE, DISTANCE);
				else 
					getCoord().translate(0, DISTANCE);
				return true;
			case "E" :
				if ((qPressed) || ((zPressed) && (sPressed)))
					return false;
				if (zPressed)
					getCoord().translate(DISTANCE, - DISTANCE);
				else if (sPressed)
					getCoord().translate(DISTANCE, DISTANCE);
				else
					getCoord().translate(DISTANCE, 0);
				return true;
			case "W" :
				if ((dPressed) || ((zPressed) && (sPressed)))
					return false;
				if (zPressed)
					getCoord().translate(-DISTANCE, - DISTANCE);
				else if (sPressed)
					getCoord().translate(-DISTANCE, DISTANCE);
				else
					getCoord().translate(-DISTANCE, 0);
				return true;
			default :
				return false;
		}
	}
	
	@Override
	public boolean jump(Direction dir) {
		switch (m_direction.toString()) {
		case "N" : 
			getCoord().translate(0, - 40);
			return true;
		case "S" : 
			getCoord().translate(0, 40);
			return true;
		case "E" :
			getCoord().translate(40, 0);
			return true;
		case "W" :
			getCoord().translate(-40, 0);
			return true;
		default :
			return false;
		}
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
			if (hidden)
				g.drawOval( m_coord.X() , m_coord.Y(), SIZE / 2, SIZE / 2);
		}
	}
	
	public void tick(long elapsed) {
		m_imageElapsed += elapsed;
		if (m_imageElapsed > 200) {
			m_imageElapsed = 0;
			m_image_index++;
		}
		m_automaton.step(this);
		
	}
	
	
	
	
	

	
}
