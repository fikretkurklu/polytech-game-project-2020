package underworld;

import java.awt.Image;


import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import game.Coord;
import environnement.Element;

public class Cloud extends Element{
	
	public static final int SIZE = 86;
	
	int m_width, m_height;
	String imagePath;
	boolean outScreen; // Indique si le nuage n'est plus visible à l'écran

	public Cloud(Automaton automaton, Coord coord) {
		super(false, true, coord, automaton);
		m_width = 2 * SIZE;
		m_height = 2 * SIZE;
		outScreen = false;
		imagePath = UnderworldParam.cloudImage[0];
		try {
			loadImage(imagePath, m_width, m_height);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean cell(Direction dir, Category cat) {
		if (dir.toString() == "H")
			return getCoord().X() + m_width <= 0;
		return false;
	}
	
	@Override
	public boolean explode() {
		outScreen = true;
		return true;
	}
	
	
	@Override
	public boolean move(Direction dir) {
		getCoord().translateX(-20);
		return true;
	}
}
