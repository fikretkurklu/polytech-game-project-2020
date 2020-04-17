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
	
	public Room() throws IOException {
		BufferedReader f = new BufferedReader(new FileReader(new File(roomFile)));
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
		
		for (int i = 0; i < nbRow; i ++) {
			String[] actualLigne = f.readLine().split("/");
			for (int j = 0 ; j < nbCol; j ++) {
				m_elements[i * nbCol + j] = CodeElement(actualLigne[j], j * nbCol * Element.SIZE, i * nbRow * Element.SIZE );
			}
		}
		f.close();
	}
	
	public Element CodeElement(String code, int x, int y) throws IOException {
		if (code == "M1") {
			return new InnerWall();
		}
		return null;
		
	}
	public void paint(Graphics g) {
		
	}
	
}
