package underworld;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import automaton.Automaton;
import environnement.Element;
import game.Coord;
import game.Model;

public class Fragment extends Element{
	
	int m_width, m_height;
	int m_imageIndex = 0;
	Model m_model;
	BufferedImage[] m_images;
	Rectangle hitBox;
	
	long m_imageElapsed = 0;
	boolean picked;
	
	public Fragment(Automaton automaton, Coord coord, Model model) {
		super(false, true, coord, automaton);
		m_model = model;
		m_width = (int) (SIZE / 2);
		m_height = (int) (SIZE / 2);
		hitBox = new Rectangle(m_coord.X() - (int)(SIZE/1.5), m_coord.Y() - (int)(SIZE/1.5), SIZE * 2, SIZE * 2);
		try {
			m_images = m_model.loadSprite(UnderworldParam.fragmentSprite, 3, 3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			g.drawImage(m_images[m_imageIndex], m_coord.X(), m_coord.Y(), m_width, m_height, null);
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
