package playerActions;

import automaton.*;

public class ActionEgg extends Action {
	
	Direction m_dir;
	
	public ActionEgg(int percentage) {
		super(percentage);
		m_dir = new Direction("F");
	}
	
	public ActionEgg(Direction direction, int percentage) {
		super(percentage);
		m_dir = direction;
	}

	@Override
	public boolean apply(Entity e) {
		return e.egg(m_dir);
	}
	
}
