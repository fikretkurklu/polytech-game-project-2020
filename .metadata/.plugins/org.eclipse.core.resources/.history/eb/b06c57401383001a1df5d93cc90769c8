package playerActions;

import game.Direction;
import game.Entity;

public class ActionMove extends Action{

	Direction m_dir;
	
	public ActionMove(int percentage) {
		super(percentage);
		m_dir = new Direction("F");
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
