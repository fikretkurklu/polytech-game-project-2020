package projectile;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import environnement.Element;
import game.Coord;
import opponent.Opponent;
import player.Player;
import player.Character;

public class Arrow extends Projectile {

	public static final int SIZE = (int) (1.5 * Element.SIZE);
	int DIMENSION;

	int m_height;
	int m_width;

	public Arrow(Automaton arrowAutomaton, int x, int y, double angle, Character shooter, Direction direction)
			throws Exception {
		super(arrowAutomaton, x, y, angle, shooter, direction);

		image = m_shooter.getProjectileImage();

		DIMENSION = SIZE / (image.getHeight(null));

		m_height = image.getHeight(null);
		m_width = image.getWidth(null);

		if (m_direction == Direction.E) {
			hitBox = new Rectangle((int) (m_coord.X() + (m_width / 2) * Math.cos(m_angle) * 1.5),
					(int) (m_coord.Y() - (m_width / 2) * Math.sin(m_angle)));
		} else {
			hitBox = new Rectangle((int)((m_coord.X() - (m_width / 2) * Math.cos(m_angle) * 1)),
					(int)((m_coord.Y() - (m_width / 2) * Math.sin(m_angle))));
		}

	}

	public void paint(Graphics g) {
		long now = System.currentTimeMillis();
		Graphics2D bg = (Graphics2D) g.create(m_coord.X() - m_width / 2, m_coord.Y() - m_height / 2, m_width * 2,
				m_height * 2);
		bg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha()));

		if (image != null) {
			int w = (int)(m_width * 1.5);
			int h = (int)(m_height/1.5);
			if (m_direction == Direction.E) {
				bg.rotate(-m_angle, m_width / 2, m_height / 2);
				bg.drawImage(image, -10, h/4, w, h, null);
			} else {
				bg.rotate(m_angle, m_width / 2, m_height / 2);
				bg.drawImage(image, w, h/4, -w, h, null);
			}
		}
		bg.dispose();
		if (now - getDeadTime() > 1000 && getState().equals(State.HIT_STATE)) {
			setAlpha(this.getAlpha() * 0.7f);
		}
	}

	public float getAlpha() {
		return m_alpha;
	}

	public void setAlpha(float alpha) {
		m_alpha = alpha;
		if (alpha <= 0.05) {
			((Player) m_shooter).removeProjectile(this);
		}
	}

	public boolean cell(Direction dir, Category cat) {
		boolean b = super.cell(dir, cat);
		if (b) {
			if (cat == Category.A) {
				collidingWith.loseLife(m_strength);
				((Opponent) collidingWith).setCollidedWith(this);
			}
		}
		
		return b;
	}

	public Coord getCoord() {
		return m_coord;
	}

}