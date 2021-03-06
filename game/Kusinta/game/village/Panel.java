package village;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import player.Player;

public abstract class Panel {
	int m_width, m_height;
	int m_x, m_y;
	LinkedList<Button> m_elem;
	Image m_bg;
	protected Scroll m_scroll;
	protected EquipementScroll m_EquipemenScroll;
	
	boolean focus;
	Player m_player;

	public Panel(int x, int y, int w, int h, Player p) {
		m_width = w;
		m_height = h;
		m_x = x;
		m_y = y;
		m_elem = new LinkedList<Button>();
		focus = false;
		m_player = p;
	}

	public void add(Button elem) {
		m_elem.add(elem);
	}

	public void remove(Button elem) {
		m_elem.remove(elem);
	}

	public void resized(int x, int y, int w, int h) {
		double dw = (double) w / (double) m_width;
		double dh = (double) h / (double) m_height;
		m_x = x;
		m_y = y;
		m_width = w;
		m_height = h;
		for (Button e : m_elem) {
			e.resized(dw, dh);
		}
		if (m_bg != null) {
			m_bg = m_bg.getScaledInstance(m_width, m_height, java.awt.Image.SCALE_SMOOTH);
		}
		if (m_scroll != null) {
			m_scroll.resized(dw, dh);
		}
		if (m_EquipemenScroll != null) {
			m_EquipemenScroll.resized(dw, dh);
		}
	}

	public void paint(Graphics g) {
		Graphics bg = g.create(m_x, m_y, m_width, m_height);
		if (m_bg != null) {
			bg.drawImage(m_bg, 0, 0, null);
		}
		for (Button e : m_elem) {
			e.paint(bg);
		}
		if (m_scroll != null) {
			m_scroll.paint(bg);
		}
		if (m_EquipemenScroll != null) {
			m_EquipemenScroll.paint(bg);
		}
		bg.dispose();
	}

	public void setImage(String path) {
		try {
			File f = new File(path);
			m_bg = ImageIO.read(f);
			m_bg = m_bg.getScaledInstance(m_width, m_height, java.awt.Image.SCALE_SMOOTH);
		} catch (Exception e) {
			m_bg = null;
			e.printStackTrace();
		}
	}

	/*
	 * Est appelée uniquement si on est sur le Panel
	 */
	public Button mouseMoved(int x, int y) {
		for (Button e : m_elem) {
			if (e.mouseMoved(x - m_x, y - m_y) != null) {
				return e;
			}
		}
		return null;
	}
	
	public boolean isInside(int x, int y) {
		return (x > m_x && x < m_x + m_width && y > m_y && y < m_y + m_height);
	}
	

}
