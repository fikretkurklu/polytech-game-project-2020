package underworld;

import java.awt.Graphics;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.LinkedList;
import javax.imageio.ImageIO;

import automaton.Automaton;
import automaton.AutomatonLibrary;
import automaton.Direction;
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
	AutomatonLibrary m_al;
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
	UnderworldEmptySpaceImageManager ESIM;
	UndInnerWallManager UIWM;
	Automaton cloudAutomaton, wallAutomaton, ghostAutomaton, fragmentAutomaton, gateAutomaton;
	UndWallImageManager UWIM;
	AutomatonLibrary m_AL;
	Model m_model;
	Image backgroundImage, cloudImage, cloudLeftUpImage, cloudRightUpImage, cloudLeftDownImage, cloudRightDownImage;
	public Image[] ghostImages, playerImages, lureImages, fragmentImages, gateImages;
	private long m_BlockAElapsed;
	private int m_RealWidth;
	private int m_RealHeight;

	public Underworld(AutomatonLibrary AL, int width, int height, Model model) {
		m_model = model;
		m_al = AL;
		m_width = width;
		m_height = height;
		startCoord = new Coord(500, 500);
		ambiance = (int) (Math.random() * UnderworldParam.nbAmbiance) + 1;
		BufferedReader f;
		ESIM = new UnderworldEmptySpaceImageManager(ambiance);
		UWIM = new UndWallImageManager(ambiance);
		UIWM = new UndInnerWallManager(ambiance);
		m_clouds = new Cloud[MAX_CLOUDS];
		m_ghosts = new LinkedList<Ghost>();
		m_fragments = new Fragment[MAX_FRAGMENTS];
		m_AL = AL;
		try {
			wallAutomaton = m_AL.getAutomaton("Block");
			cloudAutomaton = m_AL.getAutomaton("Cloud");
			ghostAutomaton = m_AL.getAutomaton("Ghost");
			fragmentAutomaton = m_AL.getAutomaton("Fragment");
			gateAutomaton = m_AL.getAutomaton("Gate");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
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
			ghostImages = ImageLoader.loadGhostImage();
			playerImages = ImageLoader.loadPlayerImage();
			lureImages = ImageLoader.loadLureImage();
			fragmentImages = ImageLoader.loadImageSprite(UnderworldParam.fragmentSprite, 3, 3, Fragment.FragmentSIZE, Fragment.FragmentSIZE);
			gateImages = ImageLoader.loadImageSprite(UnderworldParam.gateSprite, 6, 6, Gate.GateSIZE, Gate.GateSIZE);
			generateClouds(m_clouds);
			generateGhosts();
			generateFragments(m_fragments);
			generateGate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static final int BORDERX = 2580;
	public static final int BORDERY = 2580;

	public static final int XMAX = 2150;
	public static final int XMIN = 430;
	public static final int YMAX = 2150;
	public static final int YMIN = 430;

	private void generateGhosts() {
		for (int i = 0; i < 40; i++) {
			addGhost();
		}
	}

	private void generateClouds(Cloud[] clouds) {
		int randomX;
		for (int i = 0; i < clouds.length; i++) {
			randomX = (int) (Math.random() * (XMAX));
			clouds[i] = new Cloud(cloudAutomaton, new Coord(randomX, (i + 1) * 430), m_model, cloudImage);
		}
	}

	private void generateFragments(Fragment[] fragments) {
		for (int i = 0; i < fragments.length; i++) {
			fragments[i] = new Fragment(fragmentAutomaton,
					generatePosition(-(int) (Element.SIZE / 1.5), 2 * Element.SIZE), m_model, fragmentImages);
		}
	}

	private void generateGate() {
		m_gate = new Gate(gateAutomaton, generatePosition(Element.SIZE, 2 * Element.SIZE), m_model, gateImages);
	}

	public void setPlayer(PlayerSoul player) {
		m_player = player;
		playerCreated = true;
	}


	public void CodeElement(String code, int x, int y) throws Exception {
		Coord coord = new Coord(x * Element.SIZE, y * Element.SIZE);
		if (code.equals(".")) {
			Grow(false, new UnderworldEmptySpace(coord));
		} else if (code.equals("IW")) {
			Grow(false, new UndInnerWall(coord, UIWM));
		} else if (code.contentEquals("OW_E")) {
			Grow(true, new UndWall(coord, UWIM, "E", wallAutomaton));
		} else if (code.contentEquals("OW_S")) {
			Grow(true, new UndWall(coord, UWIM, "S", wallAutomaton));
		} else if (code.contentEquals("OW_N")) {
			Grow(true, new UndWall(coord, UWIM, "N", wallAutomaton));
		} else if (code.contentEquals("OW_W")) {
			Grow(true, new UndWall(coord, UWIM, "W", wallAutomaton));
		} else if (code.equals("ES")) {
			Grow(false, new UnderworldEmptySpace(coord, ESIM));
		}
//		throw new Exception("Code room err: " + code);
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
//		int y_start = (-y_decalage / Element.SIZE) * nbCol;
//		int y_end = Math.min((y_start + (m_height / Element.SIZE + 2) * nbCol), m_elements.length);
//		int x_start = (-x_decalage / Element.SIZE);
//		int x_end = Math.min((x_start + width / Element.SIZE + 2), nbCol);
//		for (int i = y_start; i < y_end; i += nbCol) {
//			for (int j = i + x_start; j < i + x_end; j++) {
//				m_elements[j].paint(g);
//			}
//		}
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
		m_ghosts.add(new Ghost(Direction.E, generatePosition(0 , Ghost.SIZE), ghostAutomaton, m_model, ghostImages));
		nbGhosts++;
	}

}
