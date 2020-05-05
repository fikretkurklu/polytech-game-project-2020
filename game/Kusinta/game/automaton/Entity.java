package automaton;

import java.awt.Rectangle;

import automaton.Automaton;
import automaton.State;
import game.Coord;
import game.Model;

public abstract class Entity {

	private State m_state;
	protected Automaton m_automaton;

	protected Model m_model;
	protected Direction m_direction;

	protected Coord m_coord;
	protected Rectangle hitBox;

	protected int m_width, m_height;

	protected int X_MOVE; 
	
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
		return false;
	}

	public boolean cell(Direction dir, Category cat) {
		if (m_model.actualMode == Model.mode.ROOM) {
			int x;
			switch (dir.toString()) {
			case Direction.Hs:
				if (cat == Category.P) {
					int xHB = m_model.getPlayer().getHitBox().x;
					int yHB = m_model.getPlayer().getHitBox().y;
					int widthHB = m_model.getPlayer().getHitBox().width;
					int heightHB = m_model.getPlayer().getHitBox().height;
					if (hitBox.contains(xHB, yHB) || hitBox.contains(xHB + widthHB / 2, yHB)
							|| hitBox.contains(xHB + widthHB, yHB) || hitBox.contains(xHB + widthHB, yHB + heightHB / 2)
							|| hitBox.contains(xHB + widthHB, yHB + heightHB)
							|| hitBox.contains(xHB + widthHB / 2, yHB + heightHB)
							|| hitBox.contains(xHB, yHB + heightHB) || hitBox.contains(xHB, yHB + heightHB / 2)
							|| hitBox.contains(xHB + widthHB / 2, yHB)) {
						return true;
					}
				}
				return false;
			case Direction.Es:
				x = hitBox.x + hitBox.width + 1 + X_MOVE;
				if (m_model.m_room.isBlocked(x, m_coord.Y() - m_height / 2)
						|| m_model.m_room.isBlocked(x, m_coord.Y() - 1)
						|| m_model.m_room.isBlocked(x, m_coord.Y() - m_height + 1)) {
					return true;

				}
				return false;
			case Direction.Ws:
				x = hitBox.x - X_MOVE;
				if (m_model.m_room.isBlocked(x, m_coord.Y() - m_height / 2)
						|| m_model.m_room.isBlocked(x, m_coord.Y() - 1)
						|| m_model.m_room.isBlocked(x, m_coord.Y() - m_height + 1)) {
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

}
