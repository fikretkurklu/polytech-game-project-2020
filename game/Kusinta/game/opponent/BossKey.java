package opponent;

import java.awt.Image;
import java.util.HashMap;

import automaton.Automaton;
import automaton.Direction;
import automaton.Entity.Action;
import game.ImageLoader;
import game.Model;

public class BossKey extends Key{

	public BossKey(Automaton automaton, int x, int y, Model model, Image[] bImages, HashMap<Action, int[]> indiceAction) throws Exception {
		super(automaton, x, y, model, bImages, indiceAction);

		m_image = ImageLoader.loadImage("resources/Room/dropable/boss_key.png", SIZE);

		m_width = m_image.getWidth(null);
		m_height = m_image.getHeight(null);
	}
	
	@Override
	public boolean wizz(Direction dir) {
		m_model.m_player.setBossKey(true);
		m_model.setBossKey(null);

		return false;
	}

}
