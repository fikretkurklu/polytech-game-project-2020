package village;

import java.awt.Graphics;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

import game.Model;

public class Village {

	private double DIVISOR = 0.2;
	
	public static enum ID_ENV {
		ADVENTURE,
		INVENTORY,
		MAGIC_SHOP,
		WEAPON_SHOP,
		INFIRMARY,
		EQUIPEMENT,
		DEFAULT
	}

	
	private String IMAGE_WEAPON_SHOP = "resources/Village/HUD/weaponShopBG.jpg";
	private String IMAGE_MAGIC_SHOP = "resources/Village/HUD/magicShopBG.jpg";

	int m_width, m_height;

	MenuPanel menuPanel;
	VillagePanel villagePanel;
	
	Button m_focus;
	Model m_model;
	
	ID_ENV env;

	public Village(int w, int h, Model model) {
		m_width = w;
		m_height = h;
		m_model = model;
		menuPanel = new MenuPanel(0, 0, (int) (m_width * DIVISOR), m_height);
		villagePanel = new VillagePanel(menuPanel.m_width, 0, m_width - menuPanel.m_width, m_height);
		setEnv(ID_ENV.DEFAULT);
		
	}

	public boolean resized(int w, int h) {
		if (w != m_width || h != m_height) {
			m_width = w;
			m_height = h;
			menuPanel.resized(0, 0, (int) (m_width * DIVISOR), m_height);
			villagePanel.resized(menuPanel.m_width, 0, m_width - menuPanel.m_width, m_height);
			return true;
		}
		return false;
	}

	public void paint(Graphics g, int w, int h) {
		resized(w, h);
		menuPanel.paint(g);
		switch (env) {
		case DEFAULT:
			villagePanel.paint(g);
			break;
		default:
			villagePanel.paint(g);
			break;
		}
		
	}

	public void mouseMoved(int x, int y) {
		Button b = null;
		if (menuPanel.isInside(x, y)) {
			b = menuPanel.mouseMoved(x, y);
		} else {
			
			b = immagePanel.mouseMoved(x, y);
		}
		if (b != m_focus) {
			if (m_focus instanceof Button) {
				((Button) m_focus).reduceImg();
			}
			m_focus = b;
			if (m_focus instanceof Button) {
				((Button) m_focus).growImg();
			}
		}
	}

	public void Clicked() {
		if (m_focus != null) {
			setEnv(m_focus.ID);
		}
	}

	private void setEnv(ID_ENV ID) {
		switch (ID) {
		case ADVENTURE:
			break;
		case INFIRMARY:
			break;
		case EQUIPEMENT:
			break;
		case WEAPON_SHOP:
			immagePanel.setImage(IMAGE_WEAPON_SHOP);
			break;
		case MAGIC_SHOP:
			immagePanel.setImage(IMAGE_MAGIC_SHOP);
			break;
		default:
			immagePanel.setImage(IMAGE_VILLAGE);
			break;
		}
		env = ID;
	}
}
