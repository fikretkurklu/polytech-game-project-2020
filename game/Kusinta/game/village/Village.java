package village;

import java.awt.Graphics;

public class Village {
	public final static long DIVISOR = 1/8;
	
	int m_width, m_height;
	
	Panel menuPanel;
	Panel immagePanel;
	public Village(int w, int h) {
		m_width = w;
		m_height = h;
		menuPanel = new Panel(0, 0, (int) (m_width * DIVISOR), m_height);
		immagePanel = new Panel((int) (m_width * DIVISOR), 0 , (int)(m_width * ( 1 - DIVISOR)), m_height);
	}
	
	public void paint(Graphics g, int w, int h) {
		if (w != m_width || h != m_height) {
			m_width = w;
			m_height = h;
		}
		menuPanel.paint(g, (int) (m_width * DIVISOR), m_height);
		immagePanel.paint(g, (int)(m_width * ( 1 - DIVISOR)), m_height);
	}
}
