package room;

import java.awt.Image;

import game.Coord;

public abstract class Decor extends Element{
	int m_imageIndex;
	long m_imageElapsed;
	boolean isAnimated;
	Image[] m_images;
	
	public Decor(boolean isSolid, boolean isVisible, boolean isAnimated, Coord coord) {
		super(isSolid, isVisible, coord);
		this.isAnimated = isAnimated;
	}
	
	public abstract void tick(long elapsed);
	
	public boolean activate() {
		return true;
	}

}
