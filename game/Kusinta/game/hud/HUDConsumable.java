package hud;

import java.awt.Graphics;
import java.awt.Image;

import entityFactory.Factory.Type;
import entityFactory.ImageLoader;
import game.Game;
import player.Player;

public class HUDConsumable {
	int m_x, m_y, m_width, m_height;
	Player m_player;
	Image SmallPot;
	Image BigPot;
	Image Key;
	int Pot_x, Pot_y, Pot_w, Pot_h;

	long updateElpased;
	long statsElapsed;

	int nbSPot, nbBPot;

	public HUDConsumable(int x, int y, int w, int h, Player p) throws Exception {
		m_x = x;
		m_y = y;
		m_width = w;
		m_height = h;
		m_player = p;
		SmallPot = ImageLoader.loadImage("resources/Equipment/Potion/Green Potion.png");
		BigPot = ImageLoader.loadImage("resources/Equipment/Potion/Red Potion.png");
		Pot_x = x + w / 4;
		Pot_y = y;
		Pot_w = w / 5;
		Pot_h = h / 2;

		updateConsommable();
	}

	public void updateConsommable() {
		nbSPot = m_player.getSmallConsumables().size();
		nbBPot = m_player.getBigConsumables().size();
		if (m_player.getKey()) {
			Key = Game.m_factory.getImage(Type.NormalKey)[0];
		} else if (m_player.getBossKey()) {
			Key = Game.m_factory.getImage(Type.BossKey)[0];
		}
	}

	public void tick(long elapsed) {
		statsElapsed += elapsed;
		if (statsElapsed > 500) {
			statsElapsed = 0;
			updateConsommable();
		}
	}

	public void paint(Graphics g) {
		g.drawImage(SmallPot, Pot_x, Pot_y, Pot_w, Pot_h, null);
		g.drawString("x" + nbSPot, Pot_x + Pot_w, Pot_y + (3 * Pot_h) / 4);
		g.drawImage(BigPot, Pot_x, (int) (Pot_y + Pot_h), Pot_w, Pot_h, null);
		g.drawString("x" + nbBPot, Pot_x + Pot_w, Pot_y + (7 * Pot_h) / 4);
		if (Key != null) {
			g.drawImage(Key, Pot_x + Pot_w * 2, Pot_h, Pot_w, Pot_h, null);
		}

	}
}
