package hud;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import automaton.Direction;
import entityFactory.ImageLoader;
import environnement.Element;
import game.Coord;
import game.Model;
import player.Player;
import room.Door;

public class Compass {

	public static final int SIZE = (int) (Element.SIZE);
	public Player m_player;
	int m_height;
	int m_width;
	double m_angle;
	Coord m_coord;
	Image img;
	Direction m_direction;
	long updateTick = 500;
	long updateElapsed = 0;
	Model m_model;

	public Compass(Coord c, double angle, Player player, Model m, Direction dir) throws Exception {

		m_height = SIZE;
		m_width = SIZE;
		m_coord = c;
		m_angle = angle;
		m_direction = dir;
		m_model = m;
		m_player = player;
		img = ImageLoader.loadImage("resources/Room/arrow.png", SIZE, SIZE);
	}

	public void paint(Graphics g) {
		if (m_player.getKey() && m_model.m_room.getDoor() != null) {
			Graphics2D bg = (Graphics2D) g.create(m_coord.X() - m_width / 2, m_coord.Y() - m_height / 2, m_width * 2,
					m_height * 2);

			if (m_direction == Direction.E) {
				bg.rotate(-m_angle, m_width / 2, m_height / 2);
				bg.drawImage(img, 0, 0, null);

			} else {
				bg.rotate(m_angle, m_width / 2, m_height / 2);
				bg.drawImage(img, m_width,0 , -m_width, m_height, null);
			}
			bg.dispose();
		}

	}

	public void tick(long elapsed) {
		updateElapsed += elapsed;
		if (updateElapsed > updateTick) {
			updateElapsed = 0;
			calculateAngle();
		}
	}

	public void calculateAngle() {
		Door d = m_model.m_room.getDoor();
		int m_x = m_player.getCoord().X();
		int m_y = m_player.getCoord().Y();
		double r;
		if (d != null) {
			int x = d.getCoord().X() - m_x;
			int y = m_y - d.getCoord().Y();

			if (d.getCoord().X() > m_x) {
				m_direction = Direction.E;
			} else {
				m_direction = Direction.W;
			}

			r = (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
			m_angle = Math.asin((double) y / (double) r);
		}
	}
}