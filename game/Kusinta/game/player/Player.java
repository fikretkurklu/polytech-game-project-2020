package player;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import automaton.*;
import game.Controller;
import game.Model;
import projectile.Arrow;
import environnement.Element;

public class Player extends Character {

	public static final int SIZE = (int) (1.5 * Element.SIZE);

	double G = 9.81;
	double ACCELERATION_JUMP = 1.8;
	int m_slowness;

	int SPEED_WALK = 1;
	
	enum State {WALKING, JUMPING, IDLE};

	int DIMENSION;

	boolean qPressed, zPressed, dPressed, espPressed, aPressed, ePressed, vPressed;
	boolean falling, jumping;

	int m_width, m_height;

	int y_gravity;
	int dt_y;
	double speed_y;

	long m_ratio_x, m_ratio_y;

	long m_time, m_shot_time;

	int[] x_hitBox, y_hitBox;

	long m_imageElapsed;

	State m_State;

	public Player(Automaton automaton, int x, int y, Direction dir, Model model) throws Exception{
		super(automaton, x, y, dir, model, 100, 100, 1000, 0, 0);
		bI = m_model.loadSprite("resources/Player/spritePlayer.png", 16, 7);

		DIMENSION = SIZE / (bI[0].getHeight());
		float ratio = (float) (bI[0].getWidth() * 2) / (float) (5 * bI[0].getHeight());
		// System.out.println("ratio = " +ratio);

		m_height = DIMENSION * bI[0].getHeight();
		m_width = (int) (m_height * ratio);

		int m_x = m_coord.X();
		int m_y = m_coord.Y();

		x_hitBox = new int[] { m_x - (m_width / 2 + 3 * DIMENSION), m_x - (m_width / 2 + 3 * DIMENSION),
				m_x + (m_width / 2 + 3 * DIMENSION), m_x + (m_width / 2 + 3 * DIMENSION) };
		y_hitBox = new int[] { m_y, m_y - m_height + 3 * DIMENSION, m_y - m_height + 3 * DIMENSION, m_y };
		m_shot_time = System.currentTimeMillis();

		m_imageElapsed = 0;
		m_State = State.IDLE;

		qPressed = false;
		zPressed = false;
		dPressed = false;
		espPressed = false;
		aPressed = false;
		ePressed = false;
		vPressed = false;

		m_slowness = 10;
	}

	@Override
	public boolean move(Direction dir) { // bouger
		int random = (int) (Math.random() * 10);
		System.out.println("random = " + random);
		if (random < m_slowness) {
			if (jumping || falling) {
				m_State = State.JUMPING;
			} else {
				m_State = State.WALKING;
			}
			int m_x = m_coord.X();
			int m_y = m_coord.Y();

			if (!dir.toString().equals(m_direction.toString())) {
				turn(dir);
			}
			if (dir.toString().equals("E")) {
				if (!checkBlock(x_hitBox[2], m_y - 1) && !checkBlock(x_hitBox[2], m_y - m_height)
						&& !checkBlock(x_hitBox[2], m_y - m_height / 2)) {
					m_x += SPEED_WALK;
					m_coord.setX(m_x);
				}
			} else if (dir.toString().equals("W")) {
				if (!checkBlock(x_hitBox[0], m_y - 1) && !checkBlock(x_hitBox[0], m_y - m_height)
						&& !checkBlock(x_hitBox[0], m_y - m_height / 2)) {
					m_x -= SPEED_WALK;
					m_coord.setX(m_x);
				}
			}
		}
		return true;
	}

	@Override
	public boolean jump(Direction dir) { // sauter
		if (!checkBlock(m_coord.X(), m_coord.Y() - m_height) && !falling) {

			m_State = State.JUMPING;
			y_gravity = m_coord.Y();
			jumping = true;
			falling = true;
			m_time = m_ratio_y;
			gravity(m_time);
		}
		return true;
	}

	@Override
	public boolean pop(Direction dir) { // sauter moins haut
		// m_model.m_room.setupVillageMode();
		System.out.println("setupVillageMode");
			return true;
	}

	private void gravity(long t) {
		if (!checkBlock(m_coord.X(), m_coord.Y()) && !checkBlock(x_hitBox[2] - 1, m_coord.Y())
				&& !checkBlock(x_hitBox[0] - 2, m_coord.Y()) || falling) {
			m_State = State.JUMPING;
			double C;
			if (jumping) {
				C = ACCELERATION_JUMP;
			} else {
				C = 0;
			}

			if (checkBlock(m_coord.X(), m_coord.Y() - m_height) || checkBlock(x_hitBox[2] - 2, m_coord.Y() - m_height)
					|| checkBlock(x_hitBox[0] + 2, m_coord.Y() - m_height)) {
				C = 0;
				m_coord.setY(m_model.m_room.blockBot(m_coord.X(), m_coord.Y() - m_height) + m_height);
				y_gravity = m_coord.Y();
				jumping = false;
				t = (long) 0.1;
				m_time = t;
				System.out.println(" ratio_y = " + m_ratio_y);
			}

			int newY = (int) ((0.5 * G * Math.pow(t, 2) * 0.0005 - C * t)) + y_gravity;
			m_coord.setY(newY);
		} else {
			m_time = 0;
		}
	}

