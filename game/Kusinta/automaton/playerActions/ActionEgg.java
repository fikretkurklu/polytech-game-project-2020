package automaton.playerActions;

import automaton.Entity;
import automaton.IAction;

public class ActionEgg implements IAction {

	public ActionEgg() {}
	
	@Override
	public boolean apply(Entity e) {
		return e.egg();
	}	
	
}
