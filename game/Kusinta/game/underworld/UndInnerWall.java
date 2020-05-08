package underworld;

import environnement.Element;
import game.Coord;

public class UndInnerWall extends Element {	
	public UndInnerWall(Coord coord) throws Exception {
		super(true, true, coord);
	}
}
