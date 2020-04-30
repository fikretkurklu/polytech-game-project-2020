package hud;

import java.awt.Graphics;

import player.Player;

public class HUD {
	Player m_player;
	int m_x, m_y, m_width, m_height;
	CoinDraw coinDraw;
	
	public HUD(int x, int y, int w, int h, Player p) {
		m_x = x;
		m_y = y;
		m_width = w;
		m_height = h;
		m_player = p;
	}
	
	public void tick(long elapsed) {
		
	}
	
	public void paint(Graphics g) {
		
	}

}
