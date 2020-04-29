package projectile;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import environnement.Element;
import game.Coord;
import player.Player;

public class Arrow extends Projectile {

	public static final int SIZE = (int) (1.5 * Element.SIZE);
	static final int SPEED = 12;
	int moving;

	int m_height;
	int m_width;

	public Arrow(Image ArrowImage, Automaton arrowAutomaton, int x, int y, double angle, Player player, Direction direction)
			throws Exception {
		super(ArrowImage, arrowAutomaton, x, y, angle, player, player.getModel(), direction);

		m_height = image.getHeight(null);
		m_width = image.getWidth(null);

		if (m_direction.toString().equals("E")) {
			hitBox = new Coord((int) (m_coord.X() + (m_width / 2) * Math.cos(m_angle)),
					(int) (m_coord.Y() - (m_width / 2) * Math.sin(m_angle)));
		} else {
			hitBox = new Coord((int) (m_coord.X() - (m_width / 2) * Math.cos(m_angle)),
					(int) (m_coord.Y() - (m_width / 2) * Math.sin(m_angle)));
		}

		m_dead_time = 0;

		moving = 0;
	}

	@Override
	public boolean explode() {
		if (m_dead_time == 0) {
			m_dead_time = System.currentTimeMillis();
		}
		return true;
	}

	@Override
	public boolean move(Direction dir) {
		int tmpX = m_coord.X();
		int tmpY = m_coord.Y();

		if (moving == 0) {
			if (m_direction.toString().equals("E")) {
				m_coord.setX((int) (m_coord.X() + SPEED * Math.cos(m_angle)));
				m_coord.setY((int) (m_coord.Y() - SPEED * Math.sin(m_angle)));
			} else {
				m_coord.setX((int) (m_coord.X() - SPEED * Math.cos(m_angle)));
				m_coord.setY((int) (m_coord.Y() - SPEED * Math.sin(m_angle)));
			}
		}
		moving = (moving + 1) % 4;

		hitBox.translate(m_coord.X() - tmpX, m_coord.Y() - tmpY);

		return true;
	}

	public void paint(Graphics g) {
		long now = System.currentTimeMillis();
		Graphics2D bg = (Graphics2D) g.create(m_coord.X() - m_width / 2, m_coord.Y() - m_height/2, m_width* 2, m_height * 2);
		bg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha()));

		if (image != null) {
			int w = m_width;
			int h = m_height;
			if (m_direction.toString().equals("E")) {
				bg.rotate(-m_angle, m_width / 2, m_height/2);
				bg.drawImage(image, 0, 0, w, h, null);
			} else {
				bg.rotate(m_angle, m_width / 2, m_height / 2);
				bg.drawImage(image, m_width, 0, -w, h, null);
			}
		}
		bg.dispose();
		if (now - getDeadTime() > 1000 && getState().equals(State.HIT_STATE)) {
			setAlpha(this.getAlpha() * 0.95f);
		}

	}

	@Override
	public boolean cell(Direction dir, Category cat) {
		boolean c = !((m_model.m_room.isBlocked(m_coord.X(), m_coord.Y()))
				|| (m_model.m_room.isBlocked(hitBox.X(), hitBox.Y())));
		if (m_State.equals(State.HIT_STATE)) {
			return !c;
		}
		if (!c) {
			m_State = State.HIT_STATE;
		}
		return c;
	}

}