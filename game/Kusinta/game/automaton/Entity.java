package automaton;

import automaton.Automaton;
import automaton.State;

public abstract class Entity {

	private State m_state;
	Automaton m_automaton;
	
	public Entity() {}
	
	public Entity(Automaton automaton) {
		m_state = automaton.getInitialState();
		m_automaton = automaton;
	}
	
	public State getCurrentState() {
		return m_state;
	}
	
	public void setCurrentState(State state) {
		m_state = state;
	}
	
	//Action
	
	public abstract boolean move(Direction dir);
	public abstract boolean jump(Direction dir);
	public abstract boolean pop(Direction dir);
	public abstract boolean wizz(Direction dir);
	public abstract boolean power();
	public abstract boolean pick(Direction dir);
	public abstract boolean throwAction(Direction dir);
	public abstract boolean turn(Direction dir);
	public abstract boolean get();
	public abstract boolean store();
	public abstract boolean explode();
	public abstract boolean egg(Direction dir);
	public abstract boolean hit(Direction dir);
	public abstract boolean protect(Direction dir);

	//Conditions
	
	public abstract boolean mydir(Direction dir);
	public abstract boolean cell(Direction dir, Category cat);
	public abstract boolean closest(Category cat, Direction dir);
	public abstract boolean gotstuff();
	public abstract boolean gotpower();
	public abstract boolean key(int keyCode);
	
	
}
