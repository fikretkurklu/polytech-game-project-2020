package automaton;

import java.awt.Rectangle;
import java.util.LinkedList;

import automaton.Automaton;
import automaton.State;
import game.Coord;
import game.Model;
import opponent.Opponent;
import player.Character;

public abstract class Entity {

	private State m_state;
	protected Automaton m_automaton;

	protected Model m_model;
	protected Direction m_direction;

	protected Coord m_coord;
	protected Rectangle hitBox;

	protected int m_width, m_height;

	protected int X_MOVE;
	
	protected Character collidingWith;

	public Entity() {
	}

	public Entity(Automaton automaton) {
		m_state = automaton.getInitialState();
		m_automaton = automaton;
	}

	public State getCurrentState() {
		return m_state;
	}

	public void setCurrentState(State state) {
		m_state = state;
	}

	// Action

	public boolean move(Direction dir) {
		return false;
	}

	public boolean jump(Direction dir) {
		return false;
	}

	public boolean pop(Direction dir) {
		return false;
	}

	public boolean wizz(Direction dir) {
		return false;
	}

	public boolean power() {
		return false;
	}

	public boolean pick(Direction dir) {
		return false;
	}

	public boolean throwAction(Direction dir) {
		return false;
	}

	public boolean turn(Direction dir) {
		return false;
	}

	public boolean get() {
		return false;
	}

	public boolean store() {
		return false;
	}

	public boolean explode() {
		return false;
	}

	public boolean egg(Direction dir) {
		return false;
	}

	public boolean hit(Direction dir) {
		return false;
	}

	public boolean protect(Direction dir) {
		return false;
	}

	public boolean waitAction() {
		return false;
	}
	// Conditions

	public boolean mydir(Direction dir) {
		return m_direction == dir;
	}

	public boolean cell(Direction dir, Category cat) {
		if (m_model.actualMode == Model.mode.ROOM) {
			if (dir == Direction.F) {
				dir = m_direction;
			}
			int x, y;
			switch (dir.toString()) {
			case Direction.Hs:
				if (cat == Category.P) {
					Rectangle playerHitBox = m_model.getPlayer().getHitBox();
					int xHB = hitBox.x;
					int yHB = hitBox.y;
					int widthHB = hitBox.width;
					int heightHB = hitBox.height;
					if (playerHitBox.contains(xHB, yHB) || playerHitBox.contains(xHB + widthHB / 2, yHB)
							|| playerHitBox.contains(xHB + widthHB, yHB) || playerHitBox.contains(xHB + widthHB, yHB + heightHB / 2)
							|| playerHitBox.contains(xHB + widthHB, yHB + heightHB)
							|| playerHitBox.contains(xHB + widthHB / 2, yHB + heightHB)
							|| playerHitBox.contains(xHB, yHB + heightHB) || playerHitBox.contains(xHB, yHB + heightHB / 2)
							|| playerHitBox.contains(xHB + widthHB / 2, yHB)) {
						return true;
					}
				} else if (cat == Category.O) {
					x = hitBox.x + hitBox.width + X_MOVE;
					return (m_model.m_room.isBlocked(x, hitBox.y + hitBox.height / 2)
							|| m_model.m_room.isBlocked(x, hitBox.y)
							|| m_model.m_room.isBlocked(x, hitBox.y + hitBox.height));
				} else if (cat == Category.A) {
					LinkedList<Opponent> opponents = m_model.getOpponent();
					for (Opponent op : opponents) {
						if (op.getHitBox().contains(hitBox.x, hitBox.y)
								|| op.getHitBox().contains(hitBox.x, hitBox.y)) {
							this.setCollidingWith(op);
							return true;
						}
					}
					return false;
				}
				return false;
			case Direction.Es:
				if (cat == Category.O) {
					x = hitBox.x + hitBox.width + X_MOVE;
					return (m_model.m_room.isBlocked(x, hitBox.y + hitBox.height / 2)
							|| m_model.m_room.isBlocked(x, hitBox.y)
							|| m_model.m_room.isBlocked(x, hitBox.y + hitBox.height));
				}
				return false;
			case Direction.Ws:
				x = hitBox.x - X_MOVE;
				if (m_model.m_room.isBlocked(x, hitBox.y + hitBox.height / 2)
						|| m_model.m_room.isBlocked(x, hitBox.y)
						|| m_model.m_room.isBlocked(x, hitBox.y + hitBox.height)) {
					return true;
				}
				return false;
			case Direction.Ns:
				y = hitBox.y - X_MOVE;
				if (m_model.m_room.isBlocked(hitBox.x, y)
						|| m_model.m_room.isBlocked(hitBox.x + hitBox.width / 2, y)
						|| m_model.m_room.isBlocked(hitBox.x + hitBox.width, y)) {
					return true;
				}
				return false;
			case Direction.Ss:
				y = hitBox.y + X_MOVE + hitBox.height;
				if (m_model.m_room.isBlocked(hitBox.x, y)
						|| m_model.m_room.isBlocked(hitBox.x + hitBox.width / 2, y)
						|| m_model.m_room.isBlocked(hitBox.x + hitBox.width, y)) {
					return true;
				}
				return false;
			default:
				return false;
			}
		}
		return false;

	}

	public boolean closest(Category cat, Direction dir) {
		return false;
	}

	public boolean gotstuff() {
		return false;
	}

	public boolean gotpower() {
		return false;
	}

	public boolean key(int keyCode) {
		return false;
	}

	public Automaton getAutomaton() {
		return m_automaton;
	}
	
	public void setCollidingWith(Character cha) {
		if (collidingWith != cha) {
			collidingWith = cha;
		}
	}
}
