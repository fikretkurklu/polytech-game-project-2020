package underworld;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import automaton.Automaton;
import environnement.Element;
import game.Coord;
import game.Model;
import player.Character;
import projectile.Projectile;

public class Lure extends Projectile {

	public static final int SIZE = (int) (1.5 * Element.SIZE);

	Image m_apparitionImages[];
	Image m_disaparitionImages[];
	Image m_images[];
	int sizeAnimation = UnderworldParam.lureImage.length;
	int sizeApearingAnimation = UnderworldParam.lureApparitionImage.length;

	boolean appearing, disapearing, disapered, normal;

	int m_imageIndex, m_imageAppearing = 0;
	

	long m_imageElapsed;

	public Lure(Automaton projectileAutomaton, int x, int y, double angle, Character shooter, Model model) {
		super(projectileAutomaton, x, y, angle, shooter, model);
		appearing = true;
		normal = false;
		disapearing = false;
		disapered = false;
		loadImage();
	}

	private void loadImage() {
		m_apparitionImages = new Image[sizeApearingAnimation];
		m_disaparitionImages = new Image[sizeApearingAnimation];
		m_images = new Image[sizeAnimation];
		File imageFile;
		Image image;
		m_imageIndex = 0;
		m_imageElapsed = 0;
		try {
			for (int i = 0; i < sizeApearingAnimation; i++) {
				imageFile = new File(UnderworldParam.lureApparitionImage[i]);
				image = ImageIO.read(imageFile);
				m_apparitionImages[i] = image.getScaledInstance(SIZE, SIZE, 0);
				m_disaparitionImages[6 - i] = image.getScaledInstance(SIZE, SIZE, 0);
			}
			for (int j = 0; j < sizeAnimation; j++) {
				imageFile = new File(UnderworldParam.lureImage[j]);
				image = ImageIO.read(imageFile);
				m_images[j] = image.getScaledInstance(SIZE, SIZE, 0);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void paint(Graphics g) {
		if (m_images != null) {
			if (disapered) {
				return;
			} else if (normal) {
				g.drawImage(m_images[m_imageIndex], m_coord.X(), m_coord.Y(), SIZE, SIZE, null);
			} else if (appearing){
					g.drawImage(m_apparitionImages[m_imageIndex], m_coord.X(), m_coord.Y(), SIZE, SIZE, null);
			} else if (disapearing) {
					g.drawImage(m_disaparitionImages[m_imageIndex], m_coord.X(), m_coord.Y(), SIZE, SIZE, null);
			}
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
}
