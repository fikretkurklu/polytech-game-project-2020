package playerActions;

import game.Entity;

public class ActionPick implements IAction{

	public ActionPick() {}
	
	@Override
	public boolean apply(Entity e) {
		return e.pick();
	}	
	
	
}
