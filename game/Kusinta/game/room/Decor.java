package room;

import java.awt.image.BufferedImage;

import game.Coord;

public abstract class Decor extends Element{
	int m_imageIndex;
	long m_imageElapsed;
	boolean isAnimated;
	BufferedImage[] m_images;
	
	public Decor(boolean isSolid, boolean isVisible) {
		super(isSolid, isVisible);
	}
	
	public Decor(boolean isSolid, boolean isVisible, Coord coord) {
		super(isSolid, isVisible, coord);
	}
	
	public abstract void tick(long elapsed);

}
