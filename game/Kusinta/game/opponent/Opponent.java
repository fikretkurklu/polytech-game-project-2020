package opponent;
import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;

import automaton.Automaton;
import automaton.Direction;
import entityFactory.Factory.Type;
import environnement.Element;
import game.Coord;
import game.Game;
import game.Model;
import player.Character;
import projectile.Arrow;

public abstract class Opponent extends Character {

	protected static final int SIZE = (int) (1.5 * Element.SIZE);

	protected Arrow collidedWith;
	
	public Opponent(Automaton automaton, Coord C, Direction dir, Model model, int maxLife, int life,
			int attackSpeed, int resistance, int strength, Image[] bImages, HashMap<Action, int[]> indiceAction) throws IOException {
		super(automaton, C, dir, model, maxLife, life, attackSpeed, resistance, strength, bImages, indiceAction);

		m_key = true;

	}

	@Override
	public void tick(long elapsed) {
		m_moveElapsed += elapsed;
		if (m_moveElapsed > m_stepTick) {
			m_moveElapsed -= m_stepTick;
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
					NormalKey k = (NormalKey) Game.m_factory.newEntity(Type.NormalKey, null, m_coord, m_model, 0	, null);
					m_model.setKey(k);
				} else if (m_bossKey == true) {
					BossKey k = (BossKey) Game.m_factory.newEntity(Type.BossKey, null, m_coord, m_model, 0, null);
					m_model.setBossKey(k);
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
