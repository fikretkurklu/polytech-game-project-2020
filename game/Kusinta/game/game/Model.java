package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import automaton.Direction;
import entityFactory.Factory;
import entityFactory.Factory.Type;
import environnement.Element;
import game.graphics.View;
import game.roomGenerator.AutomaticRoomGenerator;
import opponent.*;
import hud.HUD;
import player.Player;
import room.Room;
import underworld.Underworld;
import village.Village;
import player.Character;
import player.Character.CurrentStat;

public class Model {

	public static enum mode {
		VILLAGE, ROOM, UNDERWORLD, GAMEOVER
	};

	public static int difficultyLevel;

	int m_x, m_y, m_width, m_height, x_decalage, y_decalage;
	public Coord m_mouseCoord;

	public Character m_player;
	View m_view;

	public mode actualMode;

	public AutomaticRoomGenerator m_roomGenerator;

	public boolean qPressed, zPressed, dPressed, espPressed, aPressed, ePressed, vPressed, sPressed, xPressed, cPressed;

	public Room m_room;
	public Underworld m_underworld;
	public Village m_village;

	boolean set = false;
	LinkedList<Opponent> m_opponents;
	private LinkedList<Opponent> m_opponentsToDelete;
	LinkedList<Coin> m_coins;

	public HUD m_hud;

	public Key m_key;
	public BossKey m_bossKey;
	public Coin m_coin;
	float diametre;
	Factory m_factory;

	public Model(View view, int w, int h, Factory factory) throws Exception {
		m_view = view;
		m_width = w;
		m_height = h;
		m_factory = factory;
		m_opponentsToDelete = new LinkedList<Opponent>();

		start();

		m_opponents = new LinkedList<Opponent>();
		m_coins = new LinkedList<Coin>();

		difficultyLevel = 1;
		opponentCreator();

		switchEnv(mode.VILLAGE);
		setCenterScreenPlayer();
		diametre = 0;

		m_key = null;
		m_bossKey = null;
	}

	public void switchToNextRoom() throws Exception {
		this.m_roomGenerator.AutomaticGeneration();
		m_room = new Room(m_width, m_height);

		resetPlayer();

		m_opponents = new LinkedList<Opponent>();
		m_coins = new LinkedList<Coin>();

		opponentCreator();

		difficultyLevel++;
	}

	public void resetPlayer() {
		this.m_player.setLife(m_player.m_currentStatMap.get(CurrentStat.MaxLife));
		this.m_player.setCoord(m_room.getStartCoord());
		((Player) this.m_player).reset();
		this.m_player.resetAnim();
	}

	public void toDongeon() throws Exception {
		this.m_roomGenerator.AutomaticGeneration();
		m_room = new Room(m_width, m_height);
		
		m_opponents = new LinkedList<Opponent>();
		m_coins = new LinkedList<Coin>();

		resetPlayer();

		opponentCreator();

		difficultyLevel = 1;
	}

