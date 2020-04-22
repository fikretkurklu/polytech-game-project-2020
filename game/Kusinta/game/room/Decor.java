package room;

import java.awt.Image;

import game.Coord;

public abstract class Decor extends Element{
	
	public static final int NB_DECOR = 4;
	
	int m_imageIndex;
	long m_imageElapsed;
	boolean isAnimated;
	Image[] m_images;
	Room m_room;
	
	public Decor(boolean isSolid, boolean isVisible, boolean isAnimated, Coord coord, Room room) {
		super(isSolid, isVisible, coord);
		this.isAnimated = isAnimated;
		m_room = room;
	}
	
	public abstract void tick(long elapsed);
	
	public boolean activate() {
		return true;
	}

}
