package underworld;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

import automaton.Automaton;
import automaton.AutomatonLibrary;
import automaton.Direction;
import game.Coord;
import game.Model;
import environnement.Element;

public class Underworld {
	public final static int MAX_CLOUDS = 7;
	public final static int MAX_GHOSTS = 10;
	public final int MAX_FRAGMENTS = 4;
	
	boolean gateCreated = false;
	boolean playerCreated = false;
	public boolean gameOver = false;

	public PlayerSoul m_player;
	AutomatonLibrary m_al;
	String mapFile;
	int m_width, m_height;
	Element[] m_elements;
	int ambiance = 1;
	Coord startCoord;
	int nbRow;
	int nbCol;
	Cloud[] m_clouds;
	Ghost[] m_ghosts;
	Fragment[] m_fragments;
	Gate m_gate;
	UnderworldEmptySpaceImageManager ESIM;
	UndInnerWallManager UIWM;
	Automaton cloudAutomaton, wallAutomaton, ghostAutomaton, fragmentAutomaton, gateAutomaton;
	UndWallImageManager UWIM;
	AutomatonLibrary m_AL;
	Model m_model;
	Image background;
	public Image[] ghostImages, playerImages;
	private long m_BlockAElapsed;
	private int m_RealWidth;
	private int m_RealHeight;

