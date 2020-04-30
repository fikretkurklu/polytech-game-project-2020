package underworld;

import java.awt.Graphics;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import game.Model;
import player.Character;
import projectile.Projectile;

public class Lure extends Projectile {
	
	boolean elapsed;

	public Lure(Automaton projectileAutomaton, int x, int y, double angle, Character shooter, Model model) {
		super(projectileAutomaton, x, y, angle, shooter, model, shooter.getDirection());
		elapsed = false;
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean cell(Direction dir, Category cat) {
		// TODO Auto-generated method stub
		return false;
	}

}
