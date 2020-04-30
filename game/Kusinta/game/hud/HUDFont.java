package hud;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import player.Player;
import player.Character.CurrentStat;

public class HUDFont {
	private Color BG_COLOR = Color.DARK_GRAY;
	private Color FG_COLOR = Color.GREEN;
	private final String BG = "resources/HUD/BG.png";
	int m_x, m_y, m_width, m_height;
	Player m_player;
	Image bg;
	HUDCoin hudCoin;
	Rectangle bgRect;
	Rectangle fgRect;
	long updateElpased;
	
	public HUDFont(int x, int y, int w, int h, Player p) {
		m_x = x;
		m_y = y;
		m_width = w;
		m_height = h;
		m_player = p;
		bg = HUD.loadImage(BG, w, h);
		hudCoin = new HUDCoin(w/5, h/2, w/2, h/2, p);
		bgRect = new Rectangle(w/5, h /8, w/3*2, h/3);
		updateBar();
		
	}
	
	public void updateBar() {
		fgRect = new Rectangle(bgRect.x, bgRect.y, (int)((float)m_player.getStat(CurrentStat.Life) / m_player.getStat(CurrentStat.MaxLife) * bgRect.width), bgRect.height);
	}
	public void tick(long elapsed) {
		hudCoin.tick(elapsed);
		updateElpased += elapsed;
		if (updateElpased > 200) {
			updateElpased = 0;
			updateBar();
		}
	}
	
	public void paint(Graphics g) {
		g.drawImage(bg, m_x, m_y, null);
		g.setColor(BG_COLOR);
		g.fillRect(bgRect.x, bgRect.y, bgRect.width, bgRect.height);
		g.setColor(FG_COLOR);
		g.fillRect(fgRect.x, fgRect.y, fgRect.width, fgRect.height);
		hudCoin.paint(g);
	}
}
