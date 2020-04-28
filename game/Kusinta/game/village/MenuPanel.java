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
		int buttonSizeW = (int) (m_width * 0.5);
		int buttonSizeH = m_height / 12;
		setImage("resources/Village/HUD/menuBG.jpg");

		Button b;
		b = new Button((m_width - buttonSizeW) / 2, m_height / 6 - buttonSizeH / 2, buttonSizeW, buttonSizeH,
				ID_ENV.ADVENTURE);
		b.setImage(ADVENTURE_ICO);
		add(b);

		b = new Button((m_width - buttonSizeW) / 2, m_height / 6 * 2 - buttonSizeH / 2, buttonSizeW, buttonSizeH,
				ID_ENV.INVENTORY);
		b.setImage(INVENTORY_ICO);
		add(b);

		b = new Button((m_width - buttonSizeW) / 2, m_height / 6 * 3 - buttonSizeH / 2, buttonSizeW, buttonSizeH,
				ID_ENV.WEAPON_SHOP);
		b.setImage(WEAPON_SHOP_ICO);
		add(b);

		b = new Button((m_width - buttonSizeW) / 2, m_height / 6 * 4 - buttonSizeH / 2, buttonSizeW, buttonSizeH,
				ID_ENV.MAGIC_SHOP);
		b.setImage(MAGIC_SHOP_ICO);
		add(b);

		b = new Button((m_width - buttonSizeW) / 2, m_height / 6 * 5 - buttonSizeH / 2, buttonSizeW, buttonSizeH,
				ID_ENV.INFIRMARY);
		b.setImage(INFIRMARY_ICO);
		add(b);
	}

}
