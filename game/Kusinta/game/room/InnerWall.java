package room;

import java.awt.Image;

import environnement.Element;
import game.Coord;
/*
 * 
 * Dans le cas d'un InnerWall, les images sont dans un tableau de String
 * 
 */

public class InnerWall extends Element {

	public InnerWall(Coord coord, Image i) throws Exception {
		super(false, true, coord);
		__image = i;

	}

}
