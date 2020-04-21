package playerCond;

import automaton.*;

import game.Entity;
import game.Direction;

public class CondCell implements ICondition{
	
	Direction direction;
	Entity collisionEntity;				//A modifier par un autre champ si besoin

	public CondCell(Direction direction, Entity collisionEntity) {
		this.collisionEntity = collisionEntity;
	}

	@Override
	public boolean eval(Entity e) {
		return e.cell(collisionEntity);
	}
	
}
