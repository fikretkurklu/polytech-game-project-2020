package projectile;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import automaton.Automaton;
import automaton.Direction;
import environnement.Element;
import game.Coord;
import player.Player;

public class Arrow extends Projectile {

	public static final int SIZE = (int) (1.5 * Element.SIZE);
	static final int SPEED = 9;
	int DIMENSION;

	int m_height;
	int m_width;

	public Arrow(Automaton arrowAutomaton, int x, int y, double angle, Player player, Direction direction)
			throws Exception {
		super(arrowAutomaton, x, y, angle, player, player.getModel(), direction);

		image = loadImage("resources/Player/spriteArrow.png");

		DIMENSION = SIZE / (image.getHeight(null));

		float ratio = (float) (image.getWidth(null) * 4) / (float) (5 * image.getHeight(null));

		m_height = DIMENSION * image.getHeight(null);
		m_width = (int) (ratio * image.getWidth(null));

		if (m_direction.toString().equals("E")) {
			hitBox = new Coord((int) (m_coord.X() + (m_width / 2) * Math.cos(m_angle)),
					(int) (m_coord.Y() - (m_width / 2) * Math.sin(m_angle)));
		} else {
			hitBox = new Coord((int) (m_coord.X() - (m_width / 2) * Math.cos(m_angle)),
					(int) (m_coord.Y() - (m_width / 2) * Math.sin(m_angle)));
		}

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
				g2D.drawImage(image, m_coord.X() - (w / 2), m_coord.Y() - h / 2, w, h, null);
				g2D.rotate(m_angle, m_coord.X(), m_coord.Y());
			} else {
				g2D.rotate(m_angle, m_coord.X(), m_coord.Y());
				g2D.drawImage(image, m_coord.X() + (w / 2), m_coord.Y() - h / 2, -w, h, null);
				g2D.rotate(-m_angle, m_coord.X(), m_coord.Y());
			}
		}

		if (now - getDeadTime() > 1000 && getState() == 2) {
			setAlpha(this.getAlpha() * 0.95f);
		}
		
		((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

	}
	
	public float getAlpha() {
		return m_alpha;
	}
	
	public void setAlpha(float alpha) {
		m_alpha = alpha;
		if(alpha <= 0.05) {
			((Player)m_shooter).removeProjectile(this);
		}
	}
	
	public void tick(long elapsed) {
		m_automaton.step(this);
	}

}