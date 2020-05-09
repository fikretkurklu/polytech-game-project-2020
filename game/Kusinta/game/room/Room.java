package room;

import java.awt.Graphics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

import automaton.Automaton;
import environnement.Decor;
import environnement.Element;
import game.Coord;
import game.Game;
import room.ElementImageFactory.TypeBG;

public class Room {

	int nbRow;
	int nbCol;
	int m_width, m_height;

	String roomFile;
	Element[] m_elements; // liste des entity de la salles (mur)
	Element[] m_background; // liste du décors non entité
	Decor[] m_decor; // liste de tout les décors affichable
	
	boolean isChanged;

	/*
	 * Cette variable va nous servir à eviter que les décors ne soient pas trop
	 * collé
	 */

	int decorFreq;
	Coord startCoord;
	ElementImageFactory EIF;
	int m_BlockAElapsed = 0;
	int m_RealWidth, m_RealHeight;
	
	Automaton BlockAutomaton = null;
	Automaton StaticDecorAutomaton = null;
	
	//Concernant la position des ennemis dans la room
	LinkedList<Coord> FOTable;
	LinkedList<Coord> WOTable;

	public Room(int width, int height) throws Exception {
		m_width = width;
		m_height = height;
		startCoord = new Coord();
		m_decor = new Decor[0];
		m_elements = new Element[0];
		m_background = new Element[0];
		decorFreq = (int) (Math.random() * 10) + 5;
		FOTable = new LinkedList<Coord>();
		WOTable = new LinkedList<Coord>();
		EIF = new ElementImageFactory();
		StaticDecorAutomaton = Game.m_factory.m_AL.getAutomaton("Decor");
		BlockAutomaton =Game.m_factory.m_AL.getAutomaton("Block");
		
		BufferedReader f;

		roomFile = "resources/Room/Sample/room1.sample";
		f = new BufferedReader(new FileReader(new File(roomFile)));
		String[] firstLine = f.readLine().split(":");
		nbRow = Integer.parseInt(firstLine[0]);
		nbCol = Integer.parseInt(firstLine[1]);
		m_RealWidth = nbCol * Element.SIZE;
		m_RealHeight = nbRow * Element.SIZE;
		// m_background = new Element[i * nbCol];
		for (int i = 0; i < nbRow; i++) {
			String[] actualLigne = f.readLine().split("/");
			for (int j = 0; j < nbCol; j++) {
				CodeElement(actualLigne[j], j, i);
			}
		}
		f.close();
	}
	

	public void CodeElement(String code, int x, int y) throws Exception {

		Coord coord = new Coord(x * Element.SIZE, y * Element.SIZE);
		switch(code) {
		case "IW" :
			Grow(false, new InnerWall(coord, EIF.getImage(TypeBG.IW)));
			break;
		case "OW_E" :
			Grow(true, new OuterWall(coord, EIF.getImage(TypeBG.OWE), BlockAutomaton));
			break;
		case "OW_S" :
			Grow(true, new OuterWall(coord, EIF.getImage(TypeBG.OWS), BlockAutomaton));
			break;
		case "OW_W" :
			Grow(true, new OuterWall(coord, EIF.getImage(TypeBG.OWW), BlockAutomaton));
			break;
		case "OW_N" :
			Grow(true, new OuterWall(coord, EIF.getImage(TypeBG.OWN), BlockAutomaton));
			break;
		case "OW_SE" :
			Grow(true, new OuterWall(coord, EIF.getImage(TypeBG.OWSE), BlockAutomaton));
			break;
		case "OW_SW" :
			Grow(true, new OuterWall(coord, EIF.getImage(TypeBG.OWSW), BlockAutomaton));
			break;
		case "OW_NW" :
			Grow(true, new OuterWall(coord, EIF.getImage(TypeBG.OWNW), BlockAutomaton));
			break;
		case "OW_NE" :
			Grow(true, new OuterWall(coord, EIF.getImage(TypeBG.OWNE), BlockAutomaton));
			break;
		case "ES" :
			Grow(false, new EmptySpace(coord, EIF.getImage(TypeBG.ES)));
			break;
		case "ES_D" :
			newDecor(coord, true);
			Grow(false, new EmptySpace(coord, EIF.getImage(TypeBG.ES)));
			break;
		case "ES_I" :
			startCoord = new Coord(coord);
			startCoord.translate(Decor.SIZE / 2, Decor.SIZE);
			Grow(false, new EmptySpace(coord, EIF.getImage(TypeBG.ES)));
			break;
		case "ES_T" :
			newDecor(coord, false);
			Grow(false, new EmptySpace(coord, EIF.getImage(TypeBG.ES)));
			break;
		case "ES_WO" :
			Grow(false, new EmptySpace(coord, EIF.getImage(TypeBG.ES)));
			WOTable.add(coord);
			break;
		case "ES_FO" :
			Grow(false, new EmptySpace(coord, EIF.getImage(TypeBG.ES)));
			FOTable.add(coord);
			break;
		default :
			throw new Exception("Code room err: " + code);
		}
	} 

	public void Grow(boolean isElement, Element add) {
		if (isElement) {
			Element[] tmp_elements = new Element[m_elements.length + 1];
			System.arraycopy(m_elements, 0, tmp_elements, 0, m_elements.length);
			tmp_elements[m_elements.length] = add;
			m_elements = tmp_elements;
		}
		Element[] tmp_background = new Element[m_background.length + 1];
		System.arraycopy(m_background, 0, tmp_background, 0, m_background.length);
		tmp_background[m_background.length] = add;
		m_background = tmp_background;
	}

