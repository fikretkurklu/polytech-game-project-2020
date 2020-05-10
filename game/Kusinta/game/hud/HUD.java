package hud;

import java.awt.Graphics;

import game.Model;
import player.Player;

public class HUD {
	
	Player m_player;
	protected Model m_model;
	int m_x, m_y, m_width, m_height;
	HUDFont m_font;
	HUDStats m_stats;
	HUDConsumable m_conso;

	public HUD(int x, int y, int w, int h, Player p, Model model) throws Exception {
		m_model = model;
		m_x = x;
		m_y = y;
		m_width = w;
		m_height = h;
		m_player = p;
		m_font = new HUDFont(0, 0, w/3, h, p);
		m_stats = new HUDStats(m_font.m_width, 0, w*4/9, h, p);
		m_conso = new HUDConsumable(m_stats.m_x + m_stats.m_width, y, w*2/9, h, p, m_model);
	}

	public void tick(long elapsed) {
		m_font.tick(elapsed);
		m_stats.tick(elapsed);
		m_conso.tick(elapsed);
	}

	public void paint(Graphics g) {
		Graphics gp = g.create(m_x, m_y, m_width, m_height);
		m_font.paint(gp);
		m_stats.paint(gp);
		m_conso.paint(gp);
		gp.dispose();
	}

}
