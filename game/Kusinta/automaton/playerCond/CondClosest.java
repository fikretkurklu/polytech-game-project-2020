package automaton.playerCond;

import automaton.Entity;
import automaton.ICondition;

public class CondClosest implements ICondition{

	Entity closestEnemy;		//A remplacer par un autre champ si besoin
	
	public CondClosest(Entity closestEnemy) {
		this.closestEnemy = closestEnemy;
	}

	@Override
	public boolean eval(Entity e) {
		return e.closest(closestEnemy);
	}
	
	
}
