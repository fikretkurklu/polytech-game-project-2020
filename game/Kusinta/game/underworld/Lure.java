package underworld;

import environnement.Element;
import game.Coord;

public class Lure extends Element {
	
	boolean elapsed;

	public Lure(boolean isSolid, boolean isVisible, Coord coord) {
		super(isSolid, isVisible, coord);
		elapsed = false;
	}

}
