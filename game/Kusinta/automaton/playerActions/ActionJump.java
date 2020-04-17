package automaton.playerActions;


import automaton.Entity;
import automaton.IAction;

public class ActionJump implements IAction {
	
	public ActionJump() {}
	
	@Override
	public boolean apply(Entity e) {
		return e.jump();
	}

}
