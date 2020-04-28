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
import underworld.PlayerSoul;
import underworld.Underworld;
import village.Village;
import player.Character;

public class Model {

	public final static int VILLAGE = 0;
	public final static int ROOM = 1;
	public final static int UNDERWORLD = 2;

	int m_x, m_y, m_width, m_height, x_decalage, y_decalage;
	public Coord m_mouseCoord;

	Character m_player;
	Character m_playerSave;
	View m_view;
	public int mode;
	private AutomatonLibrary m_AL;
	public Automaton playerAutomaton;
	public Automaton arrowAutomaton;
	public Automaton playerSoulAutomaton;
	public Room m_room;
	public Underworld m_underworld;
	public Village m_village;
	boolean set = false;
//	Opponent[] m_opponents;

	public Model(View view, int w, int h) throws Exception {
		m_view = view;
		m_width = w;
		m_height = h;
		m_AL = new AutomatonLibrary();
		playerAutomaton = m_AL.getAutomaton("Player_donjon");
		playerSoulAutomaton = m_AL.getAutomaton("PlayerSoul");
		arrowAutomaton = m_AL.getAutomaton("Fleche");
		start();
		m_player = new Player(playerAutomaton, m_room.getStartCoord().X(), m_room.getStartCoord().Y(),
				new Direction("E"), this);
		setCenterScreenPlayer();
		setVillageEnv();
	}

	private void switchPlayer() {
		Character tmp = m_player;
		m_player = m_playerSave;
		m_playerSave = tmp;
	}

	public void start() throws Exception {
		m_room = new Room(m_AL, m_width, m_height);
		m_underworld = new Underworld(m_AL, m_width, m_height);
		m_playerSave = new PlayerSoul(playerSoulAutomaton, m_underworld.getStartCoord().X(),
				m_underworld.getStartCoord().Y(), new Direction("E"), this);
		mode = ROOM;
	}

	public void setRoomEnv() throws Exception {
		if (!(m_player instanceof Player))
			switchPlayer();
		mode = ROOM;
	}

	public void setUnderworldEnv() throws Exception {
		if (!(m_player instanceof PlayerSoul))
			switchPlayer();
		mode = UNDERWORLD;
	}

	public void setVillageEnv() throws Exception {
		m_village = new Village(m_width, m_height, this);
		mode = VILLAGE;
	}

	public void setCenterScreenPlayer() {
		x_decalage = m_width / 2 - m_player.getCoord().X();
		y_decalage = m_height / 2 - m_player.getCoord().Y();

	}

	public void tick(long elapsed) {
		m_player.tick(elapsed);
		switch (mode) {
		case ROOM:
			m_room.tick(elapsed);
			break;
		case UNDERWORLD:
			m_underworld.tick(elapsed);
			break;
		}
	}

	public void paint(Graphics g, int width, int height) {
		m_width = width;
		m_height = height;
		setCenterScreenPlayer();
		Graphics gp = g.create(m_x + x_decalage, m_y + y_decalage, m_width - x_decalage, m_height - y_decalage);
		switch (mode) {
		case ROOM:
			m_room.paint(gp, width, height);
			m_player.paint(gp);
			break;
		case UNDERWORLD:
			m_underworld.paint(gp, width, height);
			break;
		case VILLAGE:
			m_village.paint(g, width, height);
			break;
		}

		gp.dispose();
	}

	public void setMouseCoord(Coord mouseCoord) {
		m_mouseCoord = mouseCoord;
	}

	public void setPressed(int keyChar, boolean b) {
		m_player.setPressed(keyChar, b);
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

	public int getXDecalage() {
		return x_decalage;
	}

	public int getYDecalage() {
		return y_decalage;
	}
	
	
}
