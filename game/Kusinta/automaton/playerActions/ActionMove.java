package playerActions;

import automaton.Direction;
import automaton.Entity;

public class ActionMove extends Action{

	Direction m_dir;
	
	public ActionMove(int percentage) {
		super(percentage);
		m_dir = Direction.F;
	}
	
	public ActionMove(Direction direction, int percentage) {
		super(percentage);
		m_dir = direction;
	}
	
	@Override
	public boolean apply(Entity e) {
		return e.move(m_dir);
	}
	
	
}
