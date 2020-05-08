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

public class Gate extends Entity {

	boolean appearing, loopAnimation;

	public static final int GateSIZE = 4 * Element.SIZE;

	public Gate(Automaton automaton, Coord coord, Model model, Image[] images, HashMap<Action, int[]> hmActions) {
		super(automaton, images, hmActions);
		currentAction = Action.MOVE;
		resetAnim();
		m_coord = coord;
		m_model = model;
		m_width = GateSIZE;
		m_height = GateSIZE;
		appearing = false;
		loopAnimation = false;
		m_coord = new Coord(coord);
		hitBox = new Rectangle(m_coord.X() + Element.SIZE, m_coord.Y() + Element.SIZE, Element.SIZE * 2,
				Element.SIZE * 2);
	}

	public void paint(Graphics g) {
		g.drawImage(bImages[currentIndex[m_imageIndex]], m_coord.X(), m_coord.Y(), null);
		g.setColor(Color.blue);
		g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
	}

	public boolean contains(Rectangle hitBox) {
		return hitBox.contains(m_coord.X() + 2 * Element.SIZE, m_coord.Y() + 2 * Element.SIZE);
	}

	public void tick(long elapsed) {
		m_imageElapsed += elapsed;
		if (m_imageElapsed > m_imageTick) {
			m_imageElapsed = 0;
			m_imageIndex++;
			if (currentAction == Action.MOVE) {
				if (m_imageIndex >= currentIndex.length) {
					currentAction = Action.DEFAULT;
					resetAnim();
				}
			} else if (currentAction == Action.DEFAULT) {
					if (m_imageIndex >= currentIndex.length) {
						m_imageIndex = 0;
					}
			}
		}
		m_stepElapsed += elapsed;
		if (m_stepElapsed > m_stepTick) {
			m_stepElapsed -= m_stepTick;
			m_automaton.step(this);
		}
	}
}
