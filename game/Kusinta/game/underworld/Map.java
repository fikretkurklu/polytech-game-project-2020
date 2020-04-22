package underworld;

import java.awt.Graphics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import automaton.Entity;
import game.Coord;
import room.Element;
import room.EmptySpace;
import room.EmptySpaceImageManager;
import room.RoomParam;

public class Map {
	
	String mapFile;
	int m_width, m_height;
	Element[] m_elements; 
	int ambiance = 1;
	Coord startCoord;
	int nbRow;
	int nbCol;

	MapEmptySpaceImageManager ESIM;

	public Map() {
		startCoord = new Coord();
		ambiance = (int)(Math.random()*MapParam.nbAmbiance)+1;
		BufferedReader f;
		ESIM = new MapEmptySpaceImageManager(1);

		try {
			mapFile = MapParam.mapFile;
			f = new BufferedReader(new FileReader(new File(mapFile)));
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
		if (code.equals("ES")) {
			return new MapEmptySpace(coord, ESIM);
		}
		throw new Exception("Code room err: " + code);
	}

	public void paint(Graphics g) {
		for (int i = 0; i < m_elements.length; i++) {
			m_elements[i].paint(g);
		}
	}
	
	public Coord getStartCoord() {
		return startCoord;
	}
	
}
