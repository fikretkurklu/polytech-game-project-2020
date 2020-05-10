package village;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public abstract class Button {
	Image bgImg;
	Image fgImg;
	Image bgImgDrawned;
	Image fgImgDrawned;
	Image bigFgImg;
	int m_width, m_height, m_x, m_y;
	int decalageX, decalageY;
	private static double RATIO_BG_FG = 0.8;

	public Button(int x, int y, int w, int h) {
		m_width = w;
		m_height = h;
		decalageX = (int) (0.1 * m_width);
		decalageY = (int) (0.1 * m_height);
		m_x = x;
		m_y = y;

	}

	public void paint(Graphics g) {
		if (bgImgDrawned != null) {
			g.drawImage(bgImgDrawned, m_x, m_y, null);
		}
		if (fgImgDrawned != null) {
			g.drawImage(fgImgDrawned, m_x + (int) (m_width * (1 - RATIO_BG_FG) / 2),
					(int) (m_y + m_height * (1 - RATIO_BG_FG) / 2), null);
		}
	}

	public void setBgImage(String path) {
		try {
			File f = new File(path);
			bgImg = ImageIO.read(f);
			bgImgDrawned = bgImg.getScaledInstance(m_width, m_height, java.awt.Image.SCALE_SMOOTH);
		} catch (Exception e) {
			bgImgDrawned = null;
			bgImg = null;
		}
	}

	public void setFgImage(Image img) {
		try {
			fgImg = img;
			fgImg = fgImg.getScaledInstance((int) (m_width * RATIO_BG_FG), (int) (m_height * RATIO_BG_FG),
					java.awt.Image.SCALE_SMOOTH);
			fgImgDrawned = fgImg.getScaledInstance((int) (m_width * RATIO_BG_FG), (int) (m_height * RATIO_BG_FG),
					java.awt.Image.SCALE_SMOOTH);
		} catch (Exception e) {
			fgImgDrawned = null;
			fgImg = null;
		}
	}

	public void setBigFGImage(Image img) {
		try {
			bigFgImg = img;
			bigFgImg = bigFgImg.getScaledInstance((int) (m_width * RATIO_BG_FG), (int) (m_height * RATIO_BG_FG),
					java.awt.Image.SCALE_SMOOTH);
		} catch (Exception e) {
			bigFgImg = null;
		}
	}

	public void resized(double ratio_w, double ratio_h) {
		m_width *= ratio_w;
		m_height *= ratio_h;
		m_x *= ratio_w;
		m_y *= ratio_h;
		if (fgImg != null) {
			fgImgDrawned = fgImg.getScaledInstance((int) (m_width * RATIO_BG_FG), (int) (m_height * RATIO_BG_FG),
					java.awt.Image.SCALE_SMOOTH);
		}
		if (bgImg != null) {
			bgImgDrawned = bgImg.getScaledInstance(m_width, m_height, java.awt.Image.SCALE_SMOOTH);
		}
	}

	public Button mouseMoved(int x, int y) {
		if (x > m_x && x < m_x + m_width && y > m_y && y < m_y + m_height) {
			return this;
		} else {
			return null;
		}
	}

	public void growImg() {
		m_width = (int) (m_width * 1.2);
		m_height = (int) (m_height * 1.2);
		m_x -= decalageX;
		m_y -= decalageY;
		if (bgImg != null) {
			bgImgDrawned = bgImg.getScaledInstance(m_width, m_height, java.awt.Image.SCALE_SMOOTH);
		}
		if (fgImg != null) {
			if (bigFgImg != null) {
				fgImgDrawned = bigFgImg.getScaledInstance((int) (m_width * RATIO_BG_FG), (int) (m_height * RATIO_BG_FG),
						java.awt.Image.SCALE_SMOOTH);
			} else {
			fgImgDrawned = fgImg.getScaledInstance((int) (m_width * RATIO_BG_FG), (int) (m_height * RATIO_BG_FG),
					java.awt.Image.SCALE_SMOOTH);
			}
		}
	}

	public void reduceImg() {
		m_width = (int) (m_width / 1.2);
		m_height = (int) (m_height / 1.2);
		m_x += decalageX;
		m_y += decalageY;
		if (bgImg != null) {
			bgImgDrawned = bgImg.getScaledInstance(m_width, m_height, java.awt.Image.SCALE_SMOOTH);
		}
		if (fgImg != null) {
			fgImgDrawned = fgImg.getScaledInstance((int) (m_width * RATIO_BG_FG), (int) (m_height * RATIO_BG_FG),
					java.awt.Image.SCALE_SMOOTH);
		}

	}

	public abstract void action() throws Exception;

}