	@Override
	public boolean egg(Direction dir) { // tir
		long now = System.currentTimeMillis();

		int m_x = m_coord.X();
		int m_y = m_coord.Y() - m_height / 2;

		if (now - m_shot_time > m_attackSpeed) {

			double angle = Math.acos((m_model.m_mouseCoord.X() - m_x) / (Math.sqrt(Math.pow((m_model.m_mouseCoord.X() - m_x), 2) + Math.pow((m_model.m_mouseCoord.Y() - m_y), 2))));
			try {
				m_projectiles.add(new Arrow(m_model.arrowAutomaton, m_x, m_y, angle, this));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			m_shot_time = now;

			return true;
		}

		return false;
	}

	public void setPressed(int keyCode, boolean pressed) {
		if (keyCode == Controller.K_Q) {
			qPressed = pressed;
			if (!(jumping || falling)) {
				if (pressed == true && m_State != State.WALKING) {
					m_image_index = 8;
				} else {
					m_State = State.IDLE;
				}
			}
		}
		if (keyCode == Controller.K_Z) {
			zPressed = pressed;
			if (pressed == true && m_State != State.JUMPING) {
				m_image_index = 15;
			} else {
				m_State = State.IDLE;
			}
		}
		if (keyCode == Controller.K_D) {
			dPressed = pressed;
			if (!(jumping || falling)) {
				if (pressed == true && m_State != State.WALKING) {
					m_image_index = 8;
				} else {
					m_State = State.IDLE;
				}
			}
		}
		if (keyCode == Controller.K_SPACE)
			espPressed = pressed;
		if (keyCode == Controller.K_A)
			aPressed = pressed;
		if (keyCode == Controller.K_E)
			ePressed = pressed;
		if (keyCode == Controller.K_V)
			vPressed = pressed;
	}

	@Override
	public boolean key(int keyCode) {
		if (keyCode == Controller.K_Q) {
			return qPressed;
		}
		if (keyCode == Controller.K_Z) {
			return zPressed;
		}
		if (keyCode == Controller.K_D) {
			return dPressed;
		}
		if (keyCode == Controller.K_SPACE)
			return espPressed;
		if (keyCode == Controller.K_A)
			return aPressed;
		if (keyCode == Controller.K_E)
			return ePressed;
		if (keyCode == Controller.K_V)
			return vPressed;
		return false;
	}

	public void tick(long elapsed) {
		m_ratio_x = elapsed;
		m_ratio_y = elapsed;
		if (!checkBlock(m_coord.X(), m_coord.Y()) && !checkBlock(x_hitBox[2] - 1, m_coord.Y())
				&& !checkBlock(x_hitBox[0] + 1, m_coord.Y())) {
			if (!falling) {
				y_gravity = m_coord.Y();
				m_time = 0;
			} else {
				m_time += elapsed;
			}
			falling = true;
			if (m_time >= 10)
				gravity(m_time);
		} else if (falling) {
			m_coord.setY(m_model.m_room.blockTop(m_coord.X(), m_coord.Y()));
			falling = false;
			jumping = false;
			m_State = State.IDLE;
		} else {
			jumping = false;
			falling = false;
		}

		m_imageElapsed += elapsed;
		if (m_imageElapsed > 200) {
			m_imageElapsed = 0;

			if (falling || jumping)
				m_State = State.JUMPING;

			last_image_index = m_image_index;

			switch (m_State) {
			case IDLE:
				m_image_index = (m_image_index + 1) % 4;
				break;
			case WALKING:
				m_image_index = (m_image_index - 8 + 1) % 6 + 8;
				if (m_image_index < 8)
					m_image_index = 8;
				break;
			case JUMPING:
				m_image_index = (m_image_index - 15 + 1) % 9 + 15;
				if (falling && !jumping)
					m_image_index = 23;
				if (m_image_index == 18)
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

		x_hitBox = new int[] { m_x - (m_width / 2 + 3 * DIMENSION), m_x - (m_width / 2 + 3 * DIMENSION),
				m_x + (m_width / 2 + 3 * DIMENSION), m_x + (m_width / 2 + 3 * DIMENSION) };
		y_hitBox = new int[] { m_y, m_y - m_height + 3 * DIMENSION, m_y - m_height + 3 * DIMENSION, m_y };

		
		for(int i = 0; i < m_projectiles.size(); i++) {
			m_projectiles.get(i).tick(elapsed);
		}
	}

	public void paint(Graphics g) {
		if (bI != null) {
			int m_x = m_coord.X();
			int m_y = m_coord.Y();

			checkSprite();

			BufferedImage img = bI[m_image_index];
			int w = DIMENSION * m_width;
			int h = m_height;
			if (m_direction.toString().equals("E")) {
				g.drawImage(img, m_x - (w / 2), m_y - h, w, h, null);
			} else {
				g.drawImage(img, m_x + (w / 2), m_y - h, -w, h, null);
			}
			g.setColor(Color.blue);
			g.drawPolygon(x_hitBox, y_hitBox, x_hitBox.length);
		}
		
		for(int i = 0; i < m_projectiles.size(); i++) {
			((Arrow) m_projectiles.get(i)).paint(g);
		}
	}

	public boolean checkBlock(int x, int y) {
		return m_model.m_room.isBlocked(x, y);
	}

	public void checkSprite() {
		if (m_State == State.WALKING && (m_image_index < 8 || m_image_index > 14)) {
			m_image_index = (last_image_index - 8 + 1) % 6 + 8;
		}
		if (m_State == State.JUMPING && (m_image_index < 15 || m_image_index > 24)) {
			m_image_index = (last_image_index - 15 + 1) % 9 + 15;
			if (m_image_index >= 18 && m_image_index < 22)
				m_image_index = 22;
		}
		if (m_State == State.IDLE && (m_image_index > 4)) {
			m_image_index = (last_image_index + 1) % 4;
		}
	}
	
	public void setSlowness(int s) {
		if(s<6) {
			m_slowness = 6;
		} else {
			m_slowness = s;
		}
	}
	
	public void setGravity(int g) {
		G = g;
	}

}
