package hud;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import game.Coord;
import player.Player;

public class HUDCoin {

	int m_x, m_y, m_width, m_height;
	Image coinIco;
	Player m_player;
	private String COIN_ICO_SPRITE = "resources/HUD/AnimatedCoin.png";
	private Coord moneyC;
	Font font;
	protected int m_imageIndex;
	protected long m_imageElapsed;
	protected Image[] m_images;

	public HUDCoin(int x, int y, int w, int h, Player p) {
		m_x = x;
		m_y = y;
		m_width = w;
		m_height = h;
		m_player = p;
		try {
			m_images = HUD.loadSprite(COIN_ICO_SPRITE, 1, 6);
			int size = m_width / 4;
			for (int i = 0; i< m_images.length; i++) {
				m_images[i] = m_images[i].getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		coinIco = m_images[0];
		font = new Font("Georgia", Font.BOLD, 20);
		moneyC = new Coord(m_x + coinIco.getHeight(null) * 3 / 2, m_y + m_height / 3 * 2 - font.getSize() / 2);

	}

	public void paint(Graphics g) {
		g.setFont(font);
		g.drawImage(coinIco, m_x, m_y, null);
		g.drawString(String.valueOf(m_player.getMoney()), moneyC.X(), moneyC.Y());
	}

	public void tick(long elapsed) {
		m_imageElapsed += elapsed;
		if (m_imageElapsed > 200) {
			m_imageElapsed = 0;
			m_imageIndex = (m_imageIndex + 1) % m_images.length;
			coinIco = m_images[m_imageIndex];
		}
	}
	
	
}