	public void switchEnv(mode m) {

		qPressed = false;
		zPressed = false;
		dPressed = false;
		espPressed = false;
		aPressed = false;
		ePressed = false;
		vPressed = false;
		sPressed = false;
		xPressed = false;
		cPressed = false;
		switch (m) {
		case ROOM:
			switch (actualMode) {
			case UNDERWORLD:
				m_village = null;
				resetPlayer();
				break;
			case VILLAGE:
				try {
					toDongeon();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			default:
				break;
			}
			break;
		case UNDERWORLD:
			m_underworld.reset(40); // Nombre de Ghosts à préciser
			break;
		case VILLAGE:
			m_village.reset();
			break;
		case GAMEOVER:
			break;
		}
		actualMode = m;

	}

	public void start() throws Exception {
		m_roomGenerator = new AutomaticRoomGenerator();
		m_roomGenerator.AutomaticGeneration();
		m_room = new Room(m_width, m_height);
		m_underworld = new Underworld(m_factory, m_width, m_height, this);
		m_player = (Player) m_factory.newEntity(Type.Player, Direction.E, m_room.getStartCoord(), this, 0, null);

		m_village = new Village(m_width, m_height, this, (Player) m_player);
		int HUD_w = m_width * 2 / 5;
		int HUD_h = HUD_w / 6;
		m_hud = new HUD(0, 0, HUD_w, HUD_h, (Player) m_player);
	}

	public void setBossRoom() throws Exception {
		m_roomGenerator.bossRoomGenerator();
		m_room = new Room(m_width, m_height);
		this.m_player.setCoord(m_room.getStartCoord());
		m_opponents = new LinkedList<Opponent>();
		m_coins = new LinkedList<Coin>();
		
		if (m_room.getBossCoord() == null) {
			System.out.println("Wrong coordinate");
		}
		Boss m = (Boss) Game.m_factory.newEntity(Type.Boss, Direction.E, m_room.getBossCoord(), this, 0, null);
		m_opponents.add(m);

		Coord[] coordWO = this.m_room.getWalkingOpponentCoord();
		for (int i = 0; i < coordWO.length; i ++) {
			Coord coord = new Coord(coordWO[i].X() + Element.SIZE / 2, coordWO[i].Y());
			MiniDragon d = (MiniDragon) Game.m_factory.newEntity(Type.SmallDragon, Direction.E, coord, this, 0, null);
			m_opponents.add(d);
		}
	}

	public void setCenterScreenPlayer() {
		switch (actualMode) {
		case ROOM:
			x_decalage = m_width / 2 - m_player.getCoord().X();
			y_decalage = m_height / 2 - m_player.getCoord().Y();
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
		case UNDERWORLD:
			x_decalage = m_width / 2 - m_underworld.m_player.getCoord().X();
			y_decalage = m_height / 2 - m_underworld.m_player.getCoord().Y();
			if (m_x + x_decalage > 0) {
				x_decalage = -m_x;
			} else if (-x_decalage > m_underworld.getWitdh() - m_width) {
				x_decalage = -(m_underworld.getWitdh() - m_width);
			}
			if (m_y + y_decalage > 0) {
				y_decalage = m_y;
			} else if (-y_decalage > m_underworld.getHeight() - m_height) {
				y_decalage = -(m_underworld.getHeight() - m_height);
			}
			break;
		default:
			x_decalage = 0;
			y_decalage = 0;
		}
	}

	public void tick(long elapsed) {
		elapsed = Math.min(10, elapsed);
		switch (actualMode) {
		case ROOM:
			m_player.tick(elapsed);
			if (m_key != null) {
				m_key.tick(elapsed);
			}
			if (m_bossKey != null) {
				m_bossKey.tick(elapsed);
			}

			for (Opponent op : m_opponents) {
				op.tick(elapsed);
			}
			if (m_opponentsToDelete != null) {
				for (Opponent op : m_opponentsToDelete) {
					if (op != null) {
						m_opponents.remove(op);
					}
				}
			}
			for (Coin coin : m_coins) {
				coin.tick(elapsed);
			}
			m_room.tick(elapsed);
			m_hud.tick(elapsed);
			break;
		case UNDERWORLD:
			m_underworld.tick(elapsed);
			break;
		default:
			m_hud.tick(elapsed);
			break;
		}
	}

	public void paint(Graphics g, int width, int height) {
		m_width = width;
		m_height = height;
		setCenterScreenPlayer();
		Graphics gp = g.create(m_x + x_decalage, m_y + y_decalage, m_width - x_decalage, m_height - y_decalage);
		switch (actualMode) {
		case ROOM:
			m_room.paint(gp, width, height, m_x + x_decalage, m_y + y_decalage);
			for (Opponent op : m_opponents) {
				op.paint(gp);
			}

			if (m_key != null) {
				m_key.paint(gp);
			}

			if (m_bossKey != null) {
				m_bossKey.paint(gp);
			}

			for (Coin coin : m_coins) {
				coin.paint(gp);
			}

			m_player.paint(gp);
			if (!m_player.gotpower() && diametre > 0) {
				g.setColor(Color.BLACK);
				int x = (int) (m_player.getCoord().X() + x_decalage - diametre / 2);
				int y = (int) ((m_player.getCoord().Y() + y_decalage) - (diametre / 2));
				g.fillOval(x, y, (int) diametre, (int) diametre);
				if (diametre >= m_view.getWidth() * 1.5) {
					switchEnv(mode.UNDERWORLD);
				}
				diametre *= 1.5;
			}
			m_hud.paint(g);
			break;
		case UNDERWORLD:
			m_underworld.paint(gp, width, height, m_x + x_decalage, m_y + y_decalage);
			break;
		case VILLAGE:
			m_village.paint(g, width, height);
			m_hud.paint(g);
			break;
		default:
			break;
		}

		gp.dispose();
	}

	public void setMouseCoord(Coord mouseCoord) {
		m_mouseCoord = mouseCoord;
		m_mouseCoord.translate(-x_decalage, -y_decalage);
	}

	public int getXDecalage() {
		return x_decalage;
	}

	public int getYDecalage() {
		return y_decalage;
	}

	public Character getPlayer() {
		return m_player;
	}

	public LinkedList<Opponent> getOpponent() {
		return m_opponents;
	}

	public void addCoin(Coin coin) {
		m_coins.add(coin);
	}

	public void removeCoin(Coin coin) {
		m_coins.remove(coin);
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

	public void setKey(Key key) {
		m_key = key;
	}

	public void setBossKey(BossKey key) {
		m_bossKey = key;
	}

	public void opponentCreator() {
		try {
			Coord[] coordFO = this.m_room.getFlyingOpponentCoord();
			for (int i = 0; i < coordFO.length; i++) {
				Jin fo = (Jin) Game.m_factory.newEntity(Type.Jin, Direction.E, coordFO[i], this, 0, null);
				m_opponents.add(fo);
			}
			Coord[] coordWO = this.m_room.getWalkingOpponentCoord();
			for (int i = 0; i < coordWO.length; i++) {
				int WOtype = (int) (Math.random() * 2) + 1;
				switch (WOtype) {
				case 1:
					Demon d = (Demon) Game.m_factory.newEntity(Type.Demon, Direction.E, coordWO[i], this, 0, null);
					m_opponents.add(d);
					break;
				case 2:
					Medusa m = (Medusa) Game.m_factory.newEntity(Type.Medusa, Direction.E, coordWO[i], this, 0, null);
					m_opponents.add(m);
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Error while creating oppenant");
		}

		int randomKey = (int) (Math.random() * m_opponents.size());
		int randomBossKey = (int) (Math.random() * m_opponents.size());
		while (randomBossKey == randomKey) {
			randomBossKey = (int) (Math.random() * m_opponents.size());
		}
		m_opponents.get(randomKey).setKey(true);
		m_opponents.get(randomBossKey).setBossKey(true);
	}

	public void setPressed(int keyCode, boolean pressed) {
		switch (keyCode) {
		case Controller.K_Q:
			qPressed = pressed;
			break;
		case Controller.K_Z:
			zPressed = pressed;
			break;
		case Controller.K_D:
			dPressed = pressed;
			break;
		case Controller.K_SPACE:
			espPressed = pressed;
			break;
		case Controller.K_A:
			aPressed = pressed;
			break;
		case Controller.K_E:
			ePressed = pressed;
			break;
		case Controller.K_V:
			vPressed = pressed;
			break;
		case Controller.K_S:
			sPressed = pressed;
			break;
		case Controller.K_X:
			xPressed = pressed;
			break;
		case Controller.K_C:
			cPressed = pressed;
			break;
		}

	}

	public LinkedList<Opponent> getM_opponentsToDelete() {
		return m_opponentsToDelete;
	}

	public void setM_opponentsToDelete(LinkedList<Opponent> m_opponentsToDelete) {
		this.m_opponentsToDelete = m_opponentsToDelete;
	}
}
