package automaton;

import automaton.Automaton;
import automaton.State;

public abstract class Entity {

	private State m_state;
	protected Automaton m_automaton;
	
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
	
	public boolean move(Direction dir) {
		return false;
	}
	public boolean jump(Direction dir){
		return false;
	}

	public boolean pop(Direction dir){
		return false;
	}
	public boolean wizz(Direction dir){
		return false;
	}
	public boolean power(){
		return false;
	}
	public boolean pick(Direction dir){
		return false;
	}
	public boolean throwAction(Direction dir){
		return false;
	}
	public boolean turn(Direction dir){
		return false;
	}
	public boolean get(){
		return false;
	}
	public boolean store(){
		return false;
	}
	public boolean explode(){
		return false;
	}
	public boolean egg(Direction dir){
		return false;
	}
	public boolean hit(Direction dir){
		return false;
	}
	public boolean protect(Direction dir){
		return false;
	}
	
	public boolean waitAction(){
		return false;
	}
	//Conditions
	
	public boolean mydir(Direction dir){
		return false;
	}
	public boolean cell(Direction dir, Category cat){
		return false;
	}
	public boolean closest(Category cat, Direction dir){
		return false;
	}
	public boolean gotstuff(){
		return false;
	}
	public boolean gotpower(){
		return false;
	}
	public boolean key(int keyCode){
		return false;
	}
	
	public Automaton getAutomaton() {
		return m_automaton;
	}
	
}
