package automaton.playerCond;

import automaton.Entity;
import automaton.ICondition;

public class CondTrue implements ICondition {
	
	public CondTrue() {}
	
	@Override
	public boolean eval(Entity e) {
		return true;
	}
	
}
