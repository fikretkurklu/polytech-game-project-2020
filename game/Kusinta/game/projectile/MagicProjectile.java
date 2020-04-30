package projectile;

import java.awt.Image;

import automaton.Automaton;
import automaton.Direction;
import environnement.Element;
import game.Coord;
import opponent.FlyingOpponent;

public class MagicProjectile extends Projectile {
	
	public static final int SIZE = (int) (1.5 * Element.SIZE);
	
	Image[] images;
	
	int moving;
	int DIMENSION;

	int m_height;
	int m_width;
	
	public MagicProjectile(Automaton projectileAutomaton, int x, int y, double angle, FlyingOpponent shooter, Direction direction) throws Exception {
		super(projectileAutomaton, x, y, angle, shooter, shooter.getModel(), direction);
		
		images = new Image[13];
		for (int i = 0; i < 13; i++) {
			images[i] = loadImage("resources/oppenent/jin/Magic_Attack"+(i+1)+".png");
		}
		
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

		m_dead_time = 0;

		moving = 0;
		
	}
	
	

}
