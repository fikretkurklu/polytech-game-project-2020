package underworld;

import automaton.Entity;
import game.Coord;

public class PlayerSoul extends Entity {
	
	Coord m_coord;
	boolean hidden;
	
	public PlayerSoul(Coord coord) {
		m_coord = coord;
		hidden = false;
	}
	
	public int getX() {
		return m_coord.X();
	}
	
	public int getY() {
		return m_coord.Y();
	}
	
	public boolean playerHide() {
		return hidden;
	}

	
}
