package playerActions;

import game.Direction;
import game.Entity;

public class ActionMove implements IAction{

	Direction m_dir;
	
	public ActionMove(Direction dir) {
		m_dir = dir;
	}
	
	@Override
	public boolean apply(Entity e) {
		return e.move(m_dir);
	}
	
	
}
