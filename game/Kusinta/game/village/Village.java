package village;

import java.awt.Graphics;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

public class Village {

	private double DIVISOR = 0.2;
	
	public static enum ID_BUTTON {
		ADVENTURE,
		INVENTORY,
		MAGIC_SHOP,
		WEAPON_SHOP,
		INFIRMARY,
		EQUIPEMENT,
		DEFAULT
	}

	private String INVENTORY_ICO = "resources/Village/HUD/Chest.png";
	private String ADVENTURE_ICO = "resources/Village/HUD/adventure.png";
	private String MAGIC_SHOP_ICO = "resources/Village/HUD/magicShop.png";
	private String WEAPON_SHOP_ICO = "resources/Village/HUD/weaponShop.png";
	private String INFIRMARY_ICO = "resources/Village/HUD/infirmary.png";
	private String BUTTON_PANEL_BG = "resources/Village/HUD/menuBG.jpg";
	
	private String IMAGE_VILLAGE = "resources/Village/HUD/villageBG.jpg";
	private String IMAGE_WEAPON_SHOP = "resources/Village/HUD/weaponShopBG.jpg";
	private String IMAGE_MAGIC_SHOP = "resources/Village/HUD/magicShopBG.jpg";

	int m_width, m_height;

	Panel menuPanel;
	Panel immagePanel;

	Button m_focus;

	public Village(int w, int h) {
		m_width = w;
		m_height = h;
		menuPanel = new Panel(0, 0, (int) (m_width * DIVISOR), m_height);
		menuPanel.setImage(BUTTON_PANEL_BG);
		int buttonSizeW = (int) (menuPanel.m_width * 0.5);
		int buttonSizeH = menuPanel.m_height / 12;

		immagePanel = new Panel(menuPanel.m_width, 0, m_width - menuPanel.m_width, m_height);
		setEnv(ID_BUTTON.DEFAULT);

		Button b;
		b = new Button((menuPanel.m_width - buttonSizeW) / 2, menuPanel.m_height / 6 - buttonSizeH / 2, buttonSizeW,
				buttonSizeH, ID_BUTTON.ADVENTURE);
		b.setImage(ADVENTURE_ICO);
		menuPanel.add(b);
		b = new Button((menuPanel.m_width - buttonSizeW) / 2, menuPanel.m_height / 6 * 2 - buttonSizeH / 2, buttonSizeW,
				buttonSizeH, ID_BUTTON.INVENTORY);
		b.setImage(INVENTORY_ICO);
		menuPanel.add(b);
		b = new Button((menuPanel.m_width - buttonSizeW) / 2, menuPanel.m_height / 6 * 3 - buttonSizeH / 2, buttonSizeW,
				buttonSizeH, ID_BUTTON.WEAPON_SHOP);
		b.setImage(WEAPON_SHOP_ICO);
		menuPanel.add(b);
		b = new Button((menuPanel.m_width - buttonSizeW) / 2, menuPanel.m_height / 6 * 4 - buttonSizeH / 2, buttonSizeW,
				buttonSizeH, ID_BUTTON.MAGIC_SHOP);
		b.setImage(MAGIC_SHOP_ICO);
		menuPanel.add(b);
		b = new Button((menuPanel.m_width - buttonSizeW) / 2, menuPanel.m_height / 6 * 5 - buttonSizeH / 2, buttonSizeW,
				buttonSizeH, ID_BUTTON.INFIRMARY);
		b.setImage(INFIRMARY_ICO);
		menuPanel.add(b);
	}

	public boolean resized(int w, int h) {
		if (w != m_width || h != m_height) {
			m_width = w;
			m_height = h;
			menuPanel.resized(0, 0, (int) (m_width * DIVISOR), m_height);
			immagePanel.resized(menuPanel.m_width, 0, m_width - menuPanel.m_width, m_height);
			return true;
		}
		return false;
	}

	public void paint(Graphics g, int w, int h) {
		resized(w, h);
		menuPanel.paint(g);
		immagePanel.paint(g);
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

	private void setEnv(ID_BUTTON ID) {
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
	}
}
