package underworld;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import automaton.Automaton;
import environnement.Element;
import game.Coord;
import game.Model;

public class Fragment extends Element{
	
	public static final int FragmentSIZE = (int) (Element.SIZE / 2);
	
	int m_width, m_height;
	int m_imageIndex = 0;
	Model m_model;
	Image[] m_images;
	Rectangle hitBox;
	
	long m_imageElapsed = 0;
	boolean picked;
	
	public Fragment(Automaton automaton, Coord coord, Model model, Image[] images) {
		super(false, true, coord, automaton);
		m_model = model;
		m_width = FragmentSIZE;
		m_height = FragmentSIZE;
		hitBox = new Rectangle(m_coord.X() - (int)(SIZE/1.5), m_coord.Y() - (int)(SIZE/1.5), SIZE * 2, SIZE * 2);
		m_images = images;
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
	
	@Override
	public void paint(Graphics g) {
		if (picked)
			return;
		if (m_images != null) {
			g.drawImage(m_images[m_imageIndex], m_coord.X(), m_coord.Y(), null);
			g.setColor(Color.blue);
			g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
		}
	}
	
	public void tick(long elapsed) {
		if (picked)
			return;
		m_imageElapsed += elapsed;
	    if (m_imageElapsed > 200) {
	      m_imageElapsed = 0;
	      m_imageIndex++;
	      if (m_imageIndex >= UnderworldParam.fragmentAnimationSize) {
	    	  m_imageIndex = 0;
	      }
	    }
	}
}
