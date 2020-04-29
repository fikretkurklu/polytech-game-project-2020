package projectile;

import automaton.Automaton;
import automaton.Direction;
import opponent.FlyingOpponent;

public class MagicProjectile extends Projectile {
	
	public MagicProjectile(Automaton projectileAutomaton, int x, int y, double angle, FlyingOpponent shooter, Direction direction) {
		super(projectileAutomaton, x, y, angle, shooter, shooter.getModel(), direction);
	}

}
