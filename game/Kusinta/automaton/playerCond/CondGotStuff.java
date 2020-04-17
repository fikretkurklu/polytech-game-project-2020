package playerCond;

import automaton.Entity;

public class CondGotStuff implements ICondition{

	public CondGotStuff() {}

	@Override
	public boolean eval(Entity e) {
		return e.gotstuff();
	}
	
	
}
