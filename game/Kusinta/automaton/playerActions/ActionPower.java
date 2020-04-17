package playerActions;

import game.Entity;

public class ActionPower implements IAction {

	public ActionPower() {}
	
	@Override
	public boolean apply(Entity e) {
		return e.power();
	}
	
}
