package underworld;

import java.awt.Graphics;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import automaton.Automaton;
import automaton.AutomatonLibrary;
import automaton.Direction;
import game.Coord;
import game.Model;
import environnement.Element;

public class Underworld {
	public final static int MAX_CLOUDS = 1;
	public final static int MAX_GHOSTS = 3;


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
	UnderworldEmptySpaceImageManager ESIM;
	UndInnerWallManager UIWM;
	Automaton cloudAutomaton, wallAutomaton, ghostAutomaton;
	UndWallImageManager UWIM;
	AutomatonLibrary m_AL;
	Model m_model;
	PlayerSoul m_player;
	
	
	
	

	public Underworld(AutomatonLibrary AL, int width, int height, PlayerSoul player, Model model) {
		m_model = model;
		m_al = AL;
		m_width = width;
		m_height = height;
		m_player = player;
		startCoord = new Coord();
		ambiance = (int) (Math.random() * UnderworldParam.nbAmbiance) + 1;
		BufferedReader f;
		ESIM = new UnderworldEmptySpaceImageManager(ambiance);
		UWIM = new UndWallImageManager(ambiance);
		UIWM = new UndInnerWallManager(ambiance);
		m_clouds = new Cloud[MAX_CLOUDS];
		m_ghosts = new Ghost[MAX_GHOSTS];
		m_AL = AL;
		try {
			wallAutomaton = m_AL.getAutomaton("Block");
			cloudAutomaton = m_AL.getAutomaton("Cloud");
			ghostAutomaton = m_AL.getAutomaton("Ghost");
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
			m_elements = new Element[nbRow * nbCol];
			for (int i = 0; i < nbRow; i++) {
				String[] actualLigne = f.readLine().split("/");
				for (int j = 0; j < nbCol; j++) {
					m_elements[i * nbCol + j] = CodeElement(actualLigne[j], j * Element.SIZE, i * Element.SIZE);
					;
				}
			}
			f.close();
			generateClouds(m_clouds);
			generateGhosts(m_ghosts);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void generateGhosts(Ghost[] ghosts) {
		String[] dirs = {"N", "E", "W", "S"};
		Direction dir = new Direction(dirs[(int) (Math.random()*dirs.length)]);
		int x, y;
		for (int i = 0; i < ghosts.length; i++) {
			x = (int)(Math.random()*(4558));
			y = (int)(Math.random()*(3956));
			while (isBlocked(x, y)) {
				x = (int)(Math.random()*4558);
				y = (int)(Math.random()*3956);
			}
			ghosts[i] = new Ghost(dir, new Coord(x, y), ghostAutomaton, m_model);
			if (dir.toString().equals("W"))
				ghosts[i].leftOrientation = true;
		}
	}

	private void generateClouds(Cloud[] clouds) {
		for (int i = 0; i < clouds.length; i++) {
			clouds[i] = new Cloud(cloudAutomaton, new Coord(1000, 1000), m_player);
		}

	}

	public Element CodeElement(String code, int x, int y) throws Exception {
		Coord coord = new Coord(x, y);
/*		if (code.equals("ES")) {
			return new UnderworldEmptySpace(coord, ESIM);
		} else if (code.contentEquals("LS")) {
			return new UndWall(coord, UWIM, "LS", wallAutomaton);
		} else if (code.contentEquals("RS")) {
			return new UndWall(coord, UWIM, "RS", wallAutomaton);
		} else if (code.contentEquals("SB")) {
			return new UndWall(coord, UWIM, "HS", wallAutomaton);
		} else if (code.contentEquals("OW")) {
			return new UndWall(coord, UWIM, "W", wallAutomaton);
		} else if (code.contentEquals("WB")) {
			return new UndWall(coord, UWIM, "B", wallAutomaton);
		} else if (code.contentEquals("IOW")) {
			return new UndWall(coord, UWIM, "IOW", wallAutomaton);
		} else if (code.contentEquals("RWB")) {
			return new UndWall(coord, UWIM, "RWB", wallAutomaton);
		} else if (code.contentEquals("OWD")) {
			return new UndWall(coord, UWIM, "OWD", wallAutomaton);
		}*/
		if (code.equals("IW")) {
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

	public void paint(Graphics g, int width, int height, PlayerSoul player) {
		for (int i = 0; i < m_elements.length; i++) {
			m_elements[i].paint(g);
		}
		player.paint(g);
		for (int i = 0; i < m_clouds.length; i++) {
			m_clouds[i].paint(g);
		}
		for(int i = 0; i < m_ghosts.length; i++) {
			m_ghosts[i].paint(g);
		}
	}

	public Coord getStartCoord() {
		return startCoord;
	}

	public void tick(long elapsed) {

		for (int i = 0; i < m_clouds.length; i++) {
			if (m_clouds[i].getAutomaton() != null) {
				if (m_clouds[i].outScreen) {
			//		Coord newCoord = new Coord(m_clouds[i].getCoord().X(), m_clouds[i].getCoord().Y());
			//		m_clouds[i] = new Cloud(cloudAutomaton, newCoord, m_player);
					m_clouds[i].getCoord().setX(1000);
					m_clouds[i].getCoord().setY(1000);
				}
				m_clouds[i].tick(elapsed);
				m_clouds[i].getAutomaton().step(m_clouds[i]);
			}
		}
		for (int i = 0; i < m_ghosts.length; i++) {
			m_ghosts[i].tick(elapsed);
		}
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

	public int blockBot(int x, int y) {
		int n = (x / Element.SIZE) + (y / Element.SIZE * nbCol);
		if (n >= 0 && n < nbRow * nbCol) {
			return m_elements[n].getCoord().Y() + Element.SIZE;
		} else {
			return 0;
		}
	}

}