	/*
	 * Methode qui va creer un nouveau décor a la position souhaitée, en fonction de
	 * la fréquence d'appartition du décor. le boolean isDoor permet de spécifier
	 * que l'ont veut creer une porte.
	 */
	public void newDecor(Coord coord, boolean isDoor) throws Exception {
		if (isDoor) {
			Decor[] tmp_decor = new Decor[m_decor.length + 1];
			System.arraycopy(m_decor, 0, tmp_decor, 0, m_decor.length);
			tmp_decor[m_decor.length] = new Door(new Coord(coord), this, StaticDecorAutomaton);

			m_decor = tmp_decor;
		} else {
			int rand = (int) (Math.random() * 100);
			if (rand < decorFreq) {
				Decor[] tmp_decor = new Decor[m_decor.length + 1];
				System.arraycopy(m_decor, 0, tmp_decor, 0, m_decor.length);
				int decor_type = (int) (Math.random() * Decor.NB_DECOR);
				switch (decor_type) {
				case 0:
					tmp_decor[m_decor.length] = new Torch(new Coord(coord), this, StaticDecorAutomaton);
					break;
				case 1:
					tmp_decor[m_decor.length] = new Lamp(new Coord(coord), this, StaticDecorAutomaton);
					break;
				case 2:
					tmp_decor[m_decor.length] = new Library(new Coord(coord), this, StaticDecorAutomaton);
					break;
				case 3:
					tmp_decor[m_decor.length] = new Stage(new Coord(coord), this, StaticDecorAutomaton);
					break;
				default:
					tmp_decor[m_decor.length] = new Lamp(new Coord(coord), this, StaticDecorAutomaton);
					break;
				}
				m_decor = tmp_decor;
				decorFreq = 0;
			} else {
				decorFreq += (int) (Math.random() * 10) + 5;
			}
		}

	}

	public void paint(Graphics g, int width, int height, int x_decalage, int y_decalage) {
		m_width = width;
		m_height = height;
		int y_start = (-y_decalage / Element.SIZE) * nbCol;
		int y_end = Math.min((y_start + (m_height / Element.SIZE + 2) * nbCol), m_background.length);

		int x_start = (-x_decalage / Element.SIZE);
		int x_end = Math.min((x_start + width / Element.SIZE + 2), nbCol);

		for (int i = y_start; i < y_end; i += nbCol) {
			for (int j = i + x_start; j < i + x_end; j++) {
				m_background[j].paint(g);
			}
		}
		for (int i = 0; i < m_decor.length; i++) {
			m_decor[i].paint(g);
		}
	}

	public Coord getStartCoord() {
		return startCoord;
	}

	public Coord[] getFlyingOpponentCoord() {
		Coord[] coord = new Coord[FOTable.size()];
		for (int i = 0; i < coord.length; i++) {
			coord[i] = FOTable.remove();
		}
		return coord;
	}
	
	public Coord[] getWalkingOpponentCoord() {
		Coord[] coord = new Coord[WOTable.size()];
		for (int i = 0; i < coord.length; i++) {
			coord[i] = WOTable.remove();
		}
		return coord;
	}
	
	public boolean isBlocked(int x, int y) {
		int n = (x / Element.SIZE) + (y / Element.SIZE * nbCol);
		if (n >= 0 && n < nbRow * nbCol) {
			return m_background[n].isSolid();
		}
		return true;
	}
	
	public boolean isBlocked(Coord C) {
		return isBlocked(C.X(), C.Y());
	}

	public int blockTop(int x, int y) {
		int n = (x / Element.SIZE) + (y / Element.SIZE * nbCol);
		if (n >= 0 && n < nbRow * nbCol) {
			return m_background[n].getCoord().Y();
		} else {
			return 0;
		}
	}

	public int blockBot(int x, int y) {
		int n = (x / Element.SIZE) + (y / Element.SIZE * nbCol);
		if (n >= 0 && n < nbRow * nbCol) {
			return m_background[n].getCoord().Y() + Element.SIZE;
		} else {
			return 0;
		}
	}

	public void tick(long elapsed) {

		for (int i = 0; i < m_decor.length; i++) {
			m_decor[i].tick(elapsed);
		}
		m_BlockAElapsed += elapsed;
		if (m_BlockAElapsed > 1000) {
			m_BlockAElapsed = 0;
			for (int i = 0; i < m_elements.length; i++) {
				if (m_elements[i].getAutomaton() != null) {
					m_elements[i].getAutomaton().step(m_elements[i]);
				}
			}

			for (int i = 0; i < m_decor.length; i++) {
				if (m_decor[i].getAutomaton() != null) {
					m_decor[i].getAutomaton().step(m_decor[i]);
				}
			}

		}
	}

	public int getWitdh() {
		return m_RealWidth;
	}

	public int getHeight() {
		return m_RealHeight;
	}

	public Door getDoor() {
		for (int i = 0; i< m_decor.length; i++) {
			if(m_decor[i] instanceof Door) {
				return (Door) m_decor[i];
			}
		}
		return null;
	}

	public int blockTop(Coord c) {
		return blockTop(c.X(), c.Y());
	}
}
