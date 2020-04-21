package projectile;

import automaton.Category;
import automaton.Direction;
import player.Player;

public class Arrow extends Projectile{
	static final int SPEED = 10;

	public Arrow(int x, int y, double angle, Player player) {
		super(x, y, angle, player);
	}

	@Override
	public boolean explode() {
		m_shooter.m_arrows.remove(this);
		return true;
	}

	@Override
	public boolean move(Direction dir) {
		// TODO Auto-generated method stub
		double tmp_x = SPEED * Math.cos(m_angle);
        double tmp_y = SPEED * Math.sin(m_angle);
        
        m_x += tmp_x;
        m_y += tmp_y;
		
		return true;
	}


}
