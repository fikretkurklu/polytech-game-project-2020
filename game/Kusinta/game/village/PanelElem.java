package village;

import java.awt.Graphics;

public abstract class PanelElem {
	int m_width, m_height, m_x, m_y;
	
	public PanelElem(int x, int y, int w, int h) {
		m_width = w;
		m_height = h;
		m_x = x;
		m_y = y;
	}
		
	public abstract void resized(long ratio_w, long ratio_h);
	
	public abstract void paint(Graphics g);
}
