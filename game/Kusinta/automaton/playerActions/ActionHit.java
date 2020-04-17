package playerActions;

import automaton.Entity;

public class ActionHit implements IAction {

	public ActionHit() {}

	@Override
	public boolean apply(Entity e) {
		return e.hit();
	}
	
	
	
}
