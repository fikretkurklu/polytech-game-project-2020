package underworld;

import automaton.Automaton;
import game.Coord;
import game.Model;
import player.Character;
import projectile.Projectile;

public class Lure extends Projectile {
	
	boolean elapsed;

	public Lure(Automaton projectileAutomaton, int x, int y, double angle, Character shooter, Model model) {
		super(projectileAutomaton, x, y, angle, shooter, model);
		elapsed = false;
	}

}
