package opponent;

import automaton.Automaton;
import automaton.Direction;
import game.Model;
import player.Player;

public class NormalKey extends Key {

	public NormalKey(Automaton automaton, int x, int y, Model model) throws Exception {
		super(automaton, x, y, model);

		m_image = loadImage("resources/Room/dropable/Golden_Key.png");

		m_width = m_image.getWidth(null);
		m_height = m_image.getHeight(null);
	}
	
	@Override
	public boolean wizz(Direction dir) {
		((Player)m_model.m_player).setKey(this);
		m_model.setKey(null);
		m_model.m_room.getDoor().setVisible(true);

		return false;
	}


}
