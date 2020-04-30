package opponent;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import automaton.Automaton;
import automaton.Direction;
import environnement.Element;
import game.Model;

public class WalkingOpponent extends Opponent {

	public static final int SIZE = (int) (1.5 * Element.SIZE);
	public static final int WALKING_SPEED = 1;

	protected enum CurrentState {isAttacking, isMoving, isDead};
	CurrentState m_state;
	
	long m_shotTime;

	Image[] deathSprite;
	Image[] walkingSprite;
	Image[] attackingSprite;
	int m_image_index, m_imageElapsed;
	
	Direction m_dir;
	
	public WalkingOpponent(Automaton automaton, int x, int y, Direction dir, Model model, int maxLife, int life,
			int attackSpeed, int resistance, int strength) throws IOException {
		super(automaton, x, y, dir, model, maxLife, life, attackSpeed, resistance, strength);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paint(Graphics gp) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean move(Direction dir) {
		if (!(m_state== CurrentState.isDead)) {
			int m_x = m_coord.X();

			if (!(m_state == CurrentState.isMoving)) {
				m_image_index = 0;
			}
			m_state = CurrentState.isMoving;

			if (m_direction.toString().equals("E")) {
				m_x += WALKING_SPEED;
				hitBox.translate(m_x - m_coord.X(), 0);
				m_coord.setX(m_x);
			} else {
				m_x -= WALKING_SPEED;
				hitBox.translate(-(m_coord.X() - m_x), 0);
				m_coord.setX(m_x);
			}
		}
		return true;
	}
	
	public boolean explode() {
		m_state = CurrentState.isDead;
		return true;
	}
	
	
}
