package room;

import automaton.Automaton;
import game.Coord;

/*
 * 
 * Dans le cas d'un InnerWall, les images sont dans un tableau de String
 * 
 */

public class InnerWall extends Element {
	
	public InnerWall(Coord coord, InnerWallImageManager IWImageManager, Automaton automaton) throws Exception {
		super(true, true, coord, automaton);
		String path = IWImageManager.get("", IWImageManager.useImageTable);
		if (path != null) {
			loadImage(path);
		}
	}


}
