package playerActions;

import automaton.Entity;

public class ActionStore extends Action{
	
	public ActionStore(int percentage) {
		super(percentage);
	}
	
	@Override
	public boolean apply(Entity e) {
		return e.store();
	}

}
