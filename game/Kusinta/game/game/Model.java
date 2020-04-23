package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;


import automata.ast.AST;
import automata.parser.AutomataParser;
import automaton.Automaton;
import automaton.Direction;
import automaton.Interpretor;
import game.graphics.View;
import player.Player;
import room.Room;
import underworld.Underworld;

public class Model {

	int m_x, m_y, m_width, m_height, x_decalage, y_decalage;
	public Coord m_mouseCoord;

	public Room m_room;
	Coord centerScreen; // position du personnage plus tard;
	public Underworld m_map;
	Player m_player;
	View m_view;
//	Opponent[] m_opponents;

	public Model() throws IOException {
//		m_room = new Room();
//		centerScreen = m_room.getStartCoord();
		m_map = new Underworld();
		centerScreen = m_map.getStartCoord();
		setCenterScreen();
		m_view = null;

		AST ast;
		Automaton playerAutomaton = null;
		List<automaton.Automaton> bots;
		try {
			ast = (AST) AutomataParser
					.from_file("resources/gal/automata.gal");
			Interpretor interpret = new Interpretor();
			bots = (List<Automaton>) ast.accept(interpret);
			 playerAutomaton = bots.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		m_player = new Player(playerAutomaton, m_room.getStartCoord().X(), m_room.getStartCoord().Y(), new Direction("E"), this);
		setCenterScreen();

	}

	public void setView(View view) {
		m_view = view;
	}

	public void setCenterScreen() {
		x_decalage = m_width / 2 - m_player.getCoord().X();
		y_decalage = m_height / 2 - m_player.getCoord().Y();

	}

	public void tick(long elapsed) {
		m_player.tick(elapsed);
		m_room.tick(elapsed);
	}

	public void paint(Graphics g, int width, int height) {
		m_width = width;
		m_height = height;
		setCenterScreen();
		Graphics gp = g.create(m_x + x_decalage, m_y + y_decalage, m_width - x_decalage, m_height - y_decalage);
		m_room.paint(gp);
		m_player.paint(gp);
		gp.dispose();
	}

	public void setMouseCoord(Coord mouseCoord) {
		m_mouseCoord = mouseCoord;
	}

	/*
	 * Loading a sprite
	 */
	public static BufferedImage[] loadSprite(String filename, int nrows, int ncols) throws IOException {
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
