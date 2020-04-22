package player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import automaton.*;
import game.Model;
import projectile.Arrow;

public class Player extends Character {

	int DIMENSION = 5;
	double G = 9.81;
	double ACCELERATION = 0.01;
	double ACCELERATION_JUMP = 1.5;
	double ACCELERATION_POP = 0.5;
	
	int SPEED_WALK = 1;

	static final int WALKING = 0;
	static final int JUMPING = 1;
	static final int IDLE = 2;

	boolean qPressed, zPressed, dPressed, espPressed, aPressed, ePressed;
	boolean falling, jumping, poping;
	
	int m_width, m_height;
	
	int y_gravity;
	
	int dt_x, dt_y;
	double speed_x, speed_y;
	long m_ratio_x, m_ratio_y;

	long m_time, m_shot_time;

	int[] x_hitBox, y_hitBox;

	public LinkedList<Arrow> m_arrows;
	long m_imageElapsed;

	int m_State;

	public Player(Automaton automaton, int x, int y, Direction dir, Model model) throws IOException {
		super(automaton, x, y, dir, model);
		
		try {
			bI = loadSprite("resources/Player/spritePlayer.png", 16, 7);
			m_width = bI[0].getWidth();
			m_height= DIMENSION * bI[0].getHeight();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int m_x = m_coord.X();
		int m_y = m_coord.Y();

		x_hitBox = new int[] { m_x - m_width, m_x - m_width, m_x + m_width, m_x + m_width};
		y_hitBox = new int[] { m_y, m_y - m_height, m_y - m_height, m_y};

		m_arrows = new LinkedList<Arrow>();
		m_shot_time = System.currentTimeMillis();

		m_imageElapsed = 0;
		m_State = IDLE;
		
		qPressed = false;
		zPressed = false;
		dPressed = false;
		espPressed = false;
		aPressed = false;
		ePressed = false;
	}

	@Override
	public boolean move(Direction dir) { // bouger
		m_State = WALKING;
		int m_x = m_coord.X();
		int m_y = m_coord.Y();
		
		if (!dir.toString().equals(m_direction.toString())) {
			turn(dir);
		}
		if (dir.toString().equals("E")) {
			if(!m_model.m_room.isBlocked(m_x + SPEED_WALK, m_y-1) && !m_model.m_room.isBlocked(m_x + m_width, m_y-m_height) && !m_model.m_room.isBlocked(m_x + m_width, m_y-m_height/2)){
				dt_x += m_ratio_x;
				speed_x = .5 * ACCELERATION * dt_x * dt_x;
				if (speed_x > SPEED_WALK)
					speed_x = SPEED_WALK;
				m_x += speed_x;
				m_coord.setX(m_x);
			}
		} else if (dir.toString().equals("W")) {
			if(!m_model.m_room.isBlocked(m_x - SPEED_WALK, m_y-1) && !m_model.m_room.isBlocked(m_x - m_width, m_y-m_height)){
				dt_x += m_ratio_x;
				speed_x = .5 * ACCELERATION * dt_x * dt_x;
				if (speed_x > SPEED_WALK)
					speed_x = SPEED_WALK;
				m_x -= speed_x;
				m_coord.setX(m_x);
			}
		}
		return true;
	}

	@Override
	public boolean jump(Direction dir) { // sauter
		// TODO Auto-generated method stub
		if(!m_model.m_room.isBlocked(m_coord.X(), m_coord.Y()+ m_height)){
			m_State = JUMPING;
			y_gravity = m_coord.Y();
			jumping = true;
			falling = true;
			m_time = m_ratio_y;
			gravity(m_time);
			}
		return true;
	}

	@Override
	public boolean pop(Direction dir) { // sauter
		// TODO Auto-generated method stub
		if(!m_model.m_room.isBlocked(m_coord.X(), m_coord.Y()+ m_height)){
			m_State = JUMPING;
			y_gravity = m_coord.Y();
			poping = true;
			m_time = 0;
		}
		return true;
	}

	private void gravity(long t) {
		// TODO Auto-generated method stub
		if(!m_model.m_room.isBlocked(m_coord.X(), m_coord.Y()) || falling){
			m_State = JUMPING;
			double C;
			if (jumping) {
				C = ACCELERATION_JUMP;
			} else if (poping) {
				C = ACCELERATION_POP;
			} else {
				C = 0;
			}
			int newY = (int) ((0.5 * G * Math.pow(t, 2) * 0.0005 - C * t)) + y_gravity;
			m_coord.setY(newY);
		}
	}

	@Override
	public boolean egg(Direction dir) { // tir
		// TODO Auto-generated method stub
		long now = System.currentTimeMillis();
		
		int m_x = m_coord.X();
		int m_y = m_coord.Y();

		if (now - m_shot_time > 1000) {

			double angle = Math.acos((m_model.m_mouseCoord.X() - m_x) / (Math.sqrt(
					Math.pow((m_model.m_mouseCoord.X() - m_x), 2) + Math.pow((m_model.m_mouseCoord.Y() - m_y), 2))));
			m_arrows.add(new Arrow(m_x, m_y, angle, this));

			return true;
		}

		return false;
	}

	public void setPressed(int keyCode, boolean pressed) {
		if (keyCode == 113) {
			qPressed = pressed;
			if(pressed == true && m_State!=WALKING) {
				m_image_index = 8;
			} else {
				m_State = IDLE;
			}
		}
		if (keyCode == 122) {
			zPressed = pressed;
			if(pressed == true && m_State!=JUMPING) {
				m_image_index = 15;
			} else {
					m_State = IDLE;
			}
		}
		if (keyCode == 100) {
			dPressed = pressed;
			if(pressed == true && m_State!=WALKING) {
				m_image_index = 8;
			} else {
				m_State = IDLE;
			}
		}
		if (keyCode == 32)
			espPressed = pressed;
		if (keyCode == 65)
			aPressed = pressed;
		if (keyCode == 69)
			ePressed = pressed;
	}

	@Override
	public boolean key(int keyCode) {
		if (keyCode == 113) {
			if (speed_x == 0)
				dt_x = 0;
			return qPressed;
		}
		if (keyCode == 122) {
			return zPressed;
		}
		if (keyCode == 100) {
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
		m_ratio_x = elapsed;
		m_ratio_y = elapsed;
		if (!m_model.m_room.isBlocked(m_coord.X(), m_coord.Y())) {
			if (!falling) {
				y_gravity = m_coord.Y();
				m_time = 0;
			} else {
				m_time += elapsed;
			}
			falling = true;
			if (m_time >= 10)
				gravity(m_time);
		} else if(falling) {
			m_coord.setY(m_model.m_room.blockTop(m_coord.X(), m_coord.Y()));
			falling = false;
		} else {
			m_State = IDLE;
			jumping = false;
			falling = false;
		}
		
		m_imageElapsed += elapsed;
		if (m_imageElapsed > 200) {
			m_imageElapsed = 0;

			switch (m_State) {
			case IDLE:
				m_image_index = (m_image_index + 1) % 4;
				break;
			case WALKING:
				m_image_index = (m_image_index - 8 + 1) % 6 + 8;
				System.out.println(m_image_index);
				break;
			case JUMPING:
				m_image_index = (m_image_index - 15 + 1) % 9 + 15;
				if(m_image_index == 18)
					m_image_index = 22;
				break;
			default:
				m_image_index = (m_image_index + 1) % 4;
				break;
			}
		}
		m_automaton.step(this);
		
		int m_x = m_coord.X();
		int m_y = m_coord.Y();
		
		x_hitBox = new int[] { m_x - m_width, m_x - m_width, m_x + m_width, m_x + m_width};
		y_hitBox = new int[] { m_y, m_y - m_height, m_y - m_height, m_y};

	}

	public void paint(Graphics g) {
		if (bI != null) {
			int m_x = m_coord.X();
			int m_y = m_coord.Y();
			
			BufferedImage img = bI[m_image_index];
			int w = DIMENSION * img.getWidth();
			int h = DIMENSION * img.getHeight();
			if (m_direction.toString().equals("E")) {
				g.drawImage(img, m_x - (2*img.getWidth()+5*DIMENSION), m_y-h, w, h, null);
			} else {
				g.drawImage(img, m_x + (2*img.getWidth()+5*DIMENSION), m_y-h, -w, h, null);
			}
			g.setColor(Color.blue);
			g.drawPolygon(x_hitBox, y_hitBox, x_hitBox.length);
		}
	}

}
