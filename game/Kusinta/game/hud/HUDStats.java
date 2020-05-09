package hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import game.ImageLoader;
import player.Player;
import player.Character.CurrentStat;

public class HUDStats {
	private Color BG_COLOR = Color.DARK_GRAY;
	private final String BG = "resources/HUD/Menu1.png";
	int m_x, m_y, m_width, m_height;
	Player m_player;
	Image bg;
	long updateElpased;
	Font font;

	String[] statsDrawned;
	long statsElapsed;

	public HUDStats(int x, int y, int w, int h, Player p) throws Exception {
		m_x = x;
		m_y = y;
		m_width = w;
		m_height = h;
		m_player = p;
		bg = ImageLoader.loadImage(BG);
		font = new Font("Georgia", Font.PLAIN, 9);
		statsDrawned = new String[CurrentStat.values().length - 1];
		updateStats();
	}

	public void updateStats() {
		int i = 0;
		for (CurrentStat stat : CurrentStat.values()) {
			if (stat != CurrentStat.Life) {
				statsDrawned[i] = stat.name() + " : " + m_player.getStat(stat);
				i++;
			}
		}

	}

	public void tick(long elapsed) {
		statsElapsed += elapsed;
		if (statsElapsed > 500) {
			statsElapsed = 0;
			updateStats();
		}
	}

	public void paint(Graphics g) {
		g.drawImage(bg, m_x, m_y, m_width, m_height, null);
		g.setColor(BG_COLOR);
		int i = 0;
		for (String l : statsDrawned) {
			g.drawString(l, m_x + m_width / 2 - l.length() * font.getSize() / 2, m_y + (i + 2) * font.getSize() * 2);
			i++;
		}
	}
}
