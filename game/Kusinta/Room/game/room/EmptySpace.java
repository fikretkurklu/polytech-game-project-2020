package game.room;

import java.io.IOException;

public class EmptySpace extends Element{
	
	/*
	 * 
	 * This class represent empty spaces in the room that have a sprite and a specific behavior
	 * 
	 * 
	 */

	public EmptySpace(EmptySpaceImageManager ESImageManager) throws IOException {
		super(false, true);
		String path = ESImageManager.get("", ESImageManager.useImageTable);
		if (path != null) {
			loadImage(path);
		}
	}

	public EmptySpace(Coord coord, EmptySpaceImageManager ESImageManager) throws IOException {
		super(false, true, coord);
		String path = ESImageManager.get("", ESImageManager.useImageTable);
		if (path != null) {
			loadImage(path);
		}
	}

	
}
