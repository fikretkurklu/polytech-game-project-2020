package underworld;

import java.awt.Image;
import automaton.Automaton;
import automaton.Direction;
import game.Coord;
import room.Element;

public class Cloud extends Element{
	
	public static final int SIZE = 86;
	
	int m_width, m_height;
	String imagePath;
	PlayerSoul player;
	boolean outScreen; // Indique si le nuage n'est plus visible à l'écran

	public Cloud(PlayerSoul player, Automaton automaton, Coord coord) {
		super(false, true, coord, automaton);
		m_width = 2 * SIZE;
		m_height = 2 * SIZE;
		this.player = player;
		outScreen = false;
		try {
			loadImage(imagePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Effectue le changement de booléen nécessaire lorsque le joueur se trouve derrière un nuage
	private void hide() {
		int width = getCoord().X() + m_width;
		int height = getCoord().Y() + m_height;
		int playerX = player.getX();
		int playerY = player.getY();
		if (! player.hidden) {
			if ((playerX >= getCoord().X()) && (playerX <= width) && (playerY >= getCoord().Y()) && (playerY <= height)) {
				player.hidden = true;
			}
		} else {
			if (! ((playerX >= getCoord().X()) && (playerX <= width) && (playerY >= getCoord().Y()) && (playerY <= height))) {
				player.hidden = false;
			}
		}
	}
	
	@Override
	public boolean move(Direction dir) {
		if (getCoord().X() <= 0) {
			outScreen = true;
			return true;
		}
		hide();
		getCoord().translateX(-20);
		return true;
	}
}
