package underworld;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import environnement.Element;
import game.Coord;
import game.ImageLoader;
import player.Character;
import projectile.Projectile;

public class Lure extends Projectile {

	public static final int SIZE = (int) Element.SIZE;

	Image m_apparitionImages[];
	Image m_disaparitionImages[];
	Image m_images[];

	int sizeAnimation = UnderworldParam.lureImage.length;
	int sizeApearingAnimation = UnderworldParam.lureApparitionImage.length;

	boolean appearing, disapearing, disapered, normal;

	int m_imageIndex = 0;

	long m_imageElapsed;

	Rectangle hitBox;

	public Lure(Automaton projectileAutomaton, Coord c, double angle, Character shooter) throws IOException {
		super(projectileAutomaton, c, angle, shooter, null);
		appearing = true;
		normal = false;
		disapearing = false;
		disapered = false;
		setPosition();
		m_images = ImageLoader.loadImageLure(m_apparitionImages, m_disaparitionImages, sizeApearingAnimation, sizeAnimation, m_imageIndex, m_imageElapsed);
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
			if (disapered) {
				return;
			} else if (normal) {
				g.drawImage(m_images[m_imageIndex], m_coord.X(), m_coord.Y(), SIZE, SIZE, null);
			} else if (appearing) {
				g.drawImage(m_apparitionImages[m_imageIndex], m_coord.X(), m_coord.Y(), SIZE, SIZE, null);
			} else if (disapearing) {
				g.drawImage(m_disaparitionImages[m_imageIndex], m_coord.X(), m_coord.Y(), SIZE, SIZE, null);
			}
			g.drawRect(hitBox.x, hitBox.y, SIZE, SIZE);
		}
	}

	public void elapsed() {
		normal = false;
		disapearing = true;
		m_imageIndex = 0;
	}

	public boolean isDestroying() {
		return disapearing;
	}

	public boolean isDestroyed() {
		return disapered;
	}

	public void tick(long elapsed) {
		m_imageElapsed += elapsed;
		if (m_imageElapsed > 200) {
			m_imageElapsed = 0;
			m_imageIndex++;
			if (normal) {
				if (m_imageIndex >= sizeAnimation) {
					m_imageIndex = 0;
				}
			} else if (appearing) {
				if (m_imageIndex >= sizeApearingAnimation) {
					appearing = false;
					normal = true;
					m_imageIndex = 0;
				}
			} else if (disapearing) {
				if (m_imageIndex >= sizeApearingAnimation) {
					disapearing = false;
					disapered = true;
					m_imageIndex = 0;
				}
			}
		}
		m_automaton.step(this);
	}

	public Coord getCoord() {
		return m_coord;
	}
	
	public boolean checkBlock(int x, int y) {
		return getM_model().m_underworld.isBlocked(x, y);
	}
	
	public Coord getBlockCoord(int x, int y) {
		return getM_model().m_underworld.blockBot(x, y);
	}

	@Override
	public boolean cell(Direction dir, Category cat) {
		// TODO Auto-generated method stub
		return false;
	}
}
