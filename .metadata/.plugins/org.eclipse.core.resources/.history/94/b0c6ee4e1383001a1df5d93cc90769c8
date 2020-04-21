package playerActions;

import game.Direction;
import game.Entity;

public class ActionPop extends Action{
	
	Direction m_dir;
	
	public ActionPop(int percentage) {
		super(percentage);
		m_dir = new Direction("F");
	}
	
	public ActionPop(Direction direction, int percentage) {
		super(percentage);
		m_dir = direction;
	}
	
	@Override
	public boolean apply(Entity e) {
		return e.pop(m_dir);
	}	
	
}
