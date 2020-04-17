package playerCond;

import game.Entity;

public class CondKey implements ICondition {
	
	int keyCode;
	
	public CondKey(int keyCode) {
		this.keyCode = keyCode;
	}

	@Override
	public boolean eval(Entity e) {
		return e.key(keyCode);
	}
	
	
}
