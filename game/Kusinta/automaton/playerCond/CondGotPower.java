package playerCond;

import game.Entity;

public class CondGotPower implements ICondition{

	public CondGotPower() {}

	@Override
	public boolean eval(Entity e) {
		return e.gotpower();
	}
	
		
}
