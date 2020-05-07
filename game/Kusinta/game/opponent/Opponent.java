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

	protected static final int SIZE = (int) (1.5 * Element.SIZE);

	protected Arrow collidedWith;
	
	int SPEED_WALK_TICK = 4;

	public Opponent(Automaton automaton, Coord C, Direction dir, Model model, int maxLife, int life,
			int attackSpeed, int resistance, int strength) throws IOException {
		super(automaton, C, dir, model, maxLife, life, attackSpeed, resistance, strength);

		m_key = true;

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

	public void setKey(boolean k) {
		m_key = k;
	}

	public void dropKey() {
		if (m_key != false) {
			try {
				if (m_key == true) {
					getM_model().setKey(new NormalKey(getM_model().keyDropAutomaton, m_coord.X(), m_coord.Y(), getM_model()));
				} else if (m_bossKey == true) {
					getM_model().setBossKey(new BossKey(getM_model().keyDropAutomaton, m_coord.X(), m_coord.Y(), getM_model()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			setKey(false);
		}
	}
	
	public boolean move(Direction dir) {
		if (gotpower()) {
			
			int m_x = m_coord.X();
			int m_y = m_coord.Y();
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
