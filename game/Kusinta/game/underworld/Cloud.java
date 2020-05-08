package underworld;

import java.awt.Color;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.HashMap;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import automaton.Entity;
import game.Coord;
import game.Model;
import environnement.Element;

public class Cloud extends Entity{
	
	boolean outScreen; // Indique si le nuage n'est plus visible à l'écran
	boolean move; // Booléen qui permet un mouvement de 1 pixel du nuage par seconde
	long timeElapsed = 0;
	
	public static final int SIZE = 2 * Element.SIZE;

	public Cloud(Automaton automaton, Coord coord, Model model, Image[] images, HashMap<Action, int[]> hmActions) {
		super(automaton, images, hmActions);
		currentAction = Action.DEFAULT;
		resetIndexAnimation();
		m_width = SIZE;
		m_height = SIZE;
		hitBox = new Rectangle(m_coord.X(), m_coord.Y(), m_width, m_height);
		m_model = model;
		outScreen = false;
		move = false;
	}
	
	@Override
	public boolean cell(Direction dir, Category cat) {
		if ((dir == Direction.B) && (cat == Category.O)) {
			return m_coord.X() + m_width <= 0;
		}
		return false;
	}
	
	public boolean contains(int x, int y) {
		return hitBox.contains(x,y);
	}
	
	@Override
	public boolean explode() {
		outScreen = true;
		return true;
	}
	

	public void reactivate() {
		m_coord.setX(Underworld.BORDERX);
		hitBox.setLocation(Underworld.BORDERX, m_coord.Y());
		setCurrentState(m_automaton.getInitialState());
		outScreen = false;
		move = false;
		timeElapsed = 0;
	}
	
	@Override
	public boolean move(Direction dir) {
		if (move) {
			move = false;
			m_coord.translateX(-1);
			hitBox.translate(-1, 0);
			return true;
		}
		return false;
	}

	
	public void paint(Graphics g) {
		g.drawImage(bImages[0], m_coord.X(), m_coord.Y(), null);
		g.setColor(Color.blue);
		g.drawRect(hitBox.x, hitBox.y, m_width, m_height);
	}
	
	public void tick(long elapsed) {
			timeElapsed += elapsed;
		    if (timeElapsed > 10) {
		      timeElapsed = 0;
		      move = true;
		    }
		    m_automaton.step(this);
	}
}
