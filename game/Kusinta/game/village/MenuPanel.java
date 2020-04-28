package village;

import village.Village.ID_ENV;

public class MenuPanel extends Panel {

	private String INVENTORY_ICO = "resources/Village/HUD/Chest.png";
	private String ADVENTURE_ICO = "resources/Village/HUD/adventure.png";
	private String MAGIC_SHOP_ICO = "resources/Village/HUD/magicShop.png";
	private String WEAPON_SHOP_ICO = "resources/Village/HUD/weaponShop.png";
	private String INFIRMARY_ICO = "resources/Village/HUD/infirmary.png";

	public MenuPanel(int x, int y, int w, int h) {
		super(x, y, w, h);
		int MenuButtonSizeW = (int) (m_width * 0.5);
		int MenuButtonSizeH = m_height / 12;
		setImage("resources/Village/HUD/menuBG.jpg");

		MenuButton b;
		b = new MenuButton((m_width - MenuButtonSizeW) / 2, m_height / 6 - MenuButtonSizeH / 2, MenuButtonSizeW, MenuButtonSizeH,
				ID_ENV.ADVENTURE);
		b.setImage(ADVENTURE_ICO);
		add(b);

		b = new MenuButton((m_width - MenuButtonSizeW) / 2, m_height / 6 * 2 - MenuButtonSizeH / 2, MenuButtonSizeW, MenuButtonSizeH,
				ID_ENV.INVENTORY);
		b.setImage(INVENTORY_ICO);
		add(b);

		b = new MenuButton((m_width - MenuButtonSizeW) / 2, m_height / 6 * 3 - MenuButtonSizeH / 2, MenuButtonSizeW, MenuButtonSizeH,
				ID_ENV.WEAPON_SHOP);
		b.setImage(WEAPON_SHOP_ICO);
		add(b);

		b = new MenuButton((m_width - MenuButtonSizeW) / 2, m_height / 6 * 4 - MenuButtonSizeH / 2, MenuButtonSizeW, MenuButtonSizeH,
				ID_ENV.MAGIC_SHOP);
		b.setImage(MAGIC_SHOP_ICO);
		add(b);

		b = new MenuButton((m_width - MenuButtonSizeW) / 2, m_height / 6 * 5 - MenuButtonSizeH / 2, MenuButtonSizeW, MenuButtonSizeH,
				ID_ENV.INFIRMARY);
		b.setImage(INFIRMARY_ICO);
		add(b);
	}

}
