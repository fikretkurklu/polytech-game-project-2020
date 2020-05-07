package opponent;

import java.awt.Image;
import java.util.HashMap;

import automaton.Automaton;
import automaton.Direction;
import automaton.Entity.Action;
import game.ImageLoader;
import game.Model;
import player.Player;

public class NormalKey extends Key {

	public NormalKey(Automaton automaton, int x, int y, Model model, Image[] bImages, HashMap<Action, int[]> indiceAction) throws Exception {
		super(automaton, x, y, model, bImages, indiceAction);

		m_image = ImageLoader.loadImage("resources/Room/dropable/Golden_Key.png", SIZE);

		m_width = m_image.getWidth(null);
		m_height = m_image.getHeight(null);
	}
	
	@Override
	public boolean wizz(Direction dir) {
		((Player)m_model.m_player).setKey(true);
		m_model.setKey(null);
		m_model.m_room.getDoor().setVisible(true);

		return false;
	}


}
