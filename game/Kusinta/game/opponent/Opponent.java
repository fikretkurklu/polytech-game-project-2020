package opponent;
import java.io.IOException;

import automaton.Automaton;
import automaton.Direction;
import environnement.Element;
import game.Coord;
import game.Model;
import player.Character;
import projectile.Arrow;

public abstract class Opponent extends Character {

	public static final int SIZE = (int) (1.5 * Element.SIZE);

	protected boolean moving;

	protected Arrow collidedWith;

	Key m_key;
	
	int SPEED_WALK_TICK = 4;
	long m_moveElapsed;

	public Opponent(Automaton automaton, Coord C, Direction dir, Model model, int maxLife, int life,
			int attackSpeed, int resistance, int strength) throws IOException {
		super(automaton, C, dir, model, maxLife, life, attackSpeed, resistance, strength);

		m_key = null;

		moving = false;
	}

	@Override
	public void tick(long elapsed) {
		m_moveElapsed += elapsed;
		if (m_moveElapsed > SPEED_WALK_TICK) {
			m_moveElapsed -= SPEED_WALK_TICK;
			m_automaton.step(this);
		}
		if (this instanceof WalkingOpponent) {
			super.tick(elapsed);
		}
	}

	public void setCollidedWith(Arrow a) {
		collidedWith = a;
	}

	public void setKey(Key k) {
		m_key = k;
	}

	public void dropKey() {
		if (m_key != null) {
			try {
				if (m_key instanceof NormalKey) {
					m_model.setKey(new NormalKey(m_model.keyDropAutomaton, m_coord.X(), m_coord.Y(), m_model));
				} else {
					m_model.setBossKey(new BossKey(m_model.keyDropAutomaton, m_coord.X(), m_coord.Y(), m_model));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			setKey(null);
		}
	}
	
	public boolean move(Direction dir) {
		if (gotpower()) {
			int m_x = m_coord.X();
			int m_y = m_coord.Y();

			if (!moving) {
				m_image_index = 0;
			}
			moving = true;

			if (!shooting)
				turn(dir);

			super.move(dir);

			if (collidedWith != null) {
				collidedWith.getCoord().translate(m_coord.X() - m_x, m_coord.Y() - m_y);
			}
		}
		return true;
	}

}
