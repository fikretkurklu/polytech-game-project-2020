package game.room;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * 
 * Cette classe abstraite représente la base d'un élément affiché dans la salle, 
 * avec son image, ses coordonnées, ainsi que 2 booléen __isVisible et __isSolid
 * __image contient le sprite de l'élément
 * __coord contient les coordonnées de l'élément dans la room
 * __isVisible permet de savoir si l'élément doit être affiché à l'écran
 * __isSolide permet de savoir si l'élément peut être traversé
 * 
 */

public abstract class Element {

	public static final int SIZE = 32;

	Image __image;
	private Coord __coord;
	boolean __isVisible;
	boolean __isSolid;

	public Element(boolean isSolid, boolean isVisible) {
		__coord = new Coord();
		__isVisible = true;
		__isSolid = isSolid;
	}
	
	public Element(boolean isSolid, boolean isVisible, Coord coord) {
		__coord = coord;
		__isVisible = isVisible;
		__isSolid = isSolid;
	}

	public void loadImage(String path) throws IOException {
		File imageFile = new File(path);
		if (imageFile.exists()) {
			__image = ImageIO.read(imageFile);
		}
	}

	public void setCoord(Coord coord) {
		__coord = coord;
	}

	public Coord getCoord() {
		return __coord;
	}

}
