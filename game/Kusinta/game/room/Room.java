package room;

import java.awt.Graphics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import automaton.Automaton;
import automaton.AutomatonLibrary;
import environnement.Decor;
import environnement.Element;
import game.Coord;

public class Room{

	int nbRow;
	int nbCol;
	
	AutomatonLibrary m_AL;
	int m_width, m_height;

	String roomFile;
	Element[] m_elements; // liste des entity de la salles (mur)
	Element[] m_background; // liste du décors non entité
	Decor[] m_decor; // liste de tout les décors affichable
	int ambiance;
	boolean isChanged;
	
	/*
	 * Cette variable va nous servir à eviter que les décors ne soient pas trop
	 * collé
	 */
	
	int decorFreq;
	Coord startCoord;

	OuterWallImageManager OWIM;
	InnerWallImageManager IWIM;
	EmptySpaceImageManager ESIM;
	DoorImageManager DIM;
	Automaton BlockAutomaton = null;
	Automaton StaticDecorAutomaton = null;
	
	int m_BlockAElapsed = 0;
	int m_RealWidth, m_RealHeight;

	public Room(AutomatonLibrary AL, int width, int height) throws Exception {
		m_AL = AL;
		m_width = width;
		m_height = height;
		startCoord = new Coord();
		m_decor = new Decor[0];
		m_elements = new Element[0];
		m_background = new Element[0];
		decorFreq = (int) (Math.random() * 10) + 5;
		ambiance = (int) (Math.random() * RoomParam.nbAmbiance) + 1;

		OWIM = new OuterWallImageManager(ambiance);
		IWIM = new InnerWallImageManager(ambiance);
		ESIM = new EmptySpaceImageManager(ambiance);
		DIM = new DoorImageManager(ambiance);

		StaticDecorAutomaton = m_AL.getAutomaton("Decor");
		BlockAutomaton = m_AL.getAutomaton("Block");
		
		BufferedReader f;
		try {
			roomFile = RoomParam.roomFile[(int) (Math.random() * RoomParam.roomFile.length)];
			f = new BufferedReader(new FileReader(new File(roomFile)));
			/*
			 * Le fichier suis cette syntaxe: 
			 * Row:Col 
			 * CODE/CODE/CODE/...../ 
			 * ...
			 * ...
			 * ...
			 */
			String[] firstLine = f.readLine().split(":");
			nbRow = Integer.parseInt(firstLine[0]);
			nbCol = Integer.parseInt(firstLine[1]);
			m_RealWidth = nbCol * Element.SIZE;
			m_RealHeight = nbRow * Element.SIZE;
			//m_background = new Element[i * nbCol];
			for (int i = 0; i < nbRow; i++) {
				String[] actualLigne = f.readLine().split("/");
				for (int j = 0; j < nbCol; j++) {
					CodeElement(actualLigne[j], j, i);
				}
			}
			f.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void CodeElement(String code, int x, int y) throws Exception{
		
		Coord coord = new Coord(x * Element.SIZE, y * Element.SIZE);
		if (code.equals("IW")) {
			Grow(false, new InnerWall(coord, IWIM));
		} else if (code.equals("OW_E")) {
			Grow(true, new OuterWall(coord, OWIM, "E", BlockAutomaton));
		} else if (code.equals("OW_S")) {
			Grow(true, new OuterWall(coord, OWIM, "S", BlockAutomaton));
		} else if (code.equals("OW_W")) {
			Grow(true, new OuterWall(coord, OWIM, "W", BlockAutomaton));
		} else if (code.equals("OW_N")) {
			Grow(true, new OuterWall(coord, OWIM, "N", BlockAutomaton));
		} else if (code.equals("OW_SE")) {
			Grow(true, new OuterWall(coord, OWIM, "SE", BlockAutomaton));
		} else if (code.equals("OW_SW")) {
			Grow(true, new OuterWall(coord, OWIM, "SW", BlockAutomaton));
		} else if (code.equals("OW_NW")) {
			Grow(true, new OuterWall(coord, OWIM, "NW", BlockAutomaton));
		} else if (code.equals("OW_NE")) {
			Grow(true, new OuterWall(coord, OWIM, "NE", BlockAutomaton));
		} else if (code.equals("ES")) {
			Grow(false, new EmptySpace(coord, ESIM));
		} else if (code.equals("ES_D")) {
			newDecor(coord, true);
			Grow(false, new EmptySpace(coord, ESIM));
		} else if (code.equals("ES_I")) {
			startCoord = new Coord(coord);
			startCoord.translate(Decor.SIZE / 2, Decor.SIZE);
			Grow(false, new EmptySpace(coord, ESIM));
		} else if (code.equals("ES_T")) {
			newDecor(coord, false);
			Grow(false, new EmptySpace(coord, ESIM));
		} 
		//throw new Exception("Code room err: " + code);

	}

	public void Grow(boolean isElement, Element add) {
		if (isElement) {
			Element[] tmp_elements= new Element[m_elements.length + 1];
			System.arraycopy(m_elements, 0, tmp_elements, 0, m_elements.length);
			tmp_elements[m_elements.length] = add;
			m_elements = tmp_elements;
		}
		Element[] tmp_background= new Element[m_background.length + 1];
		System.arraycopy(m_background, 0, tmp_background, 0, m_background.length);
		tmp_background[m_background.length] = add;
		m_background = tmp_background;
	}
	
	/*
	 * Methode qui va creer un nouveau décor a la position souhaitée, en fonction de
	 * la fréquence d'appartition du décor. le boolean isDoor permet de spécifier
	 * que l'ont veut creer une porte.
	 */
	public void newDecor(Coord coord, boolean isDoor) {
		if (isDoor) {
			Decor[] tmp_decor = new Decor[m_decor.length + 1];
			System.arraycopy(m_decor, 0, tmp_decor, 0, m_decor.length);
			try {
				tmp_decor[m_decor.length] = new Door(new Coord(coord), DIM, this, StaticDecorAutomaton);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	public void paint(Graphics g, int width, int height) {
		m_width = width;
		m_height = height;
		for (int i = 0; i < m_background.length; i++) {
			m_background[i].paint(g);
		}
		for (int i = 0; i < m_decor.length; i++) {
			m_decor[i].paint(g);
		}
	}

	public Coord getStartCoord() {
		return startCoord;
	}

	public boolean isBlocked(int x, int y) {
		int n = (x / Element.SIZE) + (y / Element.SIZE * nbCol);
		if (n >= 0 && n < nbRow * nbCol) {
			return m_background[n].isSolid();
		}
		return true;
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
			for (int i = 0; i < m_elements.length; i ++) {
				if (m_elements[i].getAutomaton() != null) {
					m_elements[i].getAutomaton().step(m_elements[i]);
				}
			}
			
			for (int i = 0; i < m_decor.length; i ++) {
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
}
