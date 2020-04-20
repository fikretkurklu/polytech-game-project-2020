package game.room;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Room {

	int m_width, m_height;
	int nbRow;
	int nbCol;
	String roomFile;
	Element[] m_elements;
	int ambiance = 1;
	OuterWallImageManager OWIM = new OuterWallImageManager(ambiance);
	InnerWallImageManager IWIM = new InnerWallImageManager(ambiance);
	EmptySpaceImageManager ESIM = new EmptySpaceImageManager(ambiance);

	public Room() {
		System.out.println("Test");
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
		} else if (code.equals("ES_I")) {
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
	}

}
