package hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import equipment.BigHealthPotion;
import equipment.SmallHealthPotion;
import game.ImageLoader;
import player.Player;
import player.Character.CurrentStat;

public class HUDConsumable {
	private Color BG_COLOR = Color.DARK_GRAY;
	private final String BG = "resources/HUD/Menu1.png";
	int m_x, m_y, m_width, m_height;
	Player m_player;
	Image bg;
	Image SmallPot;
	Image BigPot;
	int Pot_x, Pot_y, Pot_w, Pot_h;
	
	long updateElpased;
	long statsElapsed;

	public HUDConsumable(int x, int y, int w, int h, Player p) throws Exception {
		m_x = x;
		m_y = y;
		m_width = w;
		m_height = h;
		m_player = p;
		bg = ImageLoader.loadImage(BG, m_width, m_height);
		SmallPot = new SmallHealthPotion().getImage();
		BigPot = new BigHealthPotion().getImage();
		Pot_x = 0;
		Pot_y = 0;
		Pot_w = w/4;
		Pot_h = h/2;
		
		updateConsommable();
	}

	public void updateConsommable() {

	}

	public void tick(long elapsed) {
		statsElapsed += elapsed;
		if (statsElapsed > 500) {
			statsElapsed = 0;
			updateConsommable();
		}
	}

	public void paint(Graphics g) {
		g.drawImage(bg, m_x, m_y, null);
		g.setColor(BG_COLOR);
		g.drawImage(SmallPot, Pot_x, Pot_y, Pot_w, Pot_h, null);
		g.drawImage(BigPot, Pot_x + 2 * Pot_w, Pot_y, Pot_w, Pot_h, null);
	}
}
