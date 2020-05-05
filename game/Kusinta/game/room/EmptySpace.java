package room;

import environnement.Element;
import game.Coord;
import game.ImageLoader;

public class EmptySpace extends Element {
	
	
	/*
	 * 
	 * This class represent empty spaces in the room that have a sprite and a specific behavior
	 * 
	 * 
	 */

	public EmptySpace(Coord coord, EmptySpaceImageManager ESImageManager) throws Exception {
		super(false, true, coord);
		String path = ESImageManager.get("");
		if (path != null) {
			__image = ImageLoader.loadImage(path, SIZE);
		}
	}

	
}
