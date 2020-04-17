package automaton;



public class Transition {

	ICondition m_condition;
	State initialState;
	State finalState;
	IAction m_action;
	
	public Transition(ICondition condition, State initialState, State finalState, IAction action) {
		m_condition = condition;
		this.initialState = initialState;
		this.finalState = finalState;
		m_action = action;
	}
	
}
