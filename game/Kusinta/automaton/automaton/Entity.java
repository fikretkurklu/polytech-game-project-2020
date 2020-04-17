package automaton;

public abstract class Entity {

	private State m_state;
	Automaton m_automaton;
	
	public Entity(Automaton automaton) {
		m_state = automaton.initialState;
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
	public abstract boolean jump();
	public abstract boolean pop(Direction dir);
	public abstract boolean wizz();
	public abstract boolean power();
	public abstract boolean pick();
	public abstract boolean turn(Direction dir);
	public abstract boolean get();
	public abstract boolean explode();
	public abstract boolean egg();
	public abstract boolean hit();

	//Conditions
	
	public abstract boolean mydir(Direction m_dir);
	public abstract boolean cell(Entity collisionEntity);
	public abstract boolean closest(Entity closestEnemy);
	public abstract boolean gotstuff();
	public abstract boolean gotpower();
	public abstract boolean key(int keyCode);
	
	
}
