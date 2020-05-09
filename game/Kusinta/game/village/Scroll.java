package village;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.Coord;

public class Scroll {
	public final static int WIDTH_RATIO = 3;
	public final static int HEIGHT_RATIO = 7;
	int m_width, m_height, m_x, m_y;
	Image m_img;
	String m_label;
	Font font;
	Coord labelPos;
	private static final String IMG_PATH = "resources/Village/Scroll.png";
	
	public Scroll(int x, int y, int w, int h, String s) {
		m_x = x;
		m_y = y;
		m_height = h;
		m_width = w;
		m_label = s;
		font = new Font("Georgia", Font.BOLD, 20);
		labelPos = new Coord(m_width / 2 - (font.getSize() * m_label.length()) / 3 , m_height / 2 );
		labelPos.translate(m_x, m_y);
		setImage();
	}
	public void setImage() {
		try {
			File f = new File(IMG_PATH);
			m_img = ImageIO.read(f);
			m_img = m_img.getScaledInstance(m_width, m_height, java.awt.Image.SCALE_SMOOTH);
			
		} catch (IOException e) {
			m_img = null;
			System.out.println("Error loadinf image");
		}
	}
	public void paint(Graphics g) {
		g.setFont(font);
		g.drawImage(m_img, m_x, m_y, null);
		g.drawString(m_label, labelPos.X(), labelPos.Y());
	}
	
	public void resized(double ratio_w, double ratio_h) {
		m_width *= ratio_w;
		m_height *= ratio_h;
		m_x *= ratio_w;
		m_y *= ratio_h;
		if (m_img != null) {
			m_img = m_img.getScaledInstance(m_width, m_height, java.awt.Image.SCALE_SMOOTH);
		}
		labelPos = new Coord(m_width / 2 - (font.getSize() * m_label.length()) / 3 , m_height / 2 );
		labelPos.translate(m_x, m_y);
	}
}
