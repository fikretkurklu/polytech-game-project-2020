package game.room;

import java.io.IOException;

public class InnerWall extends Element {

	private String m_orientation;
	
	public InnerWall(InnerWallImageManager innerWallImageManager) throws IOException {
		super(true, true);
		String[] pathTable = innerWallImageManager.get(m_orientation, false);
		if (pathTable != null) {
			int randomNum = (int) (Math.random()*pathTable.length);
			loadImage(pathTable[randomNum]);
		}
	}
	
	public InnerWall(Coord coord) throws IOException {
		super(true, true, coord);
	}
	
	public void setOrientation(String orientation) {
		m_orientation = orientation;
	}
	
	public String getOrientation() {
		return m_orientation;
	}

}
