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
		super(automaton, x, y, dir, model, 100, 100, 0, 0, 0);
		hidden = false;
	}
	
	private boolean contains(Cloud cloud) {
		return ((getCoord().X() >= cloud.getCoord().X()) && (getCoord().X() <= cloud.xMax) && (getCoord().Y() >= cloud.getCoord().Y()) && (getCoord().Y() <= cloud.yMax));
	}
	
	@Override
	public boolean cell(Direction dir, Category cat) {
		if ((dir.toString() == "H") && (cat.toString() == "O")) {
			Cloud clouds[] = getModel().m_underworld.m_clouds;
			for (int i = 0 ; i < clouds.length ; i++) {
				if  (contains(clouds[i]))
					return true;
			}
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
		switch (m_direction.toString()) {
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
	
	@Override
	public boolean jump(Direction dir) {
		switch (m_direction.toString()) {
		case "N" : 
			getCoord().translate(0, - 40);
			return true;
		case "S" : 
			getCoord().translate(0, 40);
			return true;
		case "E" :
			getCoord().translate(40, 0);
			return true;
		case "W" :
			getCoord().translate(-40, 0);
			return true;
		default :
			return false;
		}
	}
	
	// Ajouter la création du leurre (avec newCoord)
	@Override
	public boolean egg(Direction dir) {
		Coord newCoord = new Coord(getCoord().X(), getCoord().Y());
		switch (m_direction.toString()) {
			case "N" : 
				newCoord.translate(0, - 40);
				return true;
			case "S" : 
				newCoord.translate(0, 40);
				return true;
			case "E" :
				newCoord.translate(40, 0);
				return true;
			case "W" :
				newCoord.translate(-40, 0);
				return true;
			default :
				return false;
		}
	}
	
	
	
	
	

	
}
