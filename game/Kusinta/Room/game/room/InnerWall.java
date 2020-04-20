package game.room;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.io.IOException;

/*
 * 
 * Dans le cas d'un InnerWall, les images sont dans un tableau de String
 * 
 */

public class InnerWall extends Element {
	
	public InnerWall(InnerWallImageManager innerWallImageManager) throws IOException {
		super(true, true);
		String[] pathTable = innerWallImageManager.get("", innerWallImageManager.useImageTable);
		if (pathTable != null) {
			int randomNum = (int) (Math.random()*pathTable.length);
			loadImage(pathTable[randomNum]);
		}
	}
	
	public InnerWall(Coord coord, InnerWallImageManager innerWallImageManager) throws IOException {
		super(true, true, coord);
		String[] pathTable = innerWallImageManager.get("", innerWallImageManager.useImageTable);
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
