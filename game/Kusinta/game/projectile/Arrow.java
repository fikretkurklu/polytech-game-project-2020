package projectile;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import environnement.Element;
import player.Player;

public class Arrow extends Projectile {

	public static final int SIZE = (int) (1.5 * Element.SIZE);
	static final int SPEED = 9;
	int moving;
	int DIMENSION;

	int m_height;
	int m_width;
	
	int[] x_hitBox, y_hitBox;

	public Arrow(Automaton arrowAutomaton, int x, int y, double angle, Player player, Direction direction)
			throws Exception {
		super(arrowAutomaton, x, y, angle, player, player.getModel(), direction);
		loadImage("resources/Player/spriteArrow.png");

		DIMENSION = SIZE / (image.getHeight(null));

		float ratio = (float) (image.getWidth(null) * 4) / (float) (5 * image.getHeight(null));

		m_height = DIMENSION * image.getHeight(null);
		m_width = (int) (ratio * image.getWidth(null));
		
		hitBox = new Rectangle((int)(m_coord.X()-(m_width/2)*Math.cos(m_angle)),(int)( m_coord.Y()-(m_height/2)*Math.sin(m_angle)), m_width, m_height);
		x_hitBox = new int[] {};

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
		if (moving == 0) {
			if (m_direction.toString().equals("E")) {
				m_coord.setX((int) (m_coord.X() + SPEED * Math.cos(m_angle)));
				m_coord.setY((int) (m_coord.Y() - SPEED * Math.sin(m_angle)));
			} else {
				m_coord.setX((int) (m_coord.X() - SPEED * Math.cos(m_angle)));
				m_coord.setY((int) (m_coord.Y() - SPEED * Math.sin(m_angle)));
			}
		}
		moving = (moving + 1) % 3;

		return true;
	}

	public void paint(Graphics g) {
		long now = System.currentTimeMillis();
		((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha()));

		if (image != null) {
			int w = DIMENSION * m_width;
			int h = m_height;
			Graphics2D g2D = (Graphics2D) g;
			if (m_direction.toString().equals("E")) {
				g2D.rotate(-m_angle, m_coord.X(), m_coord.Y());
				g2D.drawImage(image, m_coord.X() + (w / 2), m_coord.Y() - h / 2, w, h, null);
				g2D.rotate(m_angle, m_coord.X(), m_coord.Y());
			} else {
				g2D.rotate(m_angle, m_coord.X(), m_coord.Y());
				g2D.drawImage(image, m_coord.X() + (w / 2), m_coord.Y() - h / 2, -w, h, null);
				g2D.rotate(-m_angle, m_coord.X(), m_coord.Y());
			}
		}
		g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);

		if (now - getDeadTime() > 1000 && getState() == 2) {
			setAlpha(this.getAlpha() * 0.95f);
		}
	}

	@Override
	public boolean cell(Direction dir, Category cat) {
		boolean c = !((m_model.m_room.isBlocked(m_coord.X(), m_coord.Y()))
				|| (m_model.m_room.isBlocked((int) (m_coord.X() + m_width * Math.cos(m_angle)),
						(int) (m_coord.Y() - m_height * Math.sin(m_angle)))));
		if (m_State == HIT_STATE) {
			return !c;
		}
		if (!c) {
			m_State = HIT_STATE;
		}
		return c;
	}

}