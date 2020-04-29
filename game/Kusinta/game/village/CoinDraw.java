package village;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.Coord;
import player.Player;

public class CoinDraw {

		int m_x, m_y, m_width, m_height;
		Image coinIco;
		Player m_player;
		private String COIN_ICO = "resources/Village/HUD/Coin.png";
		private Coord moneyC;
		Font font;
		
		public CoinDraw(int x, int y, int w, int h, Player p) {
			m_x = x;
			m_y = y;
			m_width = w;
			m_height = h;
			m_player = p;
			try {
				File f = new File(COIN_ICO);
				coinIco = ImageIO.read(f);
				coinIco = coinIco.getScaledInstance(m_width / 3, m_height / 2, java.awt.Image.SCALE_SMOOTH);
				
			} catch (IOException e) {
				coinIco = null;
				e.printStackTrace();
			}
			font = new Font("Georgia", Font.BOLD, 20);
			moneyC = new Coord(m_x + coinIco.getHeight(null) * 2,  m_y + m_height / 2 - font.getSize());
					
		}
		
		public void paint(Graphics g) {
			g.setFont(font);
			g.drawImage(coinIco, m_x, m_y, null);
			g.drawString(String.valueOf(m_player.getMoney()), moneyC.X(), moneyC.Y());
		}
		
		
		public void resized(double ratio_w, double ratio_h) {
			m_width *= ratio_w;
			m_height *= ratio_h;
			m_x *= ratio_w;
			m_y *= ratio_h;
			if (coinIco != null) {
				coinIco = coinIco.getScaledInstance(m_width / 3, m_height / 2, java.awt.Image.SCALE_SMOOTH);
			}
			moneyC = new Coord(m_x + coinIco.getHeight(null) * 2,  m_y + m_height / 2 - font.getSize());
		}
}
