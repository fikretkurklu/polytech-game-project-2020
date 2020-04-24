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
import automaton.AutomatonLibrary;
import automaton.Direction;
import automaton.Interpretor;
import environnement.Env;
import game.graphics.View;
import player.Player;
import room.Room;
import underworld.PlayerSoul;
import underworld.Underworld;

public class Model {

	int m_x, m_y, m_width, m_height, x_decalage, y_decalage;
	public Coord m_mouseCoord;

	public Room m_room;
	Coord centerScreen; // position du personnage plus tard;
	PlayerSoul m_player;
	View m_view;
//	Opponent[] m_opponents;
	Env m_env;
	public Model() throws IOException {
//		m_room = new Room();
//		centerScreen = m_room.getStartCoord();
		AutomatonLibrary m_al = new AutomatonLibrary();
		m_env = new Underworld(m_al, m_width, m_height);
		centerScreen = ((Underworld) m_env).getStartCoord();
//		setCenterScreen();
		m_view = null;

		AST ast;
		Automaton playerAutomaton = null;
		try {
			playerAutomaton = m_al.getAutomaton("PlayerSoul");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
/*		List<automaton.Automaton> bots;
		try {
			ast = (AST) AutomataParser
					.from_file("resources/gal/automata.gal");
			Interpretor interpret = new Interpretor();
			bots = (List<Automaton>) ast.accept(interpret);
			 playerAutomaton = bots.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
//		m_player = new PlayerSoul(playerAutomaton, ((Underworld) m_env).getStartCoord().X(), ((Underworld) m_env).getStartCoord().Y(), new Direction("E"), this);
	}

	public void setView(View view) {
		m_view = view;
	}

	public void setCenterScreen() {
		x_decalage = m_width / 2 - m_player.getCoord().X();
		y_decalage = m_height / 2 - m_player.getCoord().Y();

	}

	public void tick(long elapsed) {
//		m_player.tick(elapsed);
		m_env.tick(elapsed);
	}

	public void paint(Graphics g, int width, int height) {
		m_width = width;
		m_height = height;
//		setCenterScreen();
		Graphics gp = g.create(m_x + x_decalage, m_y + y_decalage, m_width - x_decalage, m_height - y_decalage);
		m_env.paint(gp, width, height);
//		m_room.paint(gp);
//		m_player.paint(gp);
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
