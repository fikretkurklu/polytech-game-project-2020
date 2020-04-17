package automaton.playerCond;

import automaton.Entity;
import automaton.ICondition;

public class CondGotStuff implements ICondition{

	public CondGotStuff() {}

	@Override
	public boolean eval(Entity e) {
		return e.gotstuff();
	}
	
	
}
