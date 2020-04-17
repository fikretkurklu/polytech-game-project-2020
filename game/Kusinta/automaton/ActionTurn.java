package automaton;

public class ActionTurn implements IAction {

	Direction m_dir;
	
	public ActionTurn(Direction dir) {
		m_dir = dir;
	}
	
	@Override
	public boolean apply(Entity e) {
		return e.turn(m_dir);
	}
}
