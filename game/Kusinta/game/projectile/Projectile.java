package projectile;

import automaton.Category;
import automaton.Direction;
import automaton.Entity;
import player.Player;

public abstract class Projectile extends Entity {
	static final int OK_STATE = 1;
	static final int HIT_STATE = 2;
	
	int m_x, m_y;
	int m_state;
	double m_angle;
	Player m_shooter;
	
	public Projectile(int x, int y, double angle, Player player) {
		m_x = x;
		m_y = y;
		m_angle = angle;
		
		m_shooter = player;
	}

	@Override
	public boolean jump(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pop(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean wizz(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean power() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pick(Direction dir) {
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
	public boolean store() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean egg(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hit(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mydir(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cell(Direction dir, Category cat) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closest(Category cat, Direction dir) {
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
