package underworld;

import automaton.Automaton;
import environnement.Element;
import game.Coord;

public class UndWall extends Element{
	
	public String m_orientation;
	public UndWall(Coord coord, UndWallImageManager UWImageManager, String orientation, Automaton automaton) throws Exception {
		super(true, true, coord, automaton);
		m_orientation = orientation;
		String path = UWImageManager.get(m_orientation);
		if (path != null) {
			loadImage(path);
		}		
	}
}
