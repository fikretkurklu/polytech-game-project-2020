package game;

import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


import automaton.Automaton;
import automaton.AutomatonLibrary;
import automaton.Direction;
import game.graphics.View;
import player.Player;
import room.Room;
import underworld.Underworld;

public class Model {
	
	public final static int VILLAGE = 0;
	public final static int ROOM = 1;
	public final static int UNDERWORLD = 2;

	int m_x, m_y, m_width, m_height, x_decalage, y_decalage;
	public Coord m_mouseCoord;

	Player m_player;
	View m_view;
	public int mode;
	private AutomatonLibrary m_AL;
	public Automaton playerAutomaton;
	public Automaton arrowAutomaton;
	public Room m_room;
	public Underworld m_underworld;
//	Opponent[] m_opponents;

	public Model(View view) throws Exception {
		m_view = view;
		m_width = m_view.getWidth();
		m_height = m_view.getHeight();
		m_AL = new AutomatonLibrary();
		playerAutomaton = m_AL.getAutomaton("Player_donjon");
		arrowAutomaton = m_AL.getAutomaton("Fleche");
		setRoomEnv();
		setUnderworldEnv();
		m_player = new Player(playerAutomaton, m_room.getStartCoord().X(), m_room.getStartCoord().Y(),
				new Direction("E"), this);
		setCenterScreen();
	}
	
	public void setRoomEnv() throws Exception {
			m_room = new Room(m_AL, m_width, m_height);
			mode = ROOM;
	}
	
	public void setUnderworldEnv() throws Exception {
		m_underworld = new Underworld(m_AL, m_width, m_height);
		mode = UNDERWORLD;
}

	public void setCenterScreen() {
		x_decalage = m_width / 2 - m_player.getCoord().X();
		y_decalage = m_height / 2 - m_player.getCoord().Y();

	}

	public void tick(long elapsed) {
		switch(mode) {
		case ROOM:
			m_player.tick(elapsed);
			m_room.tick(elapsed);
			break;
		case UNDERWORLD:
			m_underworld.tick(elapsed);
		}	
	}

	public void paint(Graphics g, int width, int height) {
		m_width = width;
		m_height = height;
		switch(mode) {
		case ROOM:
			setCenterScreen();
			Graphics gp = g.create(m_x + x_decalage, m_y + y_decalage, m_width - x_decalage, m_height - y_decalage);
			m_room.paint(gp, width, height);
			m_player.paint(gp);
			gp.dispose();
		case UNDERWORLD:
			setCenterScreen();
			Graphics gu = g.create(m_x + x_decalage, m_y + y_decalage, m_width - x_decalage, m_height - y_decalage);
			m_underworld.paint(gu, width, height);
			gu.dispose();
		}
	}

	public void setMouseCoord(Coord mouseCoord) {
		m_mouseCoord = mouseCoord;
	}

	/*
	 * Loading a sprite
	 */
	public BufferedImage[] loadSprite(String filename, int nrows, int ncols) throws IOException {
		File imageFile = new File(filename);
		if (imageFile.exists()) {
			BufferedImage image = ImageIO.read(imageFile);
			int width = image.getWidth(null) / ncols;
			int height = image.getHeight(null) / nrows;

			BufferedImage[] images = new BufferedImage[nrows * ncols];
			for (int i = 0; i < nrows; i++) {
				for (int j = 0; j < ncols; j++) {
					int x = j * width;
					int y = i * height;
					images[(i * ncols) + j] = image.getSubimage(x, y, width, height);
				}
			}
			return images;
		}
		return null;
	}

}
