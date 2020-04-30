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

		image = m_shooter.getProjectileImage();

		DIMENSION = SIZE / (image.getHeight(null));

		float ratio = (float) (image.getWidth(null) * 4) / (float) (5 * image.getHeight(null));

		m_height =  image.getHeight(null);
		m_width =  image.getWidth(null);

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
			setAlpha(this.getAlpha() * 0.7f);
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