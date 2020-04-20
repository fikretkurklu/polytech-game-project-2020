package automaton;

import java.util.HashMap;

import game.Entity;
import playerCond.*;

public class Automaton {

	State initialState;
	HashMap<State, Transition[]> mapTransitions;
	
	
	public Automaton(State state, HashMap<State, Transition[]> transitions) {
		initialState = state;
		mapTransitions = transitions;
	}
	
	public void step(Entity e) {
		Transition[] transitions = mapTransitions.get(e.getCurrentState());
		if (transitions != null) {
			for (int i = 0; i < transitions.length; i++) {
				if (transitions[i].m_condition.eval(e)) {
					transitions[i].m_action.apply(e);
					e.setCurrentState(transitions[i].finalState);
				}
			}
		}
	
	}
	
	public State getInitialState() {
		return initialState;
	}
}
