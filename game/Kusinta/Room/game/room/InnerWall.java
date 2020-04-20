package game.room;

import java.io.IOException;

/*
 * 
 * Dans le cas d'un InnerWall, les images sont dans un tableau de String
 * 
 */

public class InnerWall extends Element {
	
	public InnerWall(InnerWallImageManager IWImageManager) throws IOException {
		super(true, true);
		String path = IWImageManager.get("", IWImageManager.useImageTable);
		if (path != null) {
			loadImage(path);
		}
	}
	
	public InnerWall(Coord coord, InnerWallImageManager IWImageManager) throws IOException {
		super(true, true, coord);
		String path = IWImageManager.get("", IWImageManager.useImageTable);
		if (path != null) {
			loadImage(path);
		}
	}


}
