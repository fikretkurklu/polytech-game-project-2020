package playerActions;

import automaton.Entity;

public class ActionPower extends Action {
	
	public ActionPower(int percentage) {
		super(percentage);
	}
	
	@Override
	public boolean apply(Entity e) {
		return e.power();
	}
	
}
