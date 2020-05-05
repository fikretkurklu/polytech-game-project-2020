package hud;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import player.Player;

public class HUD {
	
	Player m_player;
	int m_x, m_y, m_width, m_height;
	HUDFont m_font;
	HUDStats m_stats;

	public HUD(int x, int y, int w, int h, Player p) throws Exception {
		m_x = x;
		m_y = y;
		m_width = w;
		m_height = h;
		m_player = p;
		m_font = new HUDFont(0, 0, w/2, h, p);
		m_stats = new HUDStats(m_font.m_width, 0, w - m_font.m_width, h, p);
	}

	public void tick(long elapsed) {
		m_font.tick(elapsed);
		m_stats.tick(elapsed);
	}

	public void paint(Graphics g) {
		Graphics gp = g.create(m_x, m_y, m_width, m_height);
		m_font.paint(gp);
		m_stats.paint(gp);
		gp.dispose();
	}

}
