package playerActions;


import automaton.Direction;
import automaton.Entity;

public class ActionJump extends Action {
	
	Direction m_dir;
	
	public ActionJump(int percentage) {
		super(percentage);
		m_dir = Direction.F;
	}
	
	public ActionJump(Direction direction, int percentage) {
		super(percentage);
		m_dir = direction;
	}
	
	@Override
	public boolean apply(Entity e) {
		return e.jump(m_dir);
	}

}
