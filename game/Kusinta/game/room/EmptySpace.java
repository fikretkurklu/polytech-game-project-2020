package room;

import game.Coord;

public class EmptySpace extends Element {
	
	
	/*
	 * 
	 * This class represent empty spaces in the room that have a sprite and a specific behavior
	 * 
	 * 
	 */

	public EmptySpace(Coord coord, EmptySpaceImageManager ESImageManager) throws Exception {
		super(false, true, coord);
		String path = ESImageManager.get("", ESImageManager.useImageTable);
		if (path != null) {
			loadImage(path);
		}
	}

	
}
