package projectile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import environnement.Element;
import player.Player;

public class Arrow extends Projectile{
	
	public static final int SIZE = (int) (1.5 * Element.SIZE);
	static final int SPEED = 1;
	int DIMENSION;
	
	int m_height;
	int m_width;

	public Arrow(Automaton arrowAutomaton, int x, int y, double angle, Player player) throws IOException {
		super(arrowAutomaton, x, y, angle, player);
		try {
			loadImage("resources/Player/spriteArrow.png");
			
			DIMENSION = SIZE / (image.getHeight(null));
			
			float ratio = (float) (image.getWidth(null) * 2) / (float) (5 * image.getHeight(null));
			
			m_height = DIMENSION * image.getHeight(null);
			m_width = (int) (m_height * ratio);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean explode() {
		System.out.println("coucou");
		m_shooter.m_arrows.remove(this);
		return true;
	}

	@Override
	public boolean move(Direction dir) {
		// TODO Auto-generated method stub
        m_coord.setX(m_coord.X()  + SPEED);
        
		return true;
	}
	
	public void paint(Graphics g) {
		if (image != null) {
			int w = DIMENSION * m_width;
			int h = m_height;
			if (m_direction.toString().equals("E")) {
				g.drawImage(image, m_coord.X() - (w / 2), m_coord.Y() - h, w, h, null);
			} else {
				g.drawImage(image, m_coord.X() + (w / 2), m_coord.Y() - h, -w, h, null);
			}
			g.setColor(Color.blue);
			//g.drawPolygon(x_hitBox, y_hitBox, x_hitBox.length);
		}
	}
	
	
	@Override
	public boolean cell(Direction dir, Category cat) {
		// TODO Auto-generated method stub
		
		return !(m_shooter.m_model.m_room.isBlocked(m_coord.X(), m_coord.Y()));
	}


}
