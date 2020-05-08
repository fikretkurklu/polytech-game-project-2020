package underworld;

import java.awt.Graphics;


import java.awt.Image;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.LinkedList;
import automaton.Automaton;
import automaton.Direction;
import entityFactory.Factory;
import entityFactory.Factory.Type;
import game.Coord;
import game.ImageLoader;
import game.Model;
import environnement.Element;

public class Underworld {
	public final static int MAX_CLOUDS = 6;
	public final static int MAX_GHOSTS = 30;// Nombre de ghosts max
	public final int MAX_FRAGMENTS = 4;

	boolean gateCreated = false;
	boolean playerCreated = false;
	public boolean gameOver = false;

	public PlayerSoul m_player;
	Factory m_factory;
	String mapFile;
	int m_width, m_height;
	Element[] m_elements, m_borders;
	int ambiance = 1;
	Coord startCoord;
	int nbRow;
	int nbCol;
	Cloud[] m_clouds;
	Iterator<Ghost> it;
	LinkedList<Ghost> m_ghosts;
	int nbGhosts;
	Fragment[] m_fragments;
	Gate m_gate;
	Model m_model;
	Automaton wallAutomaton;
	Image backgroundImage, cloudImage, cloudLeftUpImage, cloudRightUpImage, cloudLeftDownImage, cloudRightDownImage;
	private long m_BlockAElapsed;
	private int m_RealWidth;
	private int m_RealHeight;

