package village;

import java.awt.Graphics;

import game.Model;
import player.Player;

public class Village {

	private double DIVISOR = 0.2;

	public static enum ID_ENV {
		ADVENTURE, INVENTORY, MAGIC_SHOP, WEAPON_SHOP, INFIRMARY, DEFAULT
	}

	int m_width, m_height;

	MenuPanel menuPanel;
	VillagePanel villagePanel;
	WeaponPanel weaponPanel;
	MagicPanel magicPanel;
	InfirmaryPanel infirmaryPanel;
	InventoryPanel inventoryPanel;

	Button m_focus;
	static Model m_model;
 
	static ID_ENV env;

	public Village(int w, int h, Model model, Player p) {
		m_width = w;
		m_height = h;
		m_model = model;
		menuPanel = new MenuPanel(0, 0, (int) (m_width * DIVISOR), m_height, p);
		villagePanel = new VillagePanel(menuPanel.m_width, 0, m_width - menuPanel.m_width, m_height);
		weaponPanel = new WeaponPanel(menuPanel.m_width, 0, m_width - menuPanel.m_width, m_height, p);
		magicPanel = new MagicPanel(menuPanel.m_width, 0, m_width - menuPanel.m_width, m_height);
		infirmaryPanel = new InfirmaryPanel(menuPanel.m_width, 0, m_width - menuPanel.m_width, m_height);
		inventoryPanel = new InventoryPanel(menuPanel.m_width, 0, m_width - menuPanel.m_width, m_height);
		setEnv(ID_ENV.DEFAULT);
	}

	public boolean resized(int w, int h) {
		if (w != m_width || h != m_height) {
			m_width = w;
			m_height = h;
			menuPanel.resized(0, 0, (int) (m_width * DIVISOR), m_height);
			villagePanel.resized(menuPanel.m_width, 0, m_width - menuPanel.m_width, m_height);
			weaponPanel.resized(menuPanel.m_width, 0, m_width - menuPanel.m_width, m_height);
			infirmaryPanel.resized(menuPanel.m_width, 0, m_width - menuPanel.m_width, m_height);
			magicPanel.resized(menuPanel.m_width, 0, m_width - menuPanel.m_width, m_height);
			inventoryPanel.resized(menuPanel.m_width, 0, m_width - menuPanel.m_width, m_height);
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
		case INFIRMARY:
			infirmaryPanel.paint(g);
			break;
		case INVENTORY:
			inventoryPanel.paint(g);
			break;
		case WEAPON_SHOP:
			weaponPanel.paint(g);
			break;
		case MAGIC_SHOP:
			magicPanel.paint(g);
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
			switch (env) {
			case DEFAULT:
				b = villagePanel.mouseMoved(x, y);
				break;
			case INFIRMARY:
				b = infirmaryPanel.mouseMoved(x, y);
				break;
			case INVENTORY:
				b = inventoryPanel.mouseMoved(x, y);
				break;
			case WEAPON_SHOP:
				b = weaponPanel.mouseMoved(x, y);
				break;
			case MAGIC_SHOP:
				b = magicPanel.mouseMoved(x, y);
				break;
			default:
				b = null;
				break;
			}
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
			try {
				m_focus.action();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void setEnv(ID_ENV ID) {
		env = ID;
	}

	public static void leaveVillage() throws Exception {
		m_model.setRoomEnv();
	}
}
