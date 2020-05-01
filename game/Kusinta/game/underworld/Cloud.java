package underworld;

import java.awt.Graphics;
import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import game.Coord;
import game.Model;
import environnement.Element;

public class Cloud extends Element{
	
	int m_width, m_height;
	Model m_model;
	boolean outScreen; // Indique si le nuage n'est plus visible à l'écran
	boolean move; // Booléen qui permet un mouvement de 1 pixel du nuage par seconde
	long timeElapsed = 0;

	public Cloud(Automaton automaton, Coord coord, Model model) {
		super(false, true, coord, automaton);
		m_width = 2 * Element.SIZE;
		m_height = 2 * Element.SIZE;
		m_model = model;
		outScreen = false;
		move = false;
		try {
			loadImage(UnderworldParam.cloudImage[2], m_width, m_height);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean cell(Direction dir, Category cat) {
		if ((dir.toString().equals("B")) && (cat.toString().equals("O"))) {
			return m_coord.X() + m_width <= 0;
		}
		return false;
	}
	
	@Override
	public boolean explode() {
		outScreen = true;
		return true;
	}
	

	public void reactivate() {
		m_coord.setX(4556);
		setCurrentState(m_automaton.getInitialState());;
		outScreen = false;
		move = false;
		timeElapsed = 0;
	}
	
	@Override
	public boolean move(Direction dir) {
		if (move) {
			move = false;
			m_coord.translateX(-1);
			return true;
		}
		return false;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}
	
	public void tick(long elapsed) {
			timeElapsed += elapsed;
		    if (timeElapsed > 10) {
		      timeElapsed = 0;
		      move = true;
		    }
	}
}
