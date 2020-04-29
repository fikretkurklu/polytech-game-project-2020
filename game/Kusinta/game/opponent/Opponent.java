package opponent;

import java.awt.Rectangle;
import java.io.IOException;

import automaton.Automaton;
import automaton.Direction;
import game.Model;
import player.Character;

public abstract class Opponent extends Character {
	
	int m_width, m_height;
	
	Rectangle hotBox;

	public Opponent(Automaton automaton, int x, int y, Direction dir, Model model, int maxLife, int life,
			int attackSpeed, int resistance, int strength) throws IOException {
		super(automaton, x, y, dir, model, maxLife, life, attackSpeed, resistance, strength);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void tick(long elapsed) {
		m_automaton.step(this);
		
	}
	

	@Override
	public void setPressed(int keyChar, boolean b) {
		// TODO Auto-generated method stub	
	}

}
