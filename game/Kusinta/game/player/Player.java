package player;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import automaton.*;
import projectile.Arrow;

public class Player extends Character {

	int DIMENSION = 5;
	double G = 9.81;
	double ACCELERATION = 0.01;
	double ACCELERATION_JUMP = 10.5;
	double ACCELERATION_POP = 11;

	boolean qPressed, zPressed, dPressed, espPressed, aPressed, ePressed;
	boolean falling, jumping, poping;

	int SPEED_WALK = 3;

	int m_mouse_x, m_mouse_y;

	int dt_x, dt_y;
	double speed_x, speed_y;
	long m_ratio_x, m_ratio_y;

	long m_time, m_shot_time;

	int[] x_hitBox, y_hitBox;

	public LinkedList<Arrow> m_arrows;	


	public Player(Automaton automaton, int x, int y, Direction dir) {
		super(automaton, x, y, dir);

		x_hitBox = new int[] { m_x - DIMENSION, m_x - DIMENSION, m_x + DIMENSION, m_x + DIMENSION };
		y_hitBox = new int[] { m_y - DIMENSION, m_y + DIMENSION, m_y + DIMENSION, m_y - DIMENSION };


		m_arrows = new LinkedList<Arrow>();
		m_shot_time = System.currentTimeMillis();
	}

	public void setRatio(long ratio) {
		m_ratio_x = ratio;
	}

	@Override
	public boolean move(Direction dir) { // bouger
		if (dir != m_direction) {
			turn(dir);
		}
		if (dir.toString() == "East") {
			// if(!m_room.is_blocked(m_x + SPEED_WALK, m_y)){
			dt_x += m_ratio_x;
			speed_x = .5 * ACCELERATION * dt_x * dt_x;
			if (speed_x > SPEED_WALK)
				speed_x = SPEED_WALK;
			m_x += speed_x;
		} else if (dir.toString() == "West") {
			// if(!m_room.is_blocked(m_x - SPEED_WALK, m_y)){
			dt_x += m_ratio_x;
			speed_x = .5 * ACCELERATION * dt_x * dt_x;
			if (speed_x > SPEED_WALK)
				speed_x = SPEED_WALK;
			m_x -= speed_x;
		}
		return true;
	}

	@Override
	public boolean jump(Direction dir) { // sauter
		// TODO Auto-generated method stub
		// if(!m_room.is_blocked(m_x, m_y + DIMENSION)){
		jumping = true;
		m_time = 0;
		return false;
	}

	@Override
	public boolean pop(Direction dir) { // sauter
		// TODO Auto-generated method stub
		// if(!m_room.is_blocked(m_x, m_y + DIMENSION)){
		poping = true;
		m_time = 0;
		return false;
	}

	private void gravity(long t) {
		// TODO Auto-generated method stub
		double C;
		if (jumping) {
			C = ACCELERATION_JUMP;
		} else if (poping) {
			C = ACCELERATION_POP;
		} else {
			C = 0;
		}
		m_y = (int) ((-0.5 * G * t + C * t) + DIMENSION);
	}

	

	@Override
	public boolean egg(Direction dir) { // tir
		// TODO Auto-generated method stub
		long now = System.currentTimeMillis();
		
		if(now - m_shot_time > 1000) {
			
			double angle = Math.acos((m_model.m_mouseCoord.X() - m_x)/(Math.sqrt(Math.pow((m_model.m_mouseCoord.X() -m_x), 2) + Math.pow((m_model.m_mouseCoord.Y()-m_y), 2))));
			m_arrows.add(new Arrow(m_x, m_y, angle, this));
			
			return true;
		}
		
		return false;
	}
	
	public void setPressed(int keyCode, boolean pressed) {
		if (keyCode == 81)
			qPressed = pressed;
		if (keyCode == 90)
			zPressed = pressed;
		if (keyCode == 68)
			dPressed = pressed;
		if (keyCode == 32)
			espPressed = pressed;
		if (keyCode == 65)
			aPressed = pressed;
		if (keyCode == 69)
			ePressed = pressed;
	}

	@Override
	public boolean key(int keyCode) {
		if (keyCode == 81) {
			if(speed_x == 0)
				dt_x = 0;
			return qPressed;
		}
		if (keyCode == 90)
			return zPressed;
		if (keyCode == 68) {
			if(speed_x == 9)
				dt_x = 0;
			return dPressed;
		}
		if (keyCode == 32)
			return espPressed;
		if (keyCode == 65)
			return aPressed;
		if (keyCode == 69)
			return ePressed;
		return false;
	}
	
	public void tick(long elapsed) {
		// if(!m_room.is_blocked(m_x, m_y - DIMENSION)){
		if (!falling) {
			m_time = 0;
		} else {
			m_time += elapsed;
		}
		falling = true;
		if(m_time >= 10)
			gravity(m_time);
//		} else {
//		jumping = false;
//		falling= false;
	}

	@Override
	public boolean store() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private class PlayerMouseListener implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			m_mouse_x = e.getX();
			m_mouse_y = e.getY();
		}
		
	}
	
}
