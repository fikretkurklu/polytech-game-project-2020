package hud;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import player.Player;

public class HUD {
	private final String BG = "resources/HUD/BG.png";
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

	public static Image[] loadSprite(String filename, int nrows, int ncols) throws IOException {
		File imageFile = new File(filename);
		if (imageFile.exists()) {
			BufferedImage image = ImageIO.read(imageFile);
			int width = image.getWidth(null) / ncols;
			int height = image.getHeight(null) / nrows;

			Image[] images = new Image[nrows * ncols];
			for (int i = 0; i < nrows; i++) {
				for (int j = 0; j < ncols; j++) {
					int x = j * width;
					int y = i * height;
					images[(i * ncols) + j] = image.getSubimage(x, y, width, height);
				}
			}
			return images;
		}
		return null;
	}

	public static Image loadImage(String filename, int w, int h) {
		try {
			File imageFile = new File(filename);
			Image image;
			image = ImageIO.read(imageFile);
			image = image.getScaledInstance(w, h, 0);
			return image;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
