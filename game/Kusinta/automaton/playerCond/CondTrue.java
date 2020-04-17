package playerCond;

import game.Entity;

public class CondTrue implements ICondition {
	
	public CondTrue() {}
	
	@Override
	public boolean eval(Entity e) {
		return true;
	}
	
}
