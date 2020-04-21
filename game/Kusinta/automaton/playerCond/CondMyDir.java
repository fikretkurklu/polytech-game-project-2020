package playerCond;

import game.Direction;
import game.Entity;

public class CondMyDir implements ICondition{

	Direction m_dir;

	public CondMyDir(Direction dir) {
		m_dir = dir;
	}
	
	@Override
	public boolean eval(Entity e) {
		return e.mydir(m_dir);
	}
	
		
}
