package underworld;

import game.Coord;
import environnement.Element;

public class UnderworldEmptySpace extends Element {
	public UnderworldEmptySpace(Coord coord) throws Exception {
		super(false, false, coord);
	}

}
