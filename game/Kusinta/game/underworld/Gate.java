package underworld;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import automaton.Automaton;
import environnement.Element;
import game.Coord;
import game.Model;

public class Gate extends Element {

	Model m_model;
	int m_width, m_height;
	int m_imageIndex = 0;
	Rectangle hitBox;
	Image[] m_images;

	long m_imageElapsed = 0;
	boolean appearing, loopAnimation;

	public static final int GateSIZE = 4 * Element.SIZE;

	public Gate(Automaton automaton, Coord coord, Model model, Image[] images) {
		super(false, true, coord, automaton);
		m_model = model;
		m_width = GateSIZE;
		m_height = GateSIZE;
		appearing = false;
		loopAnimation = false;
		hitBox = new Rectangle(m_coord.X() + SIZE, m_coord.Y() + SIZE, SIZE * 2, SIZE * 2);
		m_images = images;
	}

	@Override
	public void paint(Graphics g) {
		if (m_images != null) {
			g.drawImage(m_images[m_imageIndex], m_coord.X(), m_coord.Y(), null);
			g.setColor(Color.blue);
			g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
		}
	}

	public boolean contains(Rectangle hitBox) {
		return hitBox.contains(m_coord.X() + 2 * SIZE, m_coord.Y() + 2 * SIZE);
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
