package game.room;

import java.io.IOException;

public class InnerWall extends Element {
	
	public InnerWall(InnerWallImageManager innerWallImageManager) throws IOException {
		super(true, true);
		String[] pathTable = innerWallImageManager.get("", false);
		if (pathTable != null) {
			int randomNum = (int) (Math.random()*pathTable.length);
			loadImage(pathTable[randomNum]);
		}
	}
	
	public InnerWall(Coord coord) throws IOException {
		super(true, true, coord);
	}

}
