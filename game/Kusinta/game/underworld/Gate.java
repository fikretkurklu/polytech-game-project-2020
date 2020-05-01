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

public class Gate extends Element {

	Model m_model;
	int m_width, m_height;
	int m_imageIndex = 0;
	Rectangle hitBox;
	BufferedImage[] m_images;

	long m_imageElapsed = 0;
	boolean appearing, loopAnimation;

	public Gate(Automaton automaton, Coord coord, Model model) {
		super(false, true, coord, automaton);
		m_model = model;
		m_width = (int) 4 * Element.SIZE;
		m_height = (int) 4 * Element.SIZE;
		appearing = false;
		loopAnimation = false;
		hitBox = new Rectangle(m_coord.X() + (Element.SIZE / 2), m_coord.Y() + (Element.SIZE / 2), Element.SIZE * 3,
				Element.SIZE * 3);
		try {
			m_images = m_model.loadSprite(UnderworldParam.gateSprite, 6, 6);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics g) {
		if (m_images != null) {
			g.drawImage(m_images[m_imageIndex], m_coord.X(), m_coord.Y(), m_width, m_height, null);
			g.setColor(Color.blue);
			g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
		}
	}
	
	public boolean contains(int x, int y) {
		return hitBox.contains(x,y);
	}

	public void tick(long elapsed) {
		m_imageElapsed += elapsed;
		if (m_imageElapsed > 200) {
			m_imageElapsed = 0;
			if (appearing) {
				m_imageIndex++;
				if (m_imageIndex >= UnderworldParam.gateApparitionAnimationSize) {
					appearing = false;
					m_imageIndex = UnderworldParam.gateApparitionAnimationSize;
				}
			} else {
				if (loopAnimation) {
					m_imageIndex--;
					if (m_imageIndex < UnderworldParam.gateApparitionAnimationSize) {
						m_imageIndex = UnderworldParam.gateApparitionAnimationSize;
						loopAnimation = false;
					}
				} else {
					m_imageIndex++;
					if (m_imageIndex >= UnderworldParam.gateAnimationSize) {
						m_imageIndex = UnderworldParam.gateAnimationSize;
						loopAnimation = true;
					}
				}
			}
		}
	}
}
