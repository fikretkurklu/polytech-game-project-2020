package automaton;

import playerCond.*;
import playerActions.*;


public class Transition {

	ICondition m_condition;
	State finalState;
	IAction m_actions[];
	
	public Transition(ICondition condition, State finalState, IAction[] actions) {
		m_condition = condition;
		this.finalState = finalState;
		m_actions = actions;
	}
	
}
