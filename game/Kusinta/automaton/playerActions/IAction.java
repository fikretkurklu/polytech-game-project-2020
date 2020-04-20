package playerActions;

import automaton.*;
import game.Entity;

public interface IAction {
	
	boolean apply(Entity e);
	
}
