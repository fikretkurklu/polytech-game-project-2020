package opponent;

import automaton.Automaton;
import automaton.Direction;
import game.Model;
import player.Player;

public class BossKey extends Key{

	public BossKey(Automaton automaton, int x, int y, Model model) throws Exception {
		super(automaton, x, y, model);

		m_image = loadImage("resources/Room/dropable/boss_key.png");

		m_width = m_image.getWidth(null);
		m_height = m_image.getHeight(null);
	}
	
	@Override
	public boolean wizz(Direction dir) {
		((Player)m_model.m_player).setBossKey(this);
		m_model.setBossKey(null);

		return false;
	}

}
