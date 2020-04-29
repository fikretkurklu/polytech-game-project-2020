package opponent;

import java.io.IOException;

import automaton.Automaton;
import automaton.Direction;
import game.Model;
import player.Character;

public abstract class Opponent extends Character {

	public Opponent(Automaton automaton, int x, int y, Direction dir, Model model, int maxLife, int life,
			int attackSpeed, int resistance, int strength) throws IOException {
		super(automaton, x, y, dir, model, maxLife, life, attackSpeed, resistance, strength);
		// TODO Auto-generated constructor stub
	}

}
