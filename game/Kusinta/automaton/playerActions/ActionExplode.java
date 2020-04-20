package playerActions;

import game.Entity;

public class ActionExplode extends Action {
	
	public ActionExplode(int percentage){
		super(percentage);
	}
	
	@Override
	public boolean apply(Entity e) {
		return e.explode();
	}
	
	
}