	public Underworld(Factory factory, int width, int height, Model model) {
		m_factory = factory;
		m_model = model;
		m_width = width;
		m_height = height;
		startCoord = new Coord(500, 500);
		ambiance = (int) (Math.random() * UnderworldParam.nbAmbiance) + 1;
		BufferedReader f;
		m_clouds = new Cloud[MAX_CLOUDS];
		m_ghosts = new LinkedList<Ghost>();
		m_fragments = new Fragment[MAX_FRAGMENTS];
		try {
			wallAutomaton = m_factory.m_AL.getAutomaton("Block");
			mapFile = UnderworldParam.mapFile;
			f = new BufferedReader(new FileReader(new File(mapFile)));
			/*
			 * Le fichier suis cette syntaxe: Row:Col CODE/CODE/CODE/...../ ... ... ...
			 */
			String[] firstLine = f.readLine().split(":");
			nbRow = Integer.parseInt(firstLine[0]);
			nbCol = Integer.parseInt(firstLine[1]);
			m_RealWidth = nbCol * Element.SIZE;
			m_RealHeight = nbRow * Element.SIZE;
			m_elements = new Element[0];
			m_borders = new Element[0];
			for (int i = 0; i < nbRow; i++) {
				String[] actualLigne = f.readLine().split("/");
				for (int j = 0; j < nbCol; j++) {
					CodeElement(actualLigne[j], j, i);
				}
			}
			f.close();
			backgroundImage = ImageLoader.loadImage(UnderworldParam.backgroundFile, width, height);
			cloudImage = ImageLoader.loadImage(UnderworldParam.cloudImage[0], Cloud.SIZE);
			cloudLeftUpImage = ImageLoader.loadImage(UnderworldParam.cloudImage[1], 860);
			cloudRightUpImage = ImageLoader.loadImage(UnderworldParam.cloudImage[2], 860);
			cloudLeftDownImage = ImageLoader.loadImage(UnderworldParam.cloudImage[3], 860);
			cloudRightDownImage = ImageLoader.loadImage(UnderworldParam.cloudImage[4], 860);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static final int BORDERXLEFT = 2494;
	public static final int BORDERYLEFT = 2494;
	
	public static final int BORDERX = 2580;
	public static final int BORDERY = 2580;

	public static final int XMAX = 2150;
	public static final int XMIN = 430;
	public static final int YMAX = 2150;
	public static final int YMIN = 430;

	private void generateGhosts(int nbGhosts) {
		for (int i = 0; i < nbGhosts; i++) {
			addGhost();
		}
	}

	private void generateClouds(Cloud[] clouds) {
		int randomX;
		for (int i = 0; i < clouds.length; i++) {
			randomX = (int) (Math.random() * (XMAX));
			clouds[i] = (Cloud) m_factory.newEntity(Type.Cloud, Direction.E, new Coord(randomX, (i + 1) * 430), m_model, 0, null);
		}
	}

	private void generateFragments(Fragment[] fragments) {
		for (int i = 0; i < fragments.length; i++) {
			fragments[i] = (Fragment) m_factory.newEntity(Type.Fragment, Direction.E, generatePosition(-(int) (Element.SIZE / 1.5), 2 * Element.SIZE), m_model, 0, null);
		}
	}

	private void generateGate() {
		m_gate = (Gate) m_factory.newEntity(Type.Gate, Direction.E, generatePosition(Element.SIZE, 2 * Element.SIZE), m_model, 0, null);
	}

	public void setPlayer(PlayerSoul player) {
		m_player = player;
		playerCreated = true;
	}


	public void CodeElement(String code, int x, int y) throws Exception {
		Coord coord = new Coord(x * Element.SIZE, y * Element.SIZE);
		 if (code.equals("IW")) {
			Grow(false, new UndInnerWall(coord));
		} else if (code.equals(".")) {
			Grow(false, new UnderworldEmptySpace(coord));
		}
	}

	public void Grow(boolean isElement, Element add) {
		if (isElement) {
			Element[] tmp_elements = new Element[m_borders.length + 1];
			System.arraycopy(m_borders, 0, tmp_elements, 0, m_borders.length);
			tmp_elements[m_borders.length] = add;
			m_borders = tmp_elements;
		}
		Element[] tmp_background = new Element[m_elements.length + 1];
		System.arraycopy(m_elements, 0, tmp_background, 0, m_elements.length);
		tmp_background[m_elements.length] = add;
		m_elements = tmp_background;
	}

	public void paint(Graphics g, int width, int height, int x_decalage, int y_decalage) {
		m_width = width;
		m_height = height;
		g.drawImage(backgroundImage, -x_decalage, -y_decalage, null);
		for (int i = 0; i < m_fragments.length; i++) {
			m_fragments[i].paint(g);
		}
		m_player.paint(g);
		if (m_player.getCoord().Y() - (height/2) <= 860) {
			g.drawImage(cloudLeftUpImage, 0, 0, null);
			g.drawImage(cloudRightUpImage, 1720, 0, null);
		}
		if (m_player.getCoord().Y() + (height/2) >= 1720) {
			g.drawImage(cloudLeftDownImage, 0, 1720, null);
			g.drawImage(cloudRightDownImage, 1720, 1720, null);
		}
		for (int i = 0; i < m_clouds.length; i++) {
			m_clouds[i].paint(g);
		}
		it = m_ghosts.iterator();
		while (it.hasNext()) {
			it.next().paint(g);
		}
		if (gateCreated)
			m_gate.paint(g);
	}

	public Coord getStartCoord() {
		return startCoord;
	}

	public void tick(long elapsed) {
		m_BlockAElapsed += elapsed;
		if (m_BlockAElapsed > 1000) {
			m_BlockAElapsed = 0;
			for (int i = 0; i < m_borders.length; i++) {
				if (m_borders[i].getAutomaton() != null) {
					m_borders[i].getAutomaton().step(m_borders[i]);
				}
			}
		}
		for (int i = 0; i < m_clouds.length; i++) {
			if (m_clouds[i].outScreen) {
				m_clouds[i].reactivate();
			}
			m_clouds[i].tick(elapsed);
		}
		m_player.tick(elapsed);
		it = m_ghosts.iterator();
		while (it.hasNext()) {
			it.next().tick(elapsed);
		}
		for (int i = 0; i < m_fragments.length; i++) {
			m_fragments[i].tick(elapsed);
		}
		if (gateCreated)
			m_gate.tick(elapsed);
	}

	private Coord generatePosition(int hitboxDim, int hitboxSize) {
		int x = XMIN + (int) (Math.random() * (XMAX - XMIN));
		int y = YMIN + (int) (Math.random() * (YMAX - YMIN));
		Rectangle hitBox = new Rectangle(x + hitboxDim, y + hitboxDim, hitboxSize, hitboxSize);
		int xRight = hitBox.x + hitboxSize;
		int yRight = hitBox.y + hitboxSize;
		while (isBlocked(xRight, hitBox.y) || isBlocked(xRight, yRight) || isBlocked(hitBox.x, yRight)
				|| isBlocked(hitBox.x, hitBox.y) || isBlocked(x, y)) {
			x = XMIN + (int) (Math.random() * (XMAX - XMIN));
			y = YMIN + (int) (Math.random() * (YMAX - YMIN));
			hitBox.setLocation(x + hitboxDim, y + hitboxDim);
			xRight = hitBox.x + hitboxSize;
			yRight = hitBox.y + hitboxSize;
		}
		return new Coord(x, y);
	}

	public boolean isBlocked(int x, int y) {
		if ((x < 0) || (y < 0) || (x > BORDERXLEFT) || (y > BORDERYLEFT))
				return false;
		int n = (x / Element.SIZE) + (y / Element.SIZE * nbCol);
		if (n >= 0 && n < nbRow * nbCol) {
			return m_elements[n].isSolid();
		}
		return true;
	}

	public boolean checkPosition(int x, int y, int hitboxDim, int hitboxSize) {
		int xRight = x + hitboxSize;
		int yRight = y + hitboxSize;
		Rectangle hitBox = new Rectangle(x + hitboxDim, y + hitboxDim, hitboxSize, hitboxSize);
		return (isBlocked(xRight, hitBox.y) || isBlocked(xRight, yRight) || isBlocked(hitBox.x, yRight)
				|| isBlocked(hitBox.x, hitBox.y) || isBlocked(x, y));
	}

	public Coord blockCoord(int x, int y) {
		int n = (x / Element.SIZE) + (y / Element.SIZE * nbCol);
		if (n >= 0 && n < nbRow * nbCol) {
			return m_elements[n].getCoord();
		} else {
			return null;
		}
	}

	public void activateGate() {
		gateCreated = true;
	}

	public int getWitdh() {
		return m_RealWidth;
	}
	
	public int getHeight() {
		return m_RealHeight;
	}

	public void addGhost() {
		if (nbGhosts == MAX_GHOSTS) {
			for (int i = 0; i < 20; i++) { // Remove 20 Ghosts 
				m_ghosts.removeLast();
				nbGhosts--;
			}
			it = m_ghosts.iterator();
			while (it.hasNext()) {
				it.next().buff();
			}
		}
		m_ghosts.add((Ghost)m_factory.newEntity(Type.Ghost, Direction.E, generatePosition(0 , Ghost.SIZE), m_model, 0, null));
		nbGhosts++;
	}
	
	public void reset(int nbNewGhosts) {
		m_player = (PlayerSoul) m_factory.newEntity(Type.PlayerSoul, Direction.E, getStartCoord(), m_model, 0, null);
		generateGhosts(nbNewGhosts);
		generateClouds(m_clouds);
		generateFragments(m_fragments);
		generateGate();
		gateCreated = false;
	}

}
