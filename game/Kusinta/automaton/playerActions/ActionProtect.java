package playerActions;

import automaton.Direction;
import automaton.Entity;

public class ActionProtect extends Action{
	Direction m_dir;
	
	public ActionProtect(int percentage) {
		super(percentage);
		m_dir = new Direction("F");
	}
	
	public ActionProtect(Direction direction, int percentage) {
		super(percentage);
		m_dir = direction;
	}

	@Override
	public boolean apply(Entity e) {
		return e.protect(m_dir);
	}
}
