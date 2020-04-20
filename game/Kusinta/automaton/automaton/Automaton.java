package automaton;

import java.util.HashMap;

import game.Entity;
import playerActions.Action;
import playerActions.IAction;
import playerCond.*;

public class Automaton {

	State initialState;
	HashMap<State, Transition[]> mapTransitions;
	
	
	public Automaton(State state, HashMap<State, Transition[]> transitions) {
		initialState = state;
		mapTransitions = transitions;
	}
	
	private Action random(Action actions[]) {
		
	}
	
	public void step(Entity e) {
		Transition[] transitions = mapTransitions.get(e.getCurrentState());
		if (transitions != null) {
			for (int i = 0; i < transitions.length; i++) {
				if (transitions[i].m_condition.eval(e)) {
					Action actions[] = (Action[]) transitions[i].m_actions;
					if (actions.length == 1)
						actions[0].apply(e);
					else
						
					e.setCurrentState(transitions[i].finalState);
				}
			}
		}
	
	}
	
	public State getInitialState() {
		return initialState;
	}
}
