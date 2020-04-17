package playerActions;


import automaton.Entity;

public class ActionJump implements IAction {
	
	public ActionJump() {}
	
	@Override
	public boolean apply(Entity e) {
		return e.jump();
	}

}
