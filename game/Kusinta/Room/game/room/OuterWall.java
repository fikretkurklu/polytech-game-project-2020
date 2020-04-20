package game.room;

import java.io.IOException;


public class OuterWall extends Element {

	/*
	 * 
	 * Dans la HashMap, voici comment son réparti les différents sprites :
	 * - 1 est associé au mur Nord
	 * - 2 est associé au mur Sud
	 * - 3 est associé au mur West
	 * - 4 est associé au mur Est
	 * - 5 est associé à l'angle Nord Est
	 * - 6 est associé à l'angle Nord West
	 * - 7 est associé à l'angle Sud Est
	 * - 8 est associé à l'angle Sud West
	 * 
	 */
	
	private String m_orientation;
	
	public OuterWall(OuterWallImageManager outerWallImageManager) throws IOException {
		super(true, true);
		String[] pathTable = outerWallImageManager.get(m_orientation,true);
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
