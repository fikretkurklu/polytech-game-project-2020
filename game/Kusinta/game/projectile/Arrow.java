package projectile;

import java.awt.Color;

import java.awt.Graphics;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import environnement.Element;
import player.Player;

public class Arrow extends Projectile{
	
	public static final int SIZE = (int) (1.5 * Element.SIZE);
	static final int SPEED = 3;
	int DIMENSION;
	
	int m_height;
	int m_width;

	public Arrow(Automaton arrowAutomaton, int x, int y, double angle, Player player) throws Exception {
		super(arrowAutomaton, x, y, angle, player, player.getModel());
		loadImage("resources/Player/spriteArrow.png");
		
		DIMENSION = SIZE / (image.getHeight(null));
		
		float ratio = (float) (image.getWidth(null) * 4) / (float) (5 * image.getHeight(null));
		
		m_height = DIMENSION * image.getHeight(null);
		m_width =  (int) (ratio * image.getWidth(null));
	}

	@Override
	public boolean explode() {
		System.out.println("coucou");
		m_shooter.getProjectiles().remove(this);
		return true;
	}

	@Override
	public boolean move(Direction dir) {
        m_coord.setX(m_coord.X()  + SPEED);
        
		return true;
	}
	
	public void paint(Graphics g) {
		if (image != null) {
			int w = DIMENSION * m_width;
			int h = m_height;
			if (m_direction.toString().equals("E")) {
				g.drawImage(image, m_coord.X() - (w / 2), m_coord.Y()- h/2, w, h, null);
			} else {
				g.drawImage(image, m_coord.X() + (w / 2), m_coord.Y() - h/2, -w, h, null);
			}
		}
	}
	
	
	@Override
	public boolean cell(Direction dir, Category cat) {
		boolean c = !(m_model.m_room.isBlocked(m_coord.X(), m_coord.Y()));
		if (m_State == HIT_STATE) {
			System.out.println(c);
			return !c;
		}
		if (!c) {
			m_State = HIT_STATE;
		}
		return c;
	}


}
