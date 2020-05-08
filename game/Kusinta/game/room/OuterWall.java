package room;

import java.awt.Image;

import automaton.Automaton;
import environnement.Element;
import game.Coord;

public class OuterWall extends Element {

	/*
	 * 
	 * 
	 * This class represent the outside of the wall, with its sprite and behavior 
	 * 
	 * 
	 */
	
	public OuterWall(Coord coord, Image i, Automaton automaton) throws Exception {
		super(true, true, coord, automaton);
		__image = i;
	}
	
}
