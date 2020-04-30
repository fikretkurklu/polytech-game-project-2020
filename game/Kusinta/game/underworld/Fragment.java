package underworld;

import java.awt.Graphics;
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
	
	long m_imageElapsed = 0;
	
	public Fragment(Automaton automaton, Coord coord, Model model) {
		super(false, true, coord, automaton);
		m_model = model;
		m_width = Element.SIZE / 2;
		m_height = Element.SIZE / 2;
		try {
			m_images = m_model.loadSprite(UnderworldParam.fragmentSprite, 3, 3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void paint(Graphics g) {
		if (m_images != null)
			g.drawImage(m_images[m_imageIndex], m_coord.X(), m_coord.Y(), m_width, m_height, null);
	}
	
	public void tick(long elapsed) {
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
