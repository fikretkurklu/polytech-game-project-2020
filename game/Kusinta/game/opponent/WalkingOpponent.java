package opponent;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import automaton.Category;
import automaton.Automaton;
import automaton.Direction;
import environnement.Element;
import game.Model;

public class WalkingOpponent extends Opponent {

	public static final int SIZE = (int) (1.5 * Element.SIZE);
	public static final int WALKING_SPEED = 1;

	protected enum CurrentState {
		isAttacking, isMoving, isDead
	};

	CurrentState m_state;

	int m_strength;
	int passiveStrength;

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

	@Override
	public boolean cell(Direction dir, Category cat) {
		if (dir.toString().equals("E")) {
			if (cat.toString().equals("O")) {
				if (!m_model.m_room.isBlocked(m_coord.X() - hitBox.x / 2, m_coord.Y() + 1)
						|| m_model.m_room.isBlocked(m_coord.X() - hitBox.x / 2, m_coord.Y() - m_height / 2)
						|| m_model.m_room.isBlocked(m_coord.X() - hitBox.x / 2, m_coord.Y() - 1)
						|| m_model.m_room.isBlocked(m_coord.X() - hitBox.x / 2, m_coord.Y() - m_height + 1)) {
					return true;
				} else if (m_model.m_room.isBlocked(m_coord.X() - hitBox.x / 2, m_coord.Y() + 1)) {
					return false;
				} else {
					return true;
				}
			} else if (cat.toString().equals("A")) {
				if (m_model.getPlayer().getHitBox().contains(m_coord.X() - hitBox.x, m_coord.Y() - hitBox.y / 2)) {
					return true;
				} else {
					return false;
				}
			}
		} else if (dir.toString().equals("W")) {
			if (cat.toString().equals("O")) {
				if (!m_model.m_room.isBlocked(m_coord.X() + hitBox.x / 2, m_coord.Y() + 1)
						|| m_model.m_room.isBlocked(m_coord.X() + hitBox.x / 2, m_coord.Y() - m_height / 2)
						|| m_model.m_room.isBlocked(m_coord.X() + hitBox.x / 2, m_coord.Y() - 1)
						|| m_model.m_room.isBlocked(m_coord.X() + hitBox.x / 2, m_coord.Y() - m_height + 1)) {
					return true;
				} else if (m_model.m_room.isBlocked(m_coord.X() - hitBox.x / 2, m_coord.Y() + 1)) {
					return false;
				} else {
					return true;
				}
			} else if (cat.toString().equals("A")) {
				if (m_model.getPlayer().getHitBox().contains(m_coord.X() + hitBox.x, m_coord.Y() - hitBox.y / 2)) {
					return true;
				} else {
					return false;
				}
			}
		} else if (dir.toString().equals("H")) {
			if (cat.toString().equals("A")) {
				int xHB = m_model.getPlayer().getHitBox().x;
				int yHB = m_model.getPlayer().getHitBox().y;
				int widthHB = m_model.getPlayer().getHitBox().width;
				int heightHB = m_model.getPlayer().getHitBox().height;
				if (hitBox.contains(xHB, yHB) || hitBox.contains(xHB + widthHB / 2, yHB)
						|| hitBox.contains(xHB + widthHB, yHB) || hitBox.contains(xHB + widthHB, yHB + heightHB / 2)
						|| hitBox.contains(xHB + widthHB, yHB + heightHB)
						|| hitBox.contains(xHB + widthHB / 2, yHB + heightHB) || hitBox.contains(xHB, yHB + heightHB)
						|| hitBox.contains(xHB, yHB + heightHB / 2)) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;

	}

	@Override
	public boolean closest(Category cat, Direction dir) {
		int xPlayer = m_model.getPlayer().getCoord().X();
		int yPlayer = m_model.getPlayer().getCoord().Y();
		if (yPlayer > hitBox.y && yPlayer < hitBox.y + hitBox.height) {
			if (dir.toString().equals("E")) {
				if (xPlayer > hitBox.x + hitBox.width && xPlayer < hitBox.x + 3 * hitBox.width) {
					int intervalle = Math.abs((xPlayer - m_coord.X()) / 10);
					for (int i = 0; i < 10; i++) {
						if (!m_model.m_room.isBlocked(m_coord.X() + i * intervalle, m_coord.Y() + 1)) {
							return false;
						}
					}
					return true;
				}
			} else if (dir.toString().equals("W")) {
				if (xPlayer > hitBox.x - 3*hitBox.width && xPlayer < hitBox.x) {
					int intervalle = Math.abs((xPlayer - m_coord.X()) / 10);
					for (int i = 0; i < 10; i++) {
						if (!m_model.m_room.isBlocked(m_coord.X() - i * intervalle, m_coord.Y() + 1)) {
							return false;
						}
					}
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean move(Direction dir) {
		if (!(m_state == CurrentState.isDead)) {
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
