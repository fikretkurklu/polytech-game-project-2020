package underworld;

import java.awt.Graphics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import automaton.Automaton;
import automaton.AutomatonLibrary;
import game.Coord;
import environnement.Element;
import environnement.Env;

public class Underworld extends Env {
	public final static int MAX_CLOUDS = 1;

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
		super(Env.UNDERWORLD, AL, width, height);
		startCoord = new Coord();
		ambiance = (int) (Math.random() * UnderworldParam.nbAmbiance) + 1;
		BufferedReader f;
		ESIM = new UnderworldEmptySpaceImageManager(ambiance);
		UWIM = new UndWallImageManager(ambiance);
		m_clouds = new Cloud[MAX_CLOUDS];
		m_AL = AL;
		try {
			wallAutomaton = m_AL.getAutomaton("Block");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
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
		for(int i = 0; i < clouds.length;i++) {
			clouds[i] = new Cloud(cloudAutomaton, new Coord(1000, 200));
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
		return new Coord(0,0);
	}

	@Override
	public void tick(long elapsed) {

		for (int i = 0; i < m_clouds.length; i++) {
			if (m_clouds[i].getAutomaton() != null) {
				if (m_clouds[i].outScreen) {
					Coord newCoord = m_clouds[i].getCoord();
					newCoord.translateX(-(int) Math.random() * (m_width - m_width / 5 + 1) + m_width / 5);
					m_clouds[i] = new Cloud(cloudAutomaton, newCoord);
				}
				m_clouds[i].getAutomaton().step(m_clouds[i]);
			}
		}
	}

	/*
	 * public void tick(long elapsed) { for (int i = 0; i < m_clouds.length; i++) {
	 * if (m_clouds[i].getAutomaton() != null) { if (m_clouds[i].outScreen) {
	 * m_clouds[i] = new Cloud(cloudAutomaton,
	 * m_clouds[i].getCoord().translateX(-(int)Math.random()*(m_height-m_height/5)))
	 * ; } m_clouds[i].getAutomaton().step(m_clouds[i]); } } }
	 */

}
