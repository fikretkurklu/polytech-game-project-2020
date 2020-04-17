package game;

import automaton.*;

public class Player extends Entity {
	
	 int m_x, m_y;
	 Model m_model;
	 String direction;

	 double speed_x, speed_y;

	 int [] x_hitBox, y_hitBox;


	public Player(Automaton automaton, int x, int y) {
		super(automaton);
		
		 m_x = x;
		 m_y = y;

		 x_hitBox = new int[]{m_x-5, m_x-5, m_x+5, m_x+5};
		 y_hitBox = new int[]{m_y-5, m_y+5, m_y+5, m_y-5};
		}

	@Override
	public boolean move(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean jump() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pop(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean wizz() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean power() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pick() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean turn(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean get() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean explode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean egg() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mydir(Direction m_dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cell(Entity collisionEntity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closest(Entity closestEnemy) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean gotstuff() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean gotpower() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean key(int keyCode) {
		// TODO Auto-generated method stub
		return false;
	}

}
