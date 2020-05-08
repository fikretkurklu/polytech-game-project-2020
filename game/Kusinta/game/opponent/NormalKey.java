package opponent;

import java.awt.Image;
import java.util.HashMap;

import automaton.Automaton;
import automaton.Direction;
import game.Coord;
import game.Model;
import player.Player;

public class NormalKey extends Key {

	public NormalKey(Automaton automaton, Coord c, Model model, Image[] bImages, HashMap<Action, int[]> indiceAction) throws Exception {
		super(automaton, c, model, bImages, indiceAction);
	}
	
	@Override
	public boolean wizz(Direction dir) {
		((Player)m_model.m_player).setKey(true);
		m_model.setKey(null);
		m_model.m_room.getDoor().setVisible(true);

		return false;
	}


}
