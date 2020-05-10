package hud;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import entityFactory.Factory.Type;
import entityFactory.ImageLoader;
import game.Game;
import game.Model;
import game.Model.mode;
import player.Player;

public class HUDConsumable {
	int m_x, m_y, m_width, m_height;
	Model m_model;
	Player m_player;
	Image SmallPot;
	Image BigPot;
	Image Key;
	Image BossKey;
	int Pot_x, Pot_y, Pot_w, Pot_h;

	long updateElpased;
	long statsElapsed;

	int nbSPot, nbBPot;

	public HUDConsumable(int x, int y, int w, int h, Player p, Model model) throws Exception {
		m_model = model;
		m_x = x;
		m_y = y;
		m_width = w;
		m_height = h;
		m_player = p;
		SmallPot = ImageLoader.loadImage("resources/Equipment/Potion/Green Potion.png");
		BigPot = ImageLoader.loadImage("resources/Equipment/Potion/Red Potion.png");
		Pot_x = m_x;
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
		} else {
			Key = null;
		}
		if (m_player.getBossKey()) {
			BossKey = Game.m_factory.getImage(Type.BossKey)[0];
		} else {
			BossKey = null;
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
		if (m_model.actualMode == mode.VILLAGE) {
			g.setColor(Color.DARK_GRAY);
		} else {
			g.setColor(Color.LIGHT_GRAY);
		}
		g.drawImage(SmallPot, Pot_x, Pot_y, 2 * Pot_w, Pot_h, null);
		g.drawString("x" + nbSPot, Pot_x + 2 * Pot_w, Pot_y + (3 * Pot_h) / 4);
		g.drawImage(BigPot, Pot_x, (int) (Pot_y + Pot_h), 2 * Pot_w, Pot_h, null);
		g.drawString("x" + nbBPot, Pot_x + 2 * Pot_w, Pot_y + (7 * Pot_h) / 4);
		if (Key != null) {
			g.drawImage(Key, Pot_x + Pot_w * 3, 0, Pot_w, Pot_h, null);
		} 
		if ( BossKey != null) {
			g.drawImage(BossKey, Pot_x + Pot_w * 3, Pot_h, Pot_w, Pot_h, null);
		}
	}
}
