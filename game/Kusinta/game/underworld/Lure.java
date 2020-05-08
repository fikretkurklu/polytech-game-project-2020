package underworld;

import java.awt.Color;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.HashMap;

import automaton.Automaton;
import automaton.Direction;
import environnement.Element;
import game.Coord;
import game.Model;
import player.Character;
import projectile.Projectile;

public class Lure extends Projectile {

	public static final int SIZE = Element.SIZE;
	public static final int STEP_TICK = 4;
	public static final int MAX_DELAY = 2500;
	public static final int PERIOD = 1;

	int delay;
	long m_imageElapsed, m_stepElapsed;
	boolean elapsedBoolean = false;

	public Lure(Automaton projectileAutomaton, Coord c, Character shooter, Direction dir, Image[] images, Model model,
			HashMap<Action, int[]> hmActions) {
		super(projectileAutomaton, c, 0, shooter, dir, images, hmActions);
		currentAction = Action.MOVE;
		resetAnim();
		m_model = model;
		delay = MAX_DELAY;
		m_imageElapsed = 0;
		hitBox = new Rectangle(m_coord.X(), m_coord.Y(), SIZE, SIZE);
	}

	@Override
	public boolean gotpower() {
		return delay > 0;
	}

	public void paint(Graphics g) {
		g.drawImage(bImages[currentIndex[m_imageIndex]], m_coord.X(), m_coord.Y(), null);
		g.setColor(Color.blue);
		g.drawRect(hitBox.x, hitBox.y, SIZE, SIZE);
	}

	@Override
	public boolean explode() {
		elapsedBoolean = true;
		currentAction = Action.MOVE;
		resetAnim();
		m_imageIndex = currentIndex.length - 1;
		return true;
	}

	public void tick(long elapsed) {
		m_imageElapsed += elapsed;
		if (m_imageElapsed > m_imageTick) {
			m_imageElapsed = 0;
			switch (currentAction) {
			case DEFAULT:
				m_imageIndex++;
				if (m_imageIndex >= currentIndex.length) {
					m_imageIndex = 0;
				}
				break;
			case MOVE:
				if (elapsedBoolean) {
					m_imageIndex--;
					if (m_imageIndex < 0) {
						m_shooter.removeProjectile(this);
					}
				} else {
					m_imageIndex++;
					if (m_imageIndex >= currentIndex.length) {
						currentAction = Action.DEFAULT;
						resetAnim();
					}
				}
				break;
			default:
				break;
			}
			m_stepElapsed += elapsed;
			if (m_stepElapsed > m_stepTick) {
				delay -= PERIOD;
				m_stepElapsed -= m_stepTick;
				m_automaton.step(this);
			}
		}
	}
}
