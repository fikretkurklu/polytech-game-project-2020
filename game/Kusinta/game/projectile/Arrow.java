package projectile;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import environnement.Element;
import game.Coord;
import opponent.Opponent;
import player.Player;

public class Arrow extends Projectile {

	public static final int SIZE = (int) (1.5 * Element.SIZE);
	int DIMENSION;

	int m_height;
	int m_width;

	public Arrow(Automaton arrowAutomaton, int x, int y, double angle, Player player, Direction direction)
			throws Exception {
		super(arrowAutomaton, x, y, angle, player, player.getModel(), direction);

		image = m_shooter.getProjectileImage();

		DIMENSION = SIZE / (image.getHeight(null));

		m_height = image.getHeight(null);
		m_width = image.getWidth(null);

		if (m_direction.toString().equals("E")) {
			hitBox = new Coord((int) (m_coord.X() + (m_width / 2) * Math.cos(m_angle) * 1.5),
					(int) (m_coord.Y() - (m_width / 2) * Math.sin(m_angle)));
		} else {
			hitBox = new Coord((int)((m_coord.X() - (m_width / 2) * Math.cos(m_angle) * 1.5)),
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
//			g.setColor(Color.blue);
//			g.fillRect(hitBox.X() - 5, hitBox.Y() - 5, 10, 10);
			if (m_direction.toString().equals("E")) {
				bg.rotate(-m_angle, m_width / 2, m_height / 2);
				bg.drawImage(image, 0, h/4, w, h, null);
			} else {
				bg.rotate(m_angle, m_width / 2, m_height / 2);
				bg.drawImage(image, w, h/4, -w, h, null);
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
		if (alpha <= 0.05) {
			((Player) m_shooter).removeProjectile(this);
		}
	}

	public boolean cell(Direction dir, Category cat) {
		boolean c;
		if (cat.toString().equals("_")) {
			c = ((m_model.m_room.isBlocked(m_coord.X(), m_coord.Y()))
					|| (m_model.m_room.isBlocked(hitBox.X(), hitBox.Y())));
			if (c) {
				m_State = State.HIT_STATE;
				return true;
			}

			LinkedList<Opponent> opponents = m_model.getOpponent();
			for (Opponent op : opponents) {
				c = op.getHitBox().contains(hitBox.X(), hitBox.Y())
						|| op.getHitBox().contains(m_coord.X(), m_coord.Y());
				if (c) {
					m_State = State.HIT_STATE;
					this.setCollidingWith(op);
					((Opponent) collidingWith).setCollidedWith(this);
					return true;
				}
			}
		} else {
			c = !((m_model.m_room.isBlocked(m_coord.X(), m_coord.Y()))
					|| (m_model.m_room.isBlocked(hitBox.X(), hitBox.Y())));
			if (m_State == State.HIT_STATE) {
				return !c;
			}
			if (!c) {
				m_State = State.HIT_STATE;
			}
			return c;
		}
		return false;
	}

	public Coord getCoord() {
		return m_coord;
	}

}