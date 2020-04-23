package underworld;

import java.io.IOException;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import game.Coord;
import game.Model;
import player.Character;

public class PlayerSoul extends Character {
	
	boolean hidden;
	
	/* A faire :
	 * Animation dash (Jump)
	 * Animation leurre (Egg)
	 */
	
	public PlayerSoul(Automaton automaton, Coord coord, int x, int y, Direction dir, Model model) throws IOException {
		super(automaton, x, y, dir, model);
		hidden = false;
	}
	
	@Override
	public boolean cell(Direction dir, Category cat) {
		if ((dir.toString() == "H") && (cat.toString() == "O")) {
			/*On demande au model les coordonnées des nuages*/
		}
		return false;
	}
	
	@Override
	public boolean pop(Direction dir) {
		hidden = true;
		return true;
	}
	
	@Override
	public boolean wizz(Direction dir) {
		hidden = false;
		return true;
	}
	
	@Override
	public boolean move(Direction dir) {
		switch (dir.toString()) {
			case "N" : 
				getCoord().translate(0, - 20);
				return true;
			case "S" : 
				getCoord().translate(0, 20);
				return true;
			case "E" :
				getCoord().translate(20, 0);
				return true;
			case "W" :
				getCoord().translate(-20, 0);
				return true;
			default :
				return false;
		}
	}
	
	public int getX() {
		return getCoord().X();
	}
	
	public int getY() {
		return getCoord().Y();
	}
	
	
	
	
	

	
}
