package automaton.playerActions;

import automaton.Entity;
import automaton.IAction;

public class ActionHit implements IAction {

	public ActionHit() {}

	@Override
	public boolean apply(Entity e) {
		return e.hit();
	}
	
	
	
}
