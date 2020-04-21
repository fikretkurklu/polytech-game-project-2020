package projectile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import player.Player;

public class Arrow {

	static final int SPEED = 10;
	static final double G = 9.81;
	static final int OK_STATE = 1;
	static final int HIT_STATE = 2;

	int m_x, m_y, old_m_x, old_m_y;
	double m_angle;
	ArrowTimerListener m_atl;
	int m_state;
	int m_time;
	Player m_shooter;

	public Arrow(int x, int y, double angle, Player player) {
		old_m_x = x;
		old_m_y = y;
		m_x = x;
		m_y = y;
		m_angle = angle;

		m_time = 0;
		
		m_atl = new ArrowTimerListener();
		
		m_shooter = player;
	}

	public void move() {
		m_x = (int) (10 * Math.cos(m_angle) * m_time + old_m_x);
		m_y = (int) (-.5 * G * m_time * m_time + 10 * Math.sin(m_angle) * m_time + old_m_y);
	}
	
	public void explode() {
		m_shooter.m_arrows.remove(this);
	}

	private class ArrowTimerListener implements ActionListener {

		private int tick_time = 10;
		private long m_last;
		Timer m_timer;

		public ArrowTimerListener() {
			m_timer = new Timer(tick_time, this);
			m_timer.start();
		}

		public long getTickTime() {
			return tick_time;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			long now = System.currentTimeMillis();
			m_time += tick_time;
			move();
			m_timer.restart();
			m_last = now;
		}

	}

}
