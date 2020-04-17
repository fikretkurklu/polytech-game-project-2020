package automaton.playerCond;

import automaton.Entity;
import automaton.ICondition;

public class CondGotPower implements ICondition{

	public CondGotPower() {}

	@Override
	public boolean eval(Entity e) {
		return e.gotpower();
	}
	
		
}
