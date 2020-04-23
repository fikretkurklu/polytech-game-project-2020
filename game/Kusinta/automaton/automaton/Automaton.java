package automaton;

import java.util.HashMap;

import playerActions.Action;


public class Automaton {

	String name;
	State initialState;
	HashMap<State, Transition[]> mapTransitions;
	
	
	public Automaton(String name, State state, HashMap<State, Transition[]> transitions) {
		this.name = name;
		initialState = state;
		mapTransitions = transitions;
	}
	
	private Action randomAction(Action actions[]) {
		float randomNumber = (float) Math.random() * 100;
		float sum = 0;
		Action act;
		for (int i = 0 ; i < actions.length ; i++) {
			act = actions[i];
			sum += act.percentage;
			if (randomNumber <= sum) {
				return act;
			}
		}
		return actions[actions.length];
	}
	
	private State randomState(HashMap<State, Transition[]> t) {
        int ind = (int)(Math.random() * t.size());
        return  (State) (t.keySet().toArray())[ind];
    }
	
	public void step(Entity e) {
		Transition[] transitions = mapTransitions.get(e.getCurrentState());
		if (transitions != null) {
			for (int i = 0; i < transitions.length; i++) {
				if (transitions[i].m_condition.eval(e)) {
					Action actions[] = (Action[]) transitions[i].m_actions;
					if (actions.length == 1)
						actions[0].apply(e);
					else if (actions.length > 1)
						randomAction(actions).apply(e);
					if (transitions[i].finalState.getName().equals("_"))
						e.setCurrentState(randomState(mapTransitions));
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
