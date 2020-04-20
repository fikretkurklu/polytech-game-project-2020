package game.room;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.io.IOException;

public class EmptySpace extends Element{
	
	/*
	 * 
	 * This class represent empty spaces in the room that have a sprite and a specific behavior
	 * 
	 * 
	 */

	public EmptySpace(EmptySpaceImageManager emptySpaceImageManager) throws IOException {
		super(false, true);
		String[] pathTable = emptySpaceImageManager.get("", emptySpaceImageManager.useImageTable);
		if (pathTable != null) {
			int randomNum = (int) (Math.random()*pathTable.length);
			loadImage(pathTable[randomNum]);
		}
	}

	public EmptySpace(Coord coord, EmptySpaceImageManager emptySpaceImageManager) throws IOException {
		super(false, true, coord);
		String[] pathTable = emptySpaceImageManager.get("", emptySpaceImageManager.useImageTable);
		if (pathTable != null) {
			int randomNum = (int) (Math.random()*pathTable.length);
			loadImage(pathTable[randomNum]);
		}
	}
	
	public void paint(Graphics g) {
		ImageObserver obs = null;
		Coord coord = this.getCoord();
		int x = coord.X();
		int y = coord.Y();
		g.drawImage(__image, x, y, obs);
	}
	
}
