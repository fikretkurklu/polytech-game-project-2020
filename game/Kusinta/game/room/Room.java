package room;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import game.Coord;

public class Room {

	int m_width, m_height;
	int nbRow;
	int nbCol;
	String roomFile;
	Element[] m_elements;
	Decor[] m_decor;
	int ambiance = 1;
	boolean isChanged;
	
	Coord startCoord;
	
	OuterWallImageManager OWIM = new OuterWallImageManager(ambiance);
	InnerWallImageManager IWIM = new InnerWallImageManager(ambiance);
	EmptySpaceImageManager ESIM = new EmptySpaceImageManager(ambiance);
	DoorImageManager DIM = new DoorImageManager(ambiance);
	
	public Room() {
		startCoord = new Coord();
		m_decor = new Decor[0];
		BufferedReader f;
		try {
			roomFile = "resources/Room/Sample/room1.sample";
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
			Decor[] tmp_decor = new Decor[m_decor.length + 1];
			System.arraycopy(m_decor, 0, tmp_decor, 0, m_decor.length);
			tmp_decor[m_decor.length] = new Door(new Coord(coord), DIM);
			m_decor = tmp_decor;
			return new EmptySpace(coord, ESIM);
		} else if (code.equals("ES_I")) {
			startCoord = new Coord(coord);
			return new EmptySpace(coord, ESIM);
		} else if (code.equals("ES_T")) {
			Decor[] tmp_decor = new Decor[m_decor.length + 1];
			System.arraycopy(m_decor, 0, tmp_decor, 0, m_decor.length);
			tmp_decor[m_decor.length] = new Torch(new Coord(coord));
			m_decor = tmp_decor;
			return new EmptySpace(coord, ESIM);
		} else {
			System.out.println("Non reach point. Code Error on : " + code);
		}

		throw new Exception("Code room err: " + code);

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
		return m_elements[(x%nbCol) * nbCol + y %nbRow].__isSolid;
	}
	
	public void tick(long elapsed) {
		for (int i = 0; i < m_decor.length; i ++) {
			m_decor[i].tick(elapsed);
		}
	}

}
