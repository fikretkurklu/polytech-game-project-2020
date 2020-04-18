package automaton;

import playerCond.*;
import playerActions.*;


public class Transition {

	ICondition m_condition;
	State finalState;
	IAction m_action;
	
	public Transition(ICondition condition, State finalState, IAction action) {
		m_condition = condition;
		this.finalState = finalState;
		m_action = action;
	}
	
}
