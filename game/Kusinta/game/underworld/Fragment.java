package underworld;

import java.awt.Color;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.HashMap;
import automaton.Automaton;
import automaton.Entity;
import environnement.Element;
import game.Coord;
import game.Model;

public class Fragment extends Entity{
	
	public static final int FragmentSIZE = (int) (Element.SIZE / 2);
	
	long m_imageElapsed = 0;
	boolean picked;
	
	public Fragment(Automaton automaton, Coord coord, Model model, Image[] images, HashMap<Action, int[]> hmActions) {
		super(automaton, images, hmActions);
		currentAction = Action.DEFAULT;
		resetAnim();
		m_model = model;
		m_width = FragmentSIZE;
		m_height = FragmentSIZE;
		hitBox = new Rectangle(m_coord.X() - (int)(Element.SIZE/1.5), m_coord.Y() - (int)(Element.SIZE/1.5), Element.SIZE * 2, Element.SIZE * 2);
	}
	
	@Override
	public boolean gotpower() {
		return !picked;
	}
	
	public boolean contains(int x, int y) {
		return hitBox.contains(x,y);
	}
	
	public void setPicked() {
		picked = true;
	}
	

	public void paint(Graphics g) {
		if (picked)
			return;
		g.drawImage(bImages[currentIndex[m_imageIndex]], m_coord.X(), m_coord.Y(), null);
		g.setColor(Color.blue);
		g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
	}
	
	public void tick(long elapsed) {
		if (picked)
			return;
		m_imageElapsed += elapsed;
	    if (m_imageElapsed > m_imageTick) {
	      m_imageElapsed = 0;
	      m_imageIndex++;
	      if (m_imageIndex >= currentIndex.length) {
	    	  m_imageIndex = 0;
	      }
	    }
	    m_stepElapsed += elapsed;
		if (m_stepElapsed > m_stepTick) {
			m_stepElapsed -= m_stepTick;
			m_automaton.step(this);
		}
	}
}
