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

	AutomatonLibrary m_al;
	String mapFile;
	int m_width, m_height;
	Element[] m_elements;
	int ambiance = 1;
	Coord startCoord;
	int nbRow;
	int nbCol;
	Cloud[] m_clouds;
	UnderworldEmptySpaceImageManager ESIM;
	Automaton cloudAutomaton, wallAutomaton;
	UndWallImageManager UWIM;
	AutomatonLibrary m_AL;
	
	
	

	public Underworld(AutomatonLibrary AL, int width, int height) {
		m_al = AL;
		m_width = width;
		m_height = height;
		startCoord = new Coord();
		ambiance = (int) (Math.random() * UnderworldParam.nbAmbiance) + 1;
		BufferedReader f;
		ESIM = new UnderworldEmptySpaceImageManager(ambiance);
		UWIM = new UndWallImageManager(ambiance);
		m_clouds = new Cloud[MAX_CLOUDS];
		m_AL = AL;
		try {
			wallAutomaton = m_AL.getAutomaton("Block");
			cloudAutomaton = m_AL.getAutomaton("Cloud");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		generateClouds(m_clouds);
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void generateClouds(Cloud[] clouds) {
		for (int i = 0; i < clouds.length; i++) {
			clouds[i] = new Cloud(cloudAutomaton, new Coord(200, 1000));
		}

	}

	public Element CodeElement(String code, int x, int y) throws Exception {
		Coord coord = new Coord(x, y);
		if (code.equals("ES")) {
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
		}

		throw new Exception("Code room err: " + code);
	}

	public void paint(Graphics g, int width, int height) {
		for (int i = 0; i < m_elements.length; i++) {
			m_elements[i].paint(g);
		}
		for (int i = 0; i < m_clouds.length; i++) {
			m_clouds[i].paint(g);
		}
	}

	public Coord getStartCoord() {
		return startCoord;
	}

	public void tick(long elapsed) {

		for (int i = 0; i < m_clouds.length; i++) {
			if (m_clouds[i].getAutomaton() != null) {
				if (m_clouds[i].outScreen) {
					Coord newCoord = new Coord(1024, m_clouds[i].getCoord().Y());
					m_clouds[i] = new Cloud(cloudAutomaton, newCoord);
				}
				m_clouds[i].tick(elapsed);
				m_clouds[i].getAutomaton().step(m_clouds[i]);
			}
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

	public int blockBot(int x, int y) {
		int n = (x / Element.SIZE) + (y / Element.SIZE * nbCol);
		if (n >= 0 && n < nbRow * nbCol) {
			return m_elements[n].getCoord().Y() + Element.SIZE;
		} else {
			return 0;
		}
	}

}
