package opponent;

import java.awt.Image;
import java.util.HashMap;

import automaton.Automaton;
import automaton.Direction;
import game.Coord;
import game.Model;

public class BossKey extends Key{

	public BossKey(Automaton automaton, Coord c, Model model, Image[] bImages, HashMap<Action, int[]> indiceAction) throws Exception {
		super(automaton, c, model, bImages, indiceAction);
		System.out.println("Création de la boss key");
	}
	
	@Override
	public boolean wizz(Direction dir) {
		m_model.m_player.setBossKey(true);
		m_model.setBossKey(null);
		m_model.m_room.getBossDoor().setVisible(true);

		return false;
	}

}
