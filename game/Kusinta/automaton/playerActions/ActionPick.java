package playerActions;

import automaton.Direction;
import automaton.Entity;

public class ActionPick extends Action{
	
	Direction m_dir;
	
	public ActionPick(int percentage) {
		super(percentage);
		m_dir = new Direction("F");
	}
	
	public ActionPick(Direction direction, int percentage) {
		super(percentage);
		m_dir = direction;
	}
	
	@Override
	public boolean apply(Entity e) {
		return e.pick(m_dir);
	}	
	
	
}
