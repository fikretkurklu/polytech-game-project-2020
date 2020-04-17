package automaton.playerActions;

import automaton.Direction;
import automaton.Entity;
import automaton.IAction;

public class ActionPop implements IAction{
	
	Direction m_dir;

	public ActionPop(Direction dir) {
		m_dir = dir;
	}
	
	@Override
	public boolean apply(Entity e) {
		return e.pop(m_dir);
	}	
	
}
