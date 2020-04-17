package playerActions;

import automaton.Entity;

public class ActionExplode implements IAction {

	public ActionExplode(){}
	
	@Override
	public boolean apply(Entity e) {
		return e.explode();
	}
	
	
}
