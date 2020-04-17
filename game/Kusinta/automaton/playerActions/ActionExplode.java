package automaton.playerActions;

import automaton.Entity;
import automaton.IAction;

public class ActionExplode implements IAction {

	public ActionExplode(){}
	
	@Override
	public boolean apply(Entity e) {
		return e.explode();
	}
	
	
}
