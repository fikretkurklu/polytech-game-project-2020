package projectile;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import automaton.Automaton;
import automaton.Direction;
import environnement.Element;
import game.Coord;
import opponent.FlyingOpponent;

public class MagicProjectile extends Projectile {
	
	public static final int SIZE = (int) (1.5 * Element.SIZE);
	
	Image[] images;
	int m_imageElapsed, m_image_index;
	
	int moving;
	int DIMENSION;

	int m_height;
	int m_width;
	
	public MagicProjectile(Automaton projectileAutomaton, int x, int y, double angle, FlyingOpponent shooter, Direction direction) throws Exception {
		super(projectileAutomaton, x, y, angle, shooter, shooter.getModel(), direction);
		
		images = m_shooter.getProjectileImages();
		
		DIMENSION = SIZE / (images[0].getHeight(null));

		float ratio = (float) (images[0].getWidth(null) * 4) / (float) (5 * images[0].getHeight(null));

		m_height = DIMENSION * images[0].getHeight(null);
		m_width = (int) (ratio * images[0].getWidth(null));

		if (m_direction.toString().equals("E")) {
			hitBox = new Coord((int) (m_coord.X() + (m_width / 2) * Math.cos(m_angle)),
					(int) (m_coord.Y() - (m_width / 2) * Math.sin(m_angle)));
		} else {
			hitBox = new Coord((int) (m_coord.X() - (m_width / 2) * Math.cos(m_angle)),
					(int) (m_coord.Y() - (m_width / 2) * Math.sin(m_angle)));
		}

		m_imageElapsed = 0;
		m_image_index = 0;
		
		setSpeed(4);
		
	}
	
	public void paint(Graphics g) {
		
		Image img = images[m_image_index];
		
		if (images != null) {
			int w = DIMENSION * m_width;
			int h = m_height;
			Graphics2D g2D = (Graphics2D) g;
			if (m_direction.toString().equals("E")) {
				g2D.rotate(-m_angle, m_coord.X(), m_coord.Y());
				g2D.drawImage(img, m_coord.X() - (w / 2), m_coord.Y() - h / 2, w, h, null);
				g2D.rotate(m_angle, m_coord.X(), m_coord.Y());
			} else {
				g2D.rotate(m_angle, m_coord.X(), m_coord.Y());
				g2D.drawImage(img, m_coord.X() + (w / 2), m_coord.Y() - h / 2, -w, h, null);
				g2D.rotate(-m_angle, m_coord.X(), m_coord.Y());
			}
		}
	}
	
	public void tick(long elapsed) {
		m_automaton.step(this);
		m_imageElapsed += elapsed;
		if (m_imageElapsed > 200) {
			m_imageElapsed = 0;
			
			if(m_image_index == 12) {
				((FlyingOpponent)m_shooter).removeProjectile(this);
			}

			if(m_State == State.OK_STATE) {
				m_image_index = 0;
			} else {
				m_image_index ++;
			}
		}
		
	}

}
