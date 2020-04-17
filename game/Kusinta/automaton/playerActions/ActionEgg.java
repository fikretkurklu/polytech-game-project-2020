package playerActions;

import automaton.*;
import game.Entity;

public class ActionEgg implements IAction {

	public ActionEgg() {}
	

	@Override
	public boolean apply(Entity e) {
		return e.egg();
	}
	
}
