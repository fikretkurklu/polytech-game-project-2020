package village;

import java.awt.Graphics;
import java.awt.Image;

public class Button extends PanelElem{

	String m_label;
	Image m_img;
	
	public Button(int x, int y, int w, int h, String label) {
		super(x, y, w, h);
		m_label = label;
	}

	@Override
	public void paint(Graphics g) {
		g.drawString(m_label, m_x, m_y);
		if (m_img != null) {
			g.drawImage(m_img, m_x, m_y, null);
		}
		
	}

	@Override
	public void resized(long ratio_w, long ratio_h) {
		m_width *= ratio_w;
		m_height *= ratio_h;
		m_x *= ratio_w;
		m_y *= ratio_h;
		if (m_img != null) {
			m_img.getScaledInstance(m_width, m_height, java.awt.Image.SCALE_DEFAULT);
		}
		
	}

}
