package room;

import automaton.Automaton;
import environnement.Element;
import game.Coord;
import game.ImageLoader;

public class OuterWall extends Element {

	/*
	 * 
	 * 
	 * This class represent the outside of the wall, with its sprite and behavior 
	 * 
	 * 
	 */
	
	private String m_orientation;
	
	public OuterWall(Coord coord, OuterWallImageManager OWImageManager, String orientation, Automaton automaton) throws Exception {
		super(true, true, coord, automaton);
		m_orientation = orientation;
		String path = OWImageManager.get(m_orientation);
		if (path != null) {
			__image = ImageLoader.loadImage(path, SIZE);
		}		
	}
	
}