	public Underworld(AutomatonLibrary AL, int width, int height, Model model) {
		m_model = model;
		m_al = AL;
		m_width = width;
		m_height = height;
		startCoord = new Coord(1000, 1000);
		ambiance = (int) (Math.random() * UnderworldParam.nbAmbiance) + 1;
		BufferedReader f;
		ESIM = new UnderworldEmptySpaceImageManager(ambiance);
		UWIM = new UndWallImageManager(ambiance);
		UIWM = new UndInnerWallManager(ambiance);
		m_clouds = new Cloud[MAX_CLOUDS];
		m_ghosts = new Ghost[MAX_GHOSTS];
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
			background = ImageIO.read(new File(UnderworldParam.backgroundFile));
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
			m_elements = new Element[nbRow * nbCol];
			for (int i = 0; i < nbRow; i++) {
				String[] actualLigne = f.readLine().split("/");
				for (int j = 0; j < nbCol; j++) {
					m_elements[i * nbCol + j] = CodeElement(actualLigne[j], j * Element.SIZE, i * Element.SIZE);
				}
			}
			f.close();
			generateClouds(m_clouds);
			ghostImages = loadGhostImage();
			playerImages = loadPlayerImage();
			generateGhosts(m_ghosts);
			generateFragments(m_fragments);
			generateGate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static final int XMAX = 3784;
	public static final int XMIN = 1290;
	public static final int YMAX = 3784;
	public static final int YMIN = 172;
	
	public static int HITBOXDIM;
	public static int HITBOXSIZE;

	private void generateGhosts(Ghost[] ghosts) {
		Direction [] dirs = { Direction.N, Direction.E, Direction.W, Direction.S };
		Direction dir = dirs[(int) (Math.random() * dirs.length)];
		HITBOXDIM = 0;
		HITBOXSIZE = Element.SIZE;
		for (int i = 0; i < ghosts.length; i++) {
			ghosts[i] = new Ghost(dir, generatePosition(), ghostAutomaton, m_model, ghostImages);
			if (dir == Direction.W)
				ghosts[i].leftOrientation = true;
		}
	}
	
	private void generateClouds(Cloud[] clouds) {
		int randomX;
		for (int i = 0; i < clouds.length; i++) {
			randomX = (int) (Math.random() * (4558));
			clouds[i] = new Cloud(cloudAutomaton, new Coord(randomX, (i + 1) * 500), m_model);
		}
	}
	
	private void generateFragments(Fragment[] fragments) {
		HITBOXDIM = - (int)(Element.SIZE/1.5);
		HITBOXSIZE = 2 * Element.SIZE;
		for (int i = 0; i < fragments.length; i++) {
			fragments[i] = new Fragment(fragmentAutomaton, generatePosition(), m_model);
		}
	}
	
	private void generateGate() {
		HITBOXDIM = Element.SIZE;
		HITBOXSIZE = 2 * Element.SIZE;
		m_gate = new Gate(gateAutomaton, generatePosition(), m_model);
	}
	
	private Coord generatePosition() {
		int x = XMIN + (int) (Math.random() * (XMAX - XMIN));
		int y = YMIN + (int) (Math.random() * (YMAX - YMIN));
		Rectangle hitBox = new Rectangle(x + HITBOXDIM, y + HITBOXDIM, HITBOXSIZE, HITBOXSIZE);
		int xRight = hitBox.x + HITBOXSIZE;
		int yRight = hitBox.y + HITBOXSIZE;
		while (isBlocked(xRight, hitBox.y) || isBlocked(xRight, yRight) 
				|| isBlocked(hitBox.x, yRight) || isBlocked(hitBox.x, hitBox.y)
				|| isBlocked(x, y)) {
			x = XMIN + (int) (Math.random() * (XMAX - XMIN));
			y = YMIN + (int) (Math.random() * (YMAX - YMIN));
			hitBox.setLocation(x + HITBOXDIM, y + HITBOXDIM);
			xRight = hitBox.x + HITBOXSIZE;
			yRight = hitBox.y + HITBOXSIZE;
		}
		return new Coord(x, y);
	}
	
	public void setPlayer(PlayerSoul player) {
		m_player = player;
		playerCreated = true;
	}


	public Element CodeElement(String code, int x, int y) throws Exception {
		Coord coord = new Coord(x, y);
		if (code.equals("")) {
			return new UnderworldEmptySpace(coord);
		} else if (code.equals("IW")) {
			return new UndInnerWall(coord, UIWM);
		} else if (code.contentEquals("OW_E")) {
			return new UndWall(coord, UWIM, "E", wallAutomaton);
		} else if (code.contentEquals("OW_S")) {
			return new UndWall(coord, UWIM, "S", wallAutomaton);
		} else if (code.contentEquals("OW_N")) {
			return new UndWall(coord, UWIM, "N", wallAutomaton);
		} else if (code.contentEquals("OW_W")) {
			return new UndWall(coord, UWIM, "W", wallAutomaton);
		} else if (code.equals("ES")) {
			return new UnderworldEmptySpace(coord, ESIM);
		}
		throw new Exception("Code room err: " + code);
	}

	public void paint(Graphics g, int width, int height, int x_decalage, int y_decalage) {
		m_width = width;
		m_height = height;
		g.drawImage(background, - x_decalage, - y_decalage, width, height, null);
		int y_start = (-y_decalage / Element.SIZE) * nbCol;
		int y_end = Math.min((y_start + (m_height / Element.SIZE + 2) * nbCol), m_elements.length);
		int x_start = (-x_decalage / Element.SIZE);
		int x_end = Math.min((x_start + width / Element.SIZE + 2), nbCol);
		for (int i = y_start; i < y_end; i += nbCol) {
			for (int j = i + x_start; j < i + x_end; j++) {
				m_elements[j].paint(g);
			}
		}
		for (int i = 0; i < m_fragments.length; i++) {
			m_fragments[i].paint(g);
		}
		m_player.paint(g);
		for (int i = 0; i < m_clouds.length; i++) {
			m_clouds[i].paint(g);
		}
		for (int i = 0; i < m_ghosts.length; i++) {
			m_ghosts[i].paint(g);
		}
		if (gateCreated)
			m_gate.paint(g);
	}

	public Coord getStartCoord() {
		return startCoord;
	}

	public void tick(long elapsed) {
		m_BlockAElapsed += elapsed;
		if (m_BlockAElapsed > 10000) {
			m_BlockAElapsed = 0;
			for (int i = 0; i < m_elements.length; i ++) {
				if (m_elements[i].getAutomaton() != null) {
					m_elements[i].getAutomaton().step(m_elements[i]);
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
		for (int i = 0; i < m_ghosts.length; i++) {
			m_ghosts[i].tick(elapsed);
		}
		for (int i = 0; i < m_fragments.length; i++) {
			m_fragments[i].tick(elapsed);
		}
		if (gateCreated)
			m_gate.tick(elapsed);
	}
	

	public boolean isBlocked(int x, int y) {
		int n = (x / Element.SIZE) + (y / Element.SIZE * nbCol);
		if (n >= 0 && n < nbRow * nbCol) {
			return m_elements[n].isSolid();
		}
		return true;
	}

	public int blockTop(int x, int y) {
		int n = (x / Element.SIZE) + (y / Element.SIZE * nbCol);
		if (n >= 0 && n < nbRow * nbCol) {
			return m_elements[n].getCoord().Y();
		} else {
			return 0;
		}
	}

	public Coord blockCoord(int x, int y) {
		int n = (x / Element.SIZE) + (y / Element.SIZE * nbCol);
		if (n >= 0 && n < nbRow * nbCol) {
			return m_elements[n].getCoord();
		} else {
			return null;
		}
	}

	public Coord blockBot(int x, int y) {
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
	
	public Image[] loadPlayerImage() {
		Image[] images = new Image[UnderworldParam.sizeDeathAnimation];
		File imageFile;
		try {
			for (int i = 0; i < UnderworldParam.sizeAnimation; i++) {
				imageFile = new File(UnderworldParam.playerSoulImage[i]);
				images[i] = ImageIO.read(imageFile);
			}
			for (int j = UnderworldParam.sizeAnimation; j < UnderworldParam.sizeDashAnimation; j++) {
				imageFile = new File(UnderworldParam.lureApparitionImage[j - UnderworldParam.sizeAnimation]);
				images[j] = ImageIO.read(imageFile);
			}
			for (int k = UnderworldParam.sizeDashAnimation; k < UnderworldParam.sizeEscapeAnimation; k++) {
				imageFile = new File(UnderworldParam.playerSoulEscapeImage[k - UnderworldParam.sizeDashAnimation]);
				images[k] = ImageIO.read(imageFile);
			}
			for (int l = UnderworldParam.sizeEscapeAnimation; l < 6 + UnderworldParam.sizeEscapeAnimation; l++) {
				imageFile = new File(UnderworldParam.playerSoulDeathImage[l - UnderworldParam.sizeEscapeAnimation]);
				images[l] = ImageIO.read(imageFile);
			}
			BufferedImage[] deathTmp = m_model.loadSprite(UnderworldParam.deathSprite, 7, 7);
			int index = 6 + UnderworldParam.sizeEscapeAnimation;
			for (int m = 14; m < 42; m++) {
				images[index] = deathTmp[m];
				index++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return images;
	}
	

	
	public Image[] loadGhostImage() {
		int len = UnderworldParam.ghostImage.length;
		Image[] images;
		images = new Image[len];
		File imageFile;
		Image image;
		for (int i = 0; i < len; i++) {
			imageFile = new File(UnderworldParam.ghostImage[i]);
			try {
				image =  ImageIO.read(imageFile);
				images[i] = image.getScaledInstance(Ghost.SIZE, Ghost.SIZE, 0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return images;
//				m_images[i] = image.getScaledInstance(SIZE, SIZE, 0);
	}
}
