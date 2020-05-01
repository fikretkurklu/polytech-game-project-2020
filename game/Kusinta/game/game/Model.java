package game;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import automaton.Automaton;
import automaton.AutomatonLibrary;
import automaton.Direction;
import game.graphics.View;
import opponent.WalkingOpponent;
import opponent.FlyingOpponent;
import opponent.Opponent;
import hud.HUD;
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

	public Character m_player;
	Character m_playerSave;
	View m_view;

	public int mode;

	private AutomatonLibrary m_AL;
	public Automaton playerAutomaton;
	public Automaton arrowAutomaton;
	public Automaton playerSoulAutomaton;
	public Automaton flyingOpponentAutomaton;
	public Automaton walkingOpponentAutomaton;
	public Automaton lureAutomaton;

	public Room m_room;
	public Underworld m_underworld;
	public Village m_village;

	boolean set = false;
	LinkedList<Opponent> m_opponents;

	public HUD m_hud;

	float diametre;

	public Model(View view, int w, int h) throws Exception {
		m_view = view;
		m_width = w;
		m_height = h;
		m_AL = new AutomatonLibrary();
		playerAutomaton = m_AL.getAutomaton("Player_donjon");
		playerSoulAutomaton = m_AL.getAutomaton("PlayerSoul");
		arrowAutomaton = m_AL.getAutomaton("Fleche");
		flyingOpponentAutomaton = m_AL.getAutomaton("FlyingOpponents");
		walkingOpponentAutomaton = m_AL.getAutomaton("WalkingOpponents");
		lureAutomaton = m_AL.getAutomaton("Lure");
		start();
		m_player = new Player(playerAutomaton, m_room.getStartCoord().X(), m_room.getStartCoord().Y(),
				new Direction("E"), this);
		int HUD_w = m_width / 3;
		int HUD_h = m_height / 9;
		m_hud = new HUD(0, 0, HUD_w, HUD_h, (Player) m_player);
		m_opponents = new LinkedList<Opponent>();
		m_opponents.add(new FlyingOpponent(flyingOpponentAutomaton, 600, 1700, new Direction("E"), this, 100, 100, 1000, 100, 5));
		m_opponents.add(new WalkingOpponent(walkingOpponentAutomaton, 800, 1220, new Direction("E"), this, 100, 100, 1000, 100, 5));
		setCenterScreenPlayer();
		setVillageEnv();

		diametre = 0;
	}

	private void switchPlayer() {
		Character tmp = m_player;
		m_player = m_playerSave;
		m_playerSave = tmp;
	}

	public void start() throws Exception {
		m_room = new Room(m_AL, m_width, m_height);
		m_underworld = new Underworld(m_AL, m_width, m_height, this);
		m_playerSave = new PlayerSoul(playerSoulAutomaton, m_underworld.getStartCoord().X(),
				m_underworld.getStartCoord().Y(), new Direction("E"), this);
		mode = ROOM;
	}

	public void setRoomEnv() throws Exception {
		if (!(m_player instanceof Player)) {
			switchPlayer();
			m_player.reset();
		}

		m_village = null;
		mode = ROOM;
	}

	public void setUnderworldEnv() throws Exception {
		if (!(m_player instanceof PlayerSoul))
			switchPlayer();
		mode = UNDERWORLD;
	}

	public void setVillageEnv() throws Exception {
		m_village = new Village(m_width, m_height, this, (Player) m_player);
		mode = VILLAGE;
	}

	public void setCenterScreenPlayer() {
		x_decalage = m_width / 2 - m_player.getCoord().X();
		y_decalage = m_height / 2 - m_player.getCoord().Y();
		switch (mode) {
		case ROOM:
			if (m_x + x_decalage > 0) {
				x_decalage = -m_x;
			} else if (-x_decalage > m_room.getWitdh() - m_width) {
				x_decalage = -(m_room.getWitdh() - m_width);
			}
			if (m_y + y_decalage > 0) {
				y_decalage = m_y;
			} else if (-y_decalage > m_room.getHeight() - m_height) {
				y_decalage = -(m_room.getHeight() - m_height);
			}
			break;
		}
	}

	public void tick(long elapsed) {
		m_player.tick(elapsed);
		for (Opponent op : m_opponents) {
			op.tick(elapsed);
		}
		m_hud.tick(elapsed);
		m_room.tick(elapsed);
		//m_underworld.tick(elapsed);
	}

	public void paint(Graphics g, int width, int height) {

		m_width = width;
		m_height = height;
		setCenterScreenPlayer();
		Graphics gp = g.create(m_x + x_decalage, m_y + y_decalage, m_width - x_decalage, m_height - y_decalage);
		switch (mode) {
		case ROOM:
			m_room.paint(gp, width, height, m_x + x_decalage, m_y + y_decalage);
			for (Opponent op : m_opponents) {
				op.paint(gp);
			}
			m_player.paint(gp);
			if (!m_player.gotpower() && diametre > 0) {
				g.setColor(Color.BLACK);
				int x = (int) (m_player.getCoord().X() + x_decalage - diametre / 2);
				int y = (int) ((m_player.getCoord().Y() + y_decalage) - (diametre / 2));
				g.fillOval(x, y, (int) diametre, (int) diametre);
				if (diametre >= m_view.getWidth() * 1.5) {
					try {
						setUnderworldEnv();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				diametre *= 1.5;
			}
			m_hud.paint(g);
			break;
		case UNDERWORLD:
			m_underworld.paint(gp, width, height);
			break;
		case VILLAGE:
			m_village.paint(g, width, height);
			m_hud.paint(g);
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

	public PlayerSoul getPlayerSoul() {
		return (PlayerSoul) m_player;
	}

	public Player getPlayer() {
		return (Player) m_player;
	}

	public LinkedList<Opponent> getOpponent() {
		return m_opponents;
	}

	public View getView() {
		return m_view;
	}

	public void setDiametre(float r) {
		diametre = r;
	}

	public float getDiametre() {
		return diametre;
	}

}
