package underworld;

import java.awt.Color;
import java.awt.Graphics;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import game.Coord;
import environnement.Element;

public class Cloud extends Element{
	
	int m_width, m_height;
	int xHitbox[];
	int yHitbox[];
	String imagePath;
	boolean outScreen; // Indique si le nuage n'est plus visible à l'écran
	boolean move; // Booléen qui permet un mouvement de 1 pixel du nuage par seconde
	long timeElapsed = 0;
	PlayerSoul m_player;

	public Cloud(Automaton automaton, Coord coord, PlayerSoul player) {
		super(false, true, coord, automaton);
		m_width = 2 * Element.SIZE;
		m_height = 2 * Element.SIZE;
		m_player = player;
		xHitbox = new int[4];
		yHitbox = new int[4];
		calculateHitbox();
//		xMax = getCoord().X() + m_width;
//		yMax = getCoord().Y() - m_height;
		outScreen = false;
		move = false;
		imagePath = UnderworldParam.cloudImage[0];
		try {
			loadImage(imagePath, m_width, m_height);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void calculateHitbox() {
		
		int x = getCoord().X();
		int y = getCoord().Y();
		
		xHitbox[0] = x;
		xHitbox[1] = x + m_width;
		xHitbox[2] = x + m_width;
		xHitbox[3] = x;
		
		yHitbox[0] = y;
		yHitbox[1] = y;
		yHitbox[2] = y + m_height;
		yHitbox[3] = y + m_height;
		
	}
	
	@Override
	public boolean cell(Direction dir, Category cat) {
		if ((dir.toString().equals("H")) && (cat.toString().equals("O"))) {
			int centerX = m_player.centerX();
			int centerY = m_player.centerY();
			return ((xHitbox[0] <= centerX) && (xHitbox[1] >= centerX) && (yHitbox[0] <= centerY) && (yHitbox[2] >= centerY));
		}
		return false;
	}
	
	@Override
	public boolean pop(Direction dir) {
		return m_player.setVisibility(true);
	}
	
	@Override
	public boolean wizz(Direction dir) {
		return m_player.setVisibility(false);
	}
	
	@Override
	public boolean explode() {
		outScreen = true;
		return true;
	}
	
	
	@Override
	public boolean move(Direction dir) {
		if (move) {
			move = false;
			getCoord().translateX(-1);
			calculateHitbox();
			return true;
		}
		return false;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.blue);
		g.drawPolygon(xHitbox, yHitbox, 4);
	}
	
	public void tick(long elapsed) {
			timeElapsed += elapsed;
		    if (timeElapsed > 10) {
		      timeElapsed = 0;
		      move = true;
		    }
	}
}
