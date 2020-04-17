package game.room;

import java.io.IOException;
import java.util.HashMap;


public class OuterWall extends Element {

	/*
	 * 
	 * Dans la HashMap, voici comment son réparti les différents sprites :
	 * - 0 est associé au mur gauche
	 * - 1 est associé au mur droit
	 * - 2 est associé au mur haut
	 * - 3 est associé au mur bas
	 * - 4 est associé à l'angle haut gauche
	 * - 5 est associé à l'angle haut droit
	 * - 6 est associé à l'angle bas gauche
	 * - 7 est associé à l'angle bas droit
	 * 
	 */
	
	
	private HashMap<String,String[]> m_HashMap;
	private String m_orientation;
	
	public OuterWall() throws IOException {
		super(true, true);
		String[] pathTable = m_HashMap.get(m_orientation);
		if (pathTable != null) {
			int randomNum = (int) (Math.random()*pathTable.length);
			loadImage(pathTable[randomNum]);
		}
	}
	
	public OuterWall(Coord coord) throws IOException {
		super(true, true, coord);
	}
	
	public void setOrientation(String orientation) {
		m_orientation = orientation;
	}
	
	public String getOrientation() {
		return m_orientation;
	}
	
}
