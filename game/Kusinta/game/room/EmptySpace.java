package room;

import automaton.Automaton;
import game.Coord;

public class EmptySpace extends Element {
	
	
	/*
	 * 
	 * This class represent empty spaces in the room that have a sprite and a specific behavior
	 * 
	 * 
	 */

	public EmptySpace(Coord coord, EmptySpaceImageManager ESImageManager, Automaton automaton) throws Exception {
		super(false, true, coord, automaton);
		String path = ESImageManager.get("", ESImageManager.useImageTable);
		if (path != null) {
			loadImage(path);
		}
	}

	
}
