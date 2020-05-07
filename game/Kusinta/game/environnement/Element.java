package environnement;

import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;

import automaton.Automaton;
import automaton.Entity;
import game.Coord;

/*
 * 
 * Cette classe abstraite représente la base d'un élément affiché dans la salle, 
 * avec son image, ses coordonnées, ainsi que 2 booléen __isVisible et __isSolid
 * __image contient le sprite de l'élément
 * m_coord contient les coordonnées de l'élément dans la room
 * __isVisible permet de savoir si l'élément doit être affiché à l'écran
 * __isSolide permet de savoir si l'élément peut être traversé
 * 
 */

public abstract class Element extends Entity{

	public static final int SIZE = 86;

	protected Image __image;
	protected boolean __isVisible;
	protected boolean __isSolid;
	
	
	public Element(boolean isSolid, boolean isVisible, Coord coord, Automaton automaton) {
		super(automaton);
		m_coord = coord;
		__isVisible = isVisible;
		__isSolid = isSolid;
	}
	
	public Element(boolean isSolid, boolean isVisible, Coord coord) {
		m_coord = coord;
		__isVisible = isVisible;
		__isSolid = isSolid;
	}
	
	public void setCoord(Coord coord) {
		m_coord = coord;
	}

	public Coord getCoord() {
		return m_coord;
	}
	
	public void paint(Graphics g) {
		if (__isVisible) {
			int x = this.getCoord().X();
			int y = this.getCoord().Y();
			g.drawImage(__image, x, y, null);
		}
		
	}
	
	public boolean isSolid() {
		return __isSolid;
	}
	
	public void setVisible(boolean v) {
		__isVisible = v;
	}

}
