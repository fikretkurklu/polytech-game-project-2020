package room;

import java.awt.Image;

import environnement.Element;
import game.Coord;
import game.ImageLoader;

public class EmptySpace extends Element {

	/*
	 * 
	 * This class represent empty spaces in the room that have a sprite and a
	 * specific behavior
	 * 
	 * 
	 */
	public EmptySpace(Coord coord, Image i) throws Exception {
		super(false, true, coord);
		__image = i;

	}

}
