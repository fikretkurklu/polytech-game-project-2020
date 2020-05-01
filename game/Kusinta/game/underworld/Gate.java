package underworld;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import automaton.Automaton;
import environnement.Element;
import game.Coord;
import game.Model;

public class Gate extends Element {
	
	Model m_model;
	int m_width, m_height;
	Rectangle hitBox;
	BufferedImage[] m_images;
	
	long m_imageElapsed = 0;
	boolean appearing;

	public Gate(Automaton automaton, Coord coord, Model model) {
		super(false, true, coord, automaton);
		m_model = model;
		m_width = (int) (Element.SIZE / 2);
		m_height = (int) (Element.SIZE / 2);
		appearing = true;
		hitBox = new Rectangle(m_coord.X() - (int)(Element.SIZE/1.5), m_coord.Y() - (int)(Element.SIZE/1.5), Element.SIZE * 2, Element.SIZE * 2);
		try {
			m_images = m_model.loadSprite(UnderworldParam.gateSprite, 5, 6);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
