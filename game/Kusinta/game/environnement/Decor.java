package environnement;

import java.awt.Image;
import java.awt.Rectangle;

import automaton.Automaton;
import game.Coord;
import room.Room;

public abstract class Decor extends Element{
	
	public static final int NB_DECOR = 4;
	
	protected int m_imageIndex;
	protected long m_imageElapsed;
	protected boolean isAnimated;
	protected Image[] m_images;
	protected Room m_room;
	
	protected Rectangle m_hitBox;
	
	public Decor(boolean isSolid, boolean isVisible, boolean isAnimated, Coord coord, Room room, Automaton automaton) {
		super(isSolid, isVisible, coord, automaton);
		
		this.isAnimated = isAnimated;
		m_room = room;
	}
	
	public abstract void tick(long elapsed);
	
	public boolean activate() {
		return true;
	}
	
	public Rectangle getHitBox() {
		return m_hitBox;
	}

}
