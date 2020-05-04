package playerActions;

import automaton.Direction;
import automaton.Entity;

public class ActionHit extends Action {
	
	Direction m_dir;
	
	public ActionHit(int percentage) {
		super(percentage);
		m_dir = Direction.F;
	}
	
	public ActionHit(Direction direction, int percentage) {
		super(percentage);
		m_dir = direction;
	}

	@Override
	public boolean apply(Entity e) {
		return e.hit(m_dir);
	}
	
	
	
}
