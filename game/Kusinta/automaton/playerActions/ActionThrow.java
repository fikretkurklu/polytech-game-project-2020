package playerActions;

import automaton.Direction;
import automaton.Entity;

public class ActionThrow extends Action{
	Direction m_dir;
	
	public ActionThrow(int percentage) {
		super(percentage);
		m_dir = Direction.F;
	}
	
	public ActionThrow(Direction direction, int percentage) {
		super(percentage);
		m_dir = direction;
	}
	
	@Override
	public boolean apply(Entity e) {
		return e.throwAction(m_dir);
	}	
	
}
