package playerActions;

import game.Direction;
import game.Entity;

public class ActionWizz extends Action{
	
	Direction m_dir;
	
	public ActionWizz(int percentage) {
		super(percentage);
		m_dir = new Direction("F");
	}
	
	public ActionWizz(Direction direction, int percentage) {
		super(percentage);
		m_dir = direction;
	}
	
	@Override
	public boolean apply(Entity e) {
		return e.wizz(m_dir);
	}
	
}
