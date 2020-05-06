package underworld;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

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
	public static final int REFRESH_TICK = 200;
	public static final int PERIOD = 1;
	
	Image m_images[];

	int m_imageIndex = UnderworldParam.sizeLureAnimation;
	int delay;
	long m_imageElapsed, m_stepElapsed;

	Rectangle hitBox;
	
	private static final int APPEARING = 1;
	private static final int NORMAL = 2;
	private static final int ELAPSED = 3;

	private int animationMode = APPEARING;

	public Lure(Automaton projectileAutomaton, Coord c, Character shooter, Direction dir, Image[] images, Model model) {
		super(projectileAutomaton, c, 0, shooter, dir);
		m_model = model;
		delay = MAX_DELAY;
		m_imageElapsed = 0;
		m_images = images;
		hitBox = new Rectangle(m_coord.X(), m_coord.Y(), SIZE, SIZE);
	}
	
	@Override
	public boolean gotpower() {
		return delay > 0;
	}

	public void paint(Graphics g) {
		if (m_images != null) {
			g.drawImage(m_images[m_imageIndex], m_coord.X(), m_coord.Y(), SIZE, SIZE, null);
			g.setColor(Color.blue);
			g.drawRect(hitBox.x, hitBox.y, SIZE, SIZE);
		}
	}

	@Override
	public boolean explode() {
		animationMode = ELAPSED;
		m_imageIndex = UnderworldParam.sizeLureApearingAnimation;
		return true;
	}

	public void tick(long elapsed) {
		m_imageElapsed += elapsed;
		if (m_imageElapsed > REFRESH_TICK) {
			m_imageElapsed = 0;
			m_imageIndex++;
			switch(animationMode) {
			case NORMAL:
				if (m_imageIndex >= UnderworldParam.sizeLureAnimation) {
					m_imageIndex = 0;
				}
				break;
			case APPEARING:
				if (m_imageIndex >= UnderworldParam.sizeLureApearingAnimation) {
					animationMode = NORMAL;
					m_imageIndex = 0;
				}
				break;
			case ELAPSED:
				if (m_imageIndex >= UnderworldParam.sizeLureDisaparitionAnimation) {
					m_shooter.removeProjectile(this);
				}
				break;
			}
		}
		m_stepElapsed += elapsed;
		if (m_stepElapsed > STEP_TICK) {
			delay -= PERIOD;
			m_stepElapsed -= STEP_TICK;
			m_automaton.step(this);
		}
	}

	public Coord getCoord() {
		return m_coord;
	}
}
