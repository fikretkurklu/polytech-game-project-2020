package village;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Button {

	Image m_img;
	int m_width, m_height, m_x, m_y;
	
	
	public Button(int x, int y, int w, int h) {
		m_width = w;
		m_height = h;
		m_x = x;
		m_y = y;
	}
	
	public void paint(Graphics g) {
		if (m_img != null) {
			g.drawImage(m_img, m_x, m_y, null);
		}
	}

	@SuppressWarnings("unused")
	public void setImage(String path) {
		try {
			File f = new File(path);
			if (f == null)
				System.out.println("Erreur while loading image at : "+ path);
			m_img = ImageIO.read(f);
			m_img = m_img.getScaledInstance(m_width, m_height, java.awt.Image.SCALE_SMOOTH);
		} catch (IOException e) {
			m_img = null;
			e.printStackTrace();
		}
	}
	public void resized(double ratio_w, double ratio_h) {
		m_width *= ratio_w;
		m_height *= ratio_h;
		m_x *= ratio_w;
		m_y *= ratio_h;
		if (m_img != null) {
			m_img = m_img.getScaledInstance(m_width, m_height, java.awt.Image.SCALE_SMOOTH);
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
		m_height = (int)(m_height * 1.2);
		m_img = m_img.getScaledInstance(m_width, m_height, java.awt.Image.SCALE_SMOOTH);
	}
	
	public void reduceImg() {
		m_width = (int) (m_width / 1.2);
		m_height = (int)(m_height / 1.2);
		m_img = m_img.getScaledInstance(m_width, m_height, java.awt.Image.SCALE_SMOOTH);
	}
	
	public abstract void action() throws Exception;

}
