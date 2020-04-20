package game;

import automaton.Automaton;

public class Block extends Entity {
	
	public Block(Automaton automaton) {
		super(automaton);
	}
	
	@Override
	public boolean move(Direction dir) {
		System.out.println("Move Block");
		System.out.println(this.getCurrentState().getName());
		return true;
	}

	@Override
	public boolean jump() {
		System.out.println("Jump Block");
		System.out.println(this.getCurrentState().getName());
		return true;
	}

	@Override
	public boolean pop(Direction dir) {
		System.out.println("Pop Block");
		System.out.println(this.getCurrentState().getName());
		return true;
	}

	@Override
	public boolean wizz() {
		System.out.println("Wizz Block");
		System.out.println(this.getCurrentState().getName());
		return true;
	}

	@Override
	public boolean power() {
		System.out.println("Power Block");
		System.out.println(this.getCurrentState().getName());
		return true;
	}

	@Override
	public boolean pick() {
		System.out.println("Pick Block");
		System.out.println(this.getCurrentState().getName());
		return true;
	}

	@Override
	public boolean turn(Direction dir) {
		System.out.println("Turn Block");
		System.out.println(this.getCurrentState().getName());
		return true;
	}

	@Override
	public boolean get() {
		System.out.println("Get Block");
		System.out.println(this.getCurrentState().getName());
		return true;
	}

	@Override
	public boolean explode() {
		System.out.println("Explode Block");
		System.out.println(this.getCurrentState().getName());
		return true;
	}

	@Override
	public boolean egg() {
		System.out.println("Egg Block");
		System.out.println(this.getCurrentState().getName());
		return true;
	}

	@Override
	public boolean hit() {
		System.out.println("Hit Block");
		System.out.println(this.getCurrentState().getName());
		return true;
	}

	@Override
	public boolean mydir(Direction m_dir) {
		System.out.println("Cond mydir");
		return true;
	}

	@Override
	public boolean cell(Entity collisionEntity) {
		System.out.println("Cond cell");
		return true;
	}

	@Override
	public boolean closest(Entity closestEnemy) {
		System.out.println("Cond closest");
		return true;
	}

	@Override
	public boolean gotstuff() {
		System.out.println("Cond gotstuff");
		return true;
	}

	@Override
	public boolean gotpower() {
		System.out.println("Cond gotpower");
		return true;
	}

	@Override
	public boolean key(int keyCode) {
		System.out.println("Cond key");
		return true;
	}
	
	
	
}
