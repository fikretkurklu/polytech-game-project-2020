package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import automaton.*;

public class Player extends Entity {
	
	double G = 9.81;
	double ACCELERATION = 0.01;
	
	boolean qPressed, zPressed, dPressed, espPressed, aPressed, ePressed;
	
	int MAX_LIFE = 100;
	int SPEED_WALK = 3;
	
//	Room m_room;
	
	 int m_x, m_y;
	 Model m_model;
	 Direction m_direction;
	 
	 int life;

	 int dt_x;
	 double speed_x;
	 long m_ratio_x;

	 int [] x_hitBox, y_hitBox;
	 
	 PlayerKeyListener pkl;


	public Player(Automaton automaton, int x, int y, Direction dir) {
		super(automaton);
		
		 m_x = x;
		 m_y = y;
		 m_direction = dir;
		 
		 life = MAX_LIFE;

		 x_hitBox = new int[]{m_x-5, m_x-5, m_x+5, m_x+5};
		 y_hitBox = new int[]{m_y-5, m_y+5, m_y+5, m_y-5};
		 
		 pkl = new PlayerKeyListener();
		 
		}
	
	public void setRatio(long ratio) {
		m_ratio_x = ratio;
	}

	@Override
	public boolean move(Direction dir) {			//bouger
		if(dir != m_direction) {
			turn(dir);
		}
		if(dir.toString() == "East") {
			//if(m_room.is_blocked(m_x,m_direction){
			dt_x +=m_ratio_x;
			speed_x  = .5 * ACCELERATION * dt_x * dt_x;
			if(speed_x>SPEED_WALK)
				speed_x = SPEED_WALK;
			m_x+=speed_x;
		} else if(dir.toString() == "West") {
			//if(m_room.is_blocked(m_x,m_direction){
			dt_x +=m_ratio_x;
			speed_x  = .5 * ACCELERATION * dt_x * dt_x;
			if(speed_x>SPEED_WALK)
				speed_x = SPEED_WALK;
			m_x-=speed_x;
		}
		return true;
	}

	@Override
	public boolean jump() {							//sauter
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pop(Direction dir) {				//sauter
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean wizz() {							//activer un levier
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean power() {						//Collision et perte de vie
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
		if(m_direction !=dir)
			m_direction = dir;
		return true;
	}

	@Override
	public boolean get() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean explode() {
		if(life!=0) {
			life = 0;
			return true;
		}
		return false;
	}

	@Override
	public boolean egg() {							//tir
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
	public boolean gotpower() {						//mort
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean key(int keyCode) {
		if(keyCode == 81)
			return qPressed;
		if(keyCode == 90)
			return zPressed;
		if(keyCode == 68)
			return dPressed;
		if(keyCode == 32)
			return espPressed;
		if(keyCode == 65)
			return aPressed;
		if(keyCode == 69)
			return ePressed;
		return false;
	}
	
	
	
	private class PlayerKeyListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == 81) {
				qPressed = true;
				dt_x = 0;
			}
			if(e.getKeyCode() == 90)
				zPressed = true;
			if(e.getKeyCode() == 68) {
				dPressed = true;
				dt_x = 0;
			}
			if(e.getKeyCode() == 32)
				espPressed = true;
			if(e.getKeyCode() == 65)
				aPressed = true;
			if(e.getKeyCode() == 69)
				ePressed = true;
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == 81)
				qPressed = false;
			if(e.getKeyCode() == 90)
				zPressed = false;
			if(e.getKeyCode() == 68)
				dPressed = false;
			if(e.getKeyCode() == 32)
				espPressed = false;
			if(e.getKeyCode() == 65)
				aPressed = false;
			if(e.getKeyCode() == 69)
				ePressed = false;
		}
	}
	

}
