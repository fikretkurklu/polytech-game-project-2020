package playerCond;

import automaton.*;
import game.Entity;

public interface ICondition {
	
	boolean eval(Entity e);
	
}
