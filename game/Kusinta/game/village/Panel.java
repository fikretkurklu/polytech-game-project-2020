package village;

import java.awt.Graphics;
import java.util.LinkedList;


public class Panel {
	int m_width, m_height;
	int m_x, m_y;
	LinkedList<PanelElem> m_elem;
	
	public Panel(int x, int y, int w, int h) {
		m_width = w;
		m_height = h;
		m_x = x;
		m_y = y;
		m_elem = new LinkedList<PanelElem>();
	}
	
	public void add(PanelElem elem) {
		m_elem.add(elem);
	}
	public void remove(PanelElem elem) {
		m_elem.remove(elem);
	}
	
	public void resized(int w, int h) {
		if (w != m_width || h != m_height) {
			long ratio_w = w/m_width;
			long ratio_h = h/m_height;
			m_width = w;
			m_height = h;
			m_x *= ratio_w;
			m_y *= ratio_h;
			for (int i = 0;  i < m_elem.size(); i ++) {
				m_elem.get(i).resized(ratio_w, ratio_h);
			}
		}
	}
	public void paint(Graphics g, int w, int h) {
		resized(w, h);
		for (int i = 0;  i < m_elem.size(); i ++) {
			m_elem.get(i).paint(g);
		}
	}
}
