package playerCond;

import automaton.Entity;

public class CondTrue implements ICondition {
	
	public CondTrue() {}
	
	@Override
	public boolean eval(Entity e) {
		return true;
	}
	
}
