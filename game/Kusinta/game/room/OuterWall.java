package room;

import game.Coord;

public class OuterWall extends Element {

	/*
	 * 
	 * 
	 * This class represent the outside of the wall, with its sprite and behavior 
	 * 
	 * 
	 */
	
	private String m_orientation;
	
	public OuterWall(OuterWallImageManager OWImageManager) throws Exception {
		super(true, true);
		m_orientation = "N";
		String path = OWImageManager.get(m_orientation,OWImageManager.useImageTable);
		if (path != null) {
			loadImage(path);
		}
	}
	
	public OuterWall(Coord coord, OuterWallImageManager OWImageManager, String orientation) throws Exception {
		super(true, true, coord);
		m_orientation = orientation;
		String path = OWImageManager.get(m_orientation,OWImageManager.useImageTable);
		if (path != null) {
			loadImage(path);
		}		
	}
	
	public void setOrientation(String orientation) {
		m_orientation = orientation;
	}
	
	public String getOrientation() {
		return m_orientation;
	}

	
}
