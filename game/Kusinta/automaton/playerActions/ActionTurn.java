package playerActions;

import automaton.*;
import game.Direction;
import game.Entity;

public class ActionTurn extends Action {

	Direction m_dir;
	
	public ActionTurn(int percentage) {
		super(percentage);
		m_dir = new Direction("R");
	}
	
	public ActionTurn(Direction direction, int percentage) {
		super(percentage);
		m_dir = direction;
	}
	
	
	@Override
	public boolean apply(Entity e) {
		return e.turn(m_dir);
	}
}
