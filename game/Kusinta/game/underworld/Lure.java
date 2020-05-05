package underworld;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import environnement.Element;
import game.Coord;
import game.Model;
import player.Character;
import projectile.Projectile;

public class Lure extends Projectile {

	public static final int SIZE = (int) Element.SIZE;
	Image m_images[];
	boolean appearing, disapearing, disapered, normal;

	int m_imageIndex = UnderworldParam.sizeLureAnimation;

	long m_imageElapsed;

	Rectangle hitBox;
	
	private static final int APPEARING = 1;
	private static final int NORMAL = 2;
	private static final int DISAPPEARING = 3;

	private int animationMode = APPEARING;

	public Lure(Automaton projectileAutomaton, Coord c, double angle, Character shooter, Direction dir, Image[] images, Model model) {
		super(projectileAutomaton, c, angle, shooter, dir);
		m_model = model;
		disapearing = false;
		disapered = false;
		m_imageElapsed = 0;
		m_images = images;
		setPosition();
	}

	private void setPosition() {
		hitBox = new Rectangle(m_coord.X(), m_coord.Y(), SIZE, SIZE);
		int xUp = hitBox.x + (SIZE / 2);
		int xDown = hitBox.x + (SIZE / 2);
		int yRight = hitBox.y + (SIZE / 2);
		int yLeft = hitBox.y + (SIZE / 2);

		if (checkBlock(xUp, hitBox.y))
			m_coord.setY(getBlockCoord(xUp, hitBox.y).Y() + Element.SIZE);
		if (checkBlock(xDown, hitBox.y + SIZE))
			m_coord.setY(getBlockCoord(xDown, hitBox.y + SIZE).Y() - Element.SIZE);
		if (checkBlock(hitBox.x + SIZE, yRight))
			m_coord.setX(getBlockCoord(hitBox.x + SIZE, yRight).X() - Element.SIZE);
		if (checkBlock(hitBox.x, yLeft))
			m_coord.setX(getBlockCoord(hitBox.x, yLeft).X() + Element.SIZE);
		
		hitBox.setLocation(m_coord.X(), m_coord.Y());
	}

	public void paint(Graphics g) {
		if (m_images != null) {
			if (disapered)
				return;
			g.drawImage(m_images[m_imageIndex], m_coord.X(), m_coord.Y(), SIZE, SIZE, null);
			g.setColor(Color.blue);
			g.drawRect(hitBox.x, hitBox.y, SIZE, SIZE);
		}
	}

	public void elapsed() {
		animationMode = DISAPPEARING;
		disapearing = true;
		m_imageIndex = UnderworldParam.sizeLureApearingAnimation;
	}

	public boolean isDestroying() {
		return disapearing;
	}

	public boolean isDestroyed() {
		return disapered;
	}

	public void tick(long elapsed) {
		if (disapered)
			return;
		m_imageElapsed += elapsed;
		if (m_imageElapsed > 200) {
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
			case DISAPPEARING:
				if (m_imageIndex >= UnderworldParam.sizeLureDisaparitionAnimation) {
					disapearing = false;
					disapered = true;
					m_imageIndex = 0;
				}
				break;
			}
		}
		m_automaton.step(this);
	}

	public Coord getCoord() {
		return m_coord;
	}
	
	public boolean checkBlock(int x, int y) {
		return m_model.m_underworld.isBlocked(x, y);
	}
	
	public Coord getBlockCoord(int x, int y) {
		return m_model.m_underworld.blockCoord(x, y);
	}

	@Override
	public boolean cell(Direction dir, Category cat) {
		// TODO Auto-generated method stub
		return false;
	}
}
