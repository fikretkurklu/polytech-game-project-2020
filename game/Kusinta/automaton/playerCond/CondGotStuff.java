package playerCond;

import game.Entity;

public class CondGotStuff implements ICondition{

	public CondGotStuff() {}

	@Override
	public boolean eval(Entity e) {
		return e.gotstuff();
	}
	
	
}
