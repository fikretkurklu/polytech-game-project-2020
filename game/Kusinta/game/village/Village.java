package village;

import java.awt.Graphics;

public class Village {

	private double DIVISOR = 0.2;

	private int ID_ADVENTURE = 0;
	private int ID_INVENTORY = 1;
	private int ID_MAGIC_SHOP = 2;
	private int ID_WEAPON_SHOP = 3;
	private int ID_INFIRMARY = 4;

	private String INVENTORY_ICO = "resources/Village/HUD/Chest.png";
	private String ADVENTURE_ICO = "resources/Village/HUD/adventure.png";
	private String MAGIC_SHOP_ICO = "resources/Village/HUD/magicShop.png";
	private String WEAPON_SHOP_ICO = "resources/Village/HUD/weaponShop.png";
	private String INFIRMARY_ICO = "resources/Village/HUD/infirmary.png";
	private String BUTTON_PANEL_BG = "resources/Village/HUD/menuBG.jpg";
	private String IMAGE_PANEL_BG = "resources/Village/HUD/villageBG.jpg";

	int m_width, m_height;

	Panel menuPanel;
	Panel immagePanel;
	
	PanelElem m_focus;

	public Village(int w, int h) {
		m_width = w;
		m_height = h;
		menuPanel = new Panel(0, 0, (int) (m_width * DIVISOR), m_height);
		menuPanel.setImage(BUTTON_PANEL_BG);
		// menuPanel.setBackGroundColor(Color.BLUE.darker());
		int buttonSizeW = (int) (menuPanel.m_width * 0.5);
		int buttonSizeH = menuPanel.m_height / 12;

		immagePanel = new Panel(menuPanel.m_width, 0, m_width - menuPanel.m_width, m_height);
		immagePanel.setImage(IMAGE_PANEL_BG);

		Button b;
		b = new Button((menuPanel.m_width - buttonSizeW) / 2, menuPanel.m_height / 6 - buttonSizeH / 2, buttonSizeW,
				buttonSizeH, ID_ADVENTURE);
		b.setImage(ADVENTURE_ICO);
		menuPanel.add(b);
		b = new Button((menuPanel.m_width - buttonSizeW) / 2, menuPanel.m_height / 6 * 2 - buttonSizeH / 2, buttonSizeW,
				buttonSizeH, ID_INVENTORY);
		b.setImage(INVENTORY_ICO);
		menuPanel.add(b);
		b = new Button((menuPanel.m_width - buttonSizeW) / 2, menuPanel.m_height / 6 * 3 - buttonSizeH / 2, buttonSizeW,
				buttonSizeH, ID_WEAPON_SHOP);
		b.setImage(WEAPON_SHOP_ICO);
		menuPanel.add(b);
		b = new Button((menuPanel.m_width - buttonSizeW) / 2, menuPanel.m_height / 6 * 4 - buttonSizeH / 2, buttonSizeW,
				buttonSizeH, ID_MAGIC_SHOP);
		b.setImage(MAGIC_SHOP_ICO);
		menuPanel.add(b);
		b = new Button((menuPanel.m_width - buttonSizeW) / 2, menuPanel.m_height / 6 * 5 - buttonSizeH / 2, buttonSizeW,
				buttonSizeH, ID_INFIRMARY);
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
		PanelElem b = null;
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
	
	
}
