package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import automaton.*;

public class Player extends Entity {
	
	int DIMENSION = 5;
	
	double G = 9.81;
	double ACCELERATION = 0.01;
	double ACCELERATION_JUMP = 10.5;
	double ACCELERATION_POP = 11;
	
	boolean qPressed, zPressed, dPressed, espPressed, aPressed, ePressed;
	boolean falling, jumping, poping;
	
	int MAX_LIFE = 100;
	int SPEED_WALK = 3;
	
//	Room m_room;
	
	 int m_x, m_y;
	 Model m_model;
	 Direction m_direction;
	 
	 int life;

	 int dt_x, dt_y;
	 double speed_x, speed_y;
	 long m_ratio_x, m_ratio_y;
	 
	 long m_time;

	 int [] x_hitBox, y_hitBox;
	 
	 PlayerKeyListener m_pkl;
	 PlayerTimerListener m_tml;


	public Player(Automaton automaton, int x, int y, Direction dir) {
		super(automaton);
		
		 m_x = x;
		 m_y = y;
		 m_direction = dir;
		 
		 life = MAX_LIFE;

		 x_hitBox = new int[]{m_x-DIMENSION, m_x-DIMENSION, m_x+DIMENSION, m_x+DIMENSION};
		 y_hitBox = new int[]{m_y-DIMENSION, m_y+DIMENSION, m_y+DIMENSION, m_y-DIMENSION};
		 
		 m_pkl = new PlayerKeyListener();
		 m_tml = new PlayerTimerListener();
		 
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
			//if(!m_room.is_blocked(m_x + SPEED_WALK, m_y)){
			dt_x +=m_ratio_x;
			speed_x  = .5 * ACCELERATION * dt_x * dt_x;
			if(speed_x>SPEED_WALK)
				speed_x = SPEED_WALK;
			m_x += speed_x;
		} else if(dir.toString() == "West") {
			//if(!m_room.is_blocked(m_x - SPEED_WALK, m_y)){
			dt_x +=m_ratio_x;
			speed_x  = .5 * ACCELERATION * dt_x * dt_x;
			if(speed_x>SPEED_WALK)
				speed_x = SPEED_WALK;
			m_x -= speed_x;
		}
		return true;
	}

	@Override
	public boolean jump() {							//sauter
		// TODO Auto-generated method stub
		//if(!m_room.is_blocked(m_x, m_y + DIMENSION)){
		jumping = true;
		m_time = 0;
		return false;
	}

	@Override
	public boolean pop(Direction dir) {				//sauter
		// TODO Auto-generated method stub
		//if(!m_room.is_blocked(m_x, m_y + DIMENSION)){
		poping = true;
		m_time = 0;
		return false;
	}
	
	private void gravity(long t) {
		// TODO Auto-generated method stub
		double C;
		if (jumping) {
			C = ACCELERATION_JUMP;
		} else if(poping) {
			C = ACCELERATION_POP;
		} else {
			C = 0;
		}
		m_y = (int) ((-0.5 * G * t + C * t) + DIMENSION);
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
			return true;
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
	
private class PlayerTimerListener implements ActionListener{
		
		private int tick_time = 10;
		private long m_last;
		Timer m_timer;
		
		public PlayerTimerListener() {
			m_timer= new Timer(tick_time, this);
			m_timer.start();
		}

		public long getTickTime() {
			return tick_time;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			long now = System.currentTimeMillis();
			//if(!m_room.is_blocked(m_x, m_y - DIMENSION)){
			if(!falling) {
				m_time=0;
			} else {
				m_time += tick_time;
			}
			falling = true;
			gravity(m_time);
//			} else {
//			jumping = false;
//			falling= false;
			m_timer.restart();
			m_last = now;
		}

	}

}
