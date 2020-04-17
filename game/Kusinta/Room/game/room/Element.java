package game.room;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Element {

	public static final int SIZE = 32;

	Image __image;
	private Coord __coord;
	boolean __isVisible;
	boolean __isSolid;

	public Element(boolean isSolid) {
		__coord = new Coord();
		__isVisible = true;
		__isSolid = isSolid;
	}

	public void load_image(String path) throws IOException {
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
