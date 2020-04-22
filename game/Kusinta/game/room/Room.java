package room;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import game.Coord;

public class Room {

	int m_width, m_height;
	int nbRow;
	int nbCol;
	
	String roomFile;
	Element[] m_elements; // liste des éléments de la salles (mur, et vide)
	Decor[] m_decor; // liste de tout les décors affichable
	int ambiance;
	boolean isChanged;
	/* 
	 * Cette variable va nous servir à eviter que les décors ne soient pas trop collé
	 */
	int decorFreq; 
	
	Coord startCoord;
	
	OuterWallImageManager OWIM;
	InnerWallImageManager IWIM;
	EmptySpaceImageManager ESIM;
	DoorImageManager DIM;
	
	public Room() {
		startCoord = new Coord();
		m_decor = new Decor[0];
		decorFreq = (int) (Math.random() * 10) + 5;
		ambiance = (int) (Math.random() * RoomParam.nbAmbiance) + 1;
		
		OWIM = new OuterWallImageManager(ambiance);
		IWIM = new InnerWallImageManager(ambiance);
		ESIM = new EmptySpaceImageManager(ambiance);
		DIM = new DoorImageManager(ambiance);
		
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
			m_elements = new Element[nbRow * nbCol];
			for (int i = 0; i < nbRow; i++) {
				String[] actualLigne = f.readLine().split("/");
				for (int j = 0; j < nbCol; j++) {
					m_elements[i * nbCol + j] = CodeElement(actualLigne[j], j * Element.SIZE,
							i * Element.SIZE);;
				}
			}
			f.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Element CodeElement(String code, int x, int y) throws Exception {
		Coord coord = new Coord(x, y);
		if (code.equals("IW")) {
			return new InnerWall(coord, IWIM);
		} else if (code.equals("OW_E")) {
			return new OuterWall(coord, OWIM, "E");
		} else if (code.equals("OW_S")) {
			return new OuterWall(coord, OWIM, "S");
		} else if (code.equals("OW_W")) {
			return new OuterWall(coord, OWIM, "W");
		} else if (code.equals("OW_N")) {
			return new OuterWall(coord, OWIM, "N");
		} else if (code.equals("OW_SE")) {
			return new OuterWall(coord, OWIM, "SE");
		} else if (code.equals("OW_SW")) {
			return new OuterWall(coord, OWIM, "SW");
		} else if (code.equals("OW_NW")) {
			return new OuterWall(coord, OWIM, "NW");
		} else if (code.equals("OW_NE")) {
			return new OuterWall(coord, OWIM, "NE");
		} else if (code.equals("ES")) {
			return new EmptySpace(coord, ESIM);
		} else if (code.equals("ES_D")) {
			newDecor(coord, true);
			return new EmptySpace(coord, ESIM);
		} else if (code.equals("ES_I")) {
			startCoord = new Coord(coord);
			startCoord.translate(Decor.SIZE / 2, Decor.SIZE / 2);
			return new EmptySpace(coord, ESIM);
		} else if (code.equals("ES_T")) {
			newDecor(coord, false);
			return new EmptySpace(coord, ESIM);
		} else {
			System.out.println("Non reach point. Code Error on : " + code);
		}

		throw new Exception("Code room err: " + code);

	}
	
	/*
	 * Methode qui va creer un nouveau décor a la position souhaitée, en fonction de la 
	 * fréquence d'appartition du décor.
	 * le boolean isDoor permet de spécifier que l'ont veut creer une porte.
	 */
	public void newDecor(Coord coord, boolean isDoor) {
		if (isDoor) {
			Decor[] tmp_decor = new Decor[m_decor.length + 1];
			System.arraycopy(m_decor, 0, tmp_decor, 0, m_decor.length);
			try {
				tmp_decor[m_decor.length] = new Door(new Coord(coord), DIM, this);
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
					tmp_decor[m_decor.length] = new Torch(new Coord(coord), this);
					break;
				case 1:
					tmp_decor[m_decor.length] = new Lamp(new Coord(coord), this);
					break;
				case 2:
					tmp_decor[m_decor.length] = new Library(new Coord(coord), this);
					break;
				case 3:
					tmp_decor[m_decor.length] = new Stage(new Coord(coord), this);
					break;
				default:
					tmp_decor[m_decor.length] = new Lamp(new Coord(coord),this);
					break;
				}
				m_decor = tmp_decor;
				decorFreq = 0;
			} else {
				decorFreq += (int) (Math.random() * 10) + 5;
			}
		}
		
	}

	public void paint(Graphics g) {
		for (int i = 0; i < m_elements.length; i++) {
			m_elements[i].paint(g);
		}
		for (int i = 0; i < m_decor.length; i ++) {
			m_decor[i].paint(g);
		}
	}
	
	public Coord getStartCoord() {
		return startCoord;
	}
	
	public boolean isBlocked(int x, int y) {
		int n = (x / Element.SIZE) + (y / Element.SIZE * nbCol);
		if (n >= 0 && n < nbRow *nbCol) {
			return m_elements[n].__isSolid;
		}
		return true;
	}
	

	public void tick(long elapsed) {
		for (int i = 0; i < m_decor.length; i ++) {
			m_decor[i].tick(elapsed);
		}
	}

}
