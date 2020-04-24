package underworld;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import game.Coord;
import environnement.Element;

public class Cloud extends Element{
	
	int m_width, m_height;
	int xMax, yMax;
	String imagePath;
	boolean outScreen; // Indique si le nuage n'est plus visible à l'écran
	boolean move; // Booléen qui permet un mouvement de 1 pixel du nuage par seconde
	long timeElapsed = 0;

	public Cloud(Automaton automaton, Coord coord) {
		super(false, true, coord, automaton);
		m_width = Element.SIZE;
		m_height = Element.SIZE;
		xMax = getCoord().X() + m_width;
		yMax = getCoord().Y() + m_height;
		outScreen = false;
		move = false;
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
		if ((dir.toString().equals("H")) && (cat.toString().equals("O"))) {
			if (getCoord().X() + m_width <= 0) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean explode() {
		outScreen = true;
		return true;
	}
	
	
	@Override
	public boolean move(Direction dir) {
		if (move) {
			move = false;
			getCoord().translateX(-1);
			xMax = getCoord().X() + m_width;
			return true;
		}
		return false;
	}
	
	public void tick(long elapsed) {
			timeElapsed += elapsed;
		    if (timeElapsed > 10) {
		      timeElapsed = 0;
		      move = true;
		    }
	}
}
