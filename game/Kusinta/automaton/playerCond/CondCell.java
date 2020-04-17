package playerCond;

import automaton.*;
import game.Entity;

public class CondCell implements ICondition{
	
	Entity collisionEntity;				//A modifier par un autre champ si besoin

	public CondCell(Entity collisionEntity) {
		this.collisionEntity = collisionEntity;
	}

	@Override
	public boolean eval(Entity e) {
		return e.cell(collisionEntity);
	}
	
}
