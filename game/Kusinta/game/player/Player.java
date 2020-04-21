package player;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import automaton.*;
import projectile.Arrow;

public class Player extends Character {

	int DIMENSION = 5;
	double G = 9.81;
	double ACCELERATION = 0.01;
	double ACCELERATION_JUMP = 10.5;
	double ACCELERATION_POP = 11;

	static final int WALKING = 0;
	static final int JUMPING = 1;
	static final int IDLE = 2;

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
	long m_imageElapsed;

	int m_state;

	public Player(Automaton automaton, int x, int y, Direction dir) throws IOException {
		super(automaton, x, y, dir);

		x_hitBox = new int[] { m_x - DIMENSION, m_x - DIMENSION, m_x + DIMENSION, m_x + DIMENSION };
		y_hitBox = new int[] { m_y - DIMENSION, m_y + DIMENSION, m_y + DIMENSION, m_y - DIMENSION };

		m_arrows = new LinkedList<Arrow>();
		m_shot_time = System.currentTimeMillis();

		m_imageElapsed = 0;
		m_state = IDLE;
		
		try {
			bI = loadSprite("resources/Player/spritePlayer.png", 7, 16);
		} catch(Exception e){
			e.printStackTrace();
		}
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

		if (now - m_shot_time > 1000) {

			double angle = Math.acos((m_model.m_mouseCoord.X() - m_x) / (Math.sqrt(
					Math.pow((m_model.m_mouseCoord.X() - m_x), 2) + Math.pow((m_model.m_mouseCoord.Y() - m_y), 2))));
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
			m_state = WALKING;
			if (speed_x == 0)
				dt_x = 0;
			return qPressed;
		}
		if (keyCode == 90) {
			m_state = JUMPING;
			return zPressed;
		}
		if (keyCode == 68) {
			m_state = WALKING;
			if (speed_x == 9)
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
		if (m_time >= 10)
			gravity(m_time);
//		} else {
//		jumping = false;
//		falling= false;
		m_imageElapsed += elapsed;
		if (m_imageElapsed > 10) {
			m_imageElapsed = 0;

			switch (m_state) {
			case WALKING:
				m_image_index = (m_image_index + 1) % 6 + 8;
				break;
			case JUMPING:
				m_image_index = (m_image_index + 1) % 9 + 15;
			default:
				m_image_index = (m_image_index + 1) % 4;
				break;
			}

		}
	}

	@Override
	public boolean store() {
		// TODO Auto-generated method stub
		return false;
	}

	public void paint(Graphics g) {
		if (bI != null) {
			BufferedImage img = bI[m_image_index];
			g.drawImage(img, m_x - DIMENSION, m_y - DIMENSION, DIMENSION * img.getWidth(), DIMENSION * img.getHeight(),
				null);
		}
	}

}
