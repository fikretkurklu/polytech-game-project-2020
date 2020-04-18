package playerCond;

import game.Direction;
import game.Entity;

public class CondClosest implements ICondition{

	Direction direction;
	Entity closestEnemy;		//A remplacer par un autre champ si besoin
	
	
	public CondClosest(Direction direction, Entity closestEnemy) {
		this.direction = direction;
		this.closestEnemy = closestEnemy;
	}

	@Override
	public boolean eval(Entity e) {
		return e.closest(closestEnemy);
	}
	
	
}
