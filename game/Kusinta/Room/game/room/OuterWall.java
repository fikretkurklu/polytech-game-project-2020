package game.room;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.io.IOException;


public class OuterWall extends Element {

	/*
	 * 
	 * 
	 * This class represent the outside of the wall, with its sprite and behavior 
	 * 
	 * 
	 */
	
	private String m_orientation;
	
	public OuterWall(OuterWallImageManager outerWallImageManager) throws IOException {
		super(true, true);
		String[] pathTable = outerWallImageManager.get(m_orientation,outerWallImageManager.useImageTable);
		if (pathTable != null) {
			int randomNum = (int) (Math.random()*pathTable.length);
			loadImage(pathTable[randomNum]);
		}
	}
	
	public OuterWall(Coord coord, OuterWallImageManager outerWallImageManager) throws IOException {
		super(true, true, coord);
		String[] pathTable = outerWallImageManager.get(m_orientation,outerWallImageManager.useImageTable);
		if (pathTable != null) {
			int randomNum = (int) (Math.random()*pathTable.length);
			loadImage(pathTable[randomNum]);
		}		
	}
	
	public void setOrientation(String orientation) {
		m_orientation = orientation;
	}
	
	public String getOrientation() {
		return m_orientation;
	}
	
	public void paint(Graphics g) {
		ImageObserver obs = null;
		Coord coord = this.getCoord();
		int x = coord.X();
		int y = coord.Y();
		g.drawImage(__image, x, y, obs);
	}
	
}
