package automaton.playerActions;

import automaton.Entity;
import automaton.IAction;

public class ActionPick implements IAction{

	public ActionPick() {}
	
	@Override
	public boolean apply(Entity e) {
		return e.pick();
	}	
	
	
}
