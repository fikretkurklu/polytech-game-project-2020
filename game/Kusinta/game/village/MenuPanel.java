package village;


import java.awt.Image;

import entityFactory.ImageLoader;
import player.Player;
import village.Village.ID_ENV;

public class MenuPanel extends Panel {

	private String INVENTORY_ICO = "resources/Village/inventory.png";
	private String ADVENTURE_ICO = "resources/Village/adventure2.png";
	private String ADVENTURE_BICO = "resources/Village/adventure.png";
	private String MAGIC_SHOP_ICO = "resources/Village/magicShop.png";
	private String MAGIC_SHOP_BICO = "resources/Village/magicShopB.png";
	private String WEAPON_SHOP_ICO = "resources/Village/weaponShop.png";
	private String WEAPON_SHOP_BICO = "resources/Village/weaponShopB.png";
	private String INFIRMARY_ICO = "resources/Village/infirmary.png";
	private String BG = "resources/Village/MenuBG.png";
	
	
	
	public MenuPanel(int x, int y, int w, int h, Player p) {
		super(x, y, w, h, p);
		int MenuButtonSizeW = (int) (m_width * 0.3);
		int MenuButtonSizeH = m_height / 11;
		setImage("resources/Village/menuBG.jpg");

		MenuButton b;
		b = new MenuButton((m_width - MenuButtonSizeW) / 2, m_height / 6  * 2- MenuButtonSizeH / 2, MenuButtonSizeW, MenuButtonSizeH,
				ID_ENV.ADVENTURE);
		b.setFgImage(ImageLoader.loadImage(ADVENTURE_ICO));
		b.setBigFGImage(ImageLoader.loadImage(ADVENTURE_BICO));
		b.setBgImage(BG);
		add(b);

		b = new MenuButton((m_width - MenuButtonSizeW) / 2, m_height / 6 * 3 - MenuButtonSizeH / 2, MenuButtonSizeW, MenuButtonSizeH,
				ID_ENV.INVENTORY);
		b.setFgImage(ImageLoader.loadImage(INVENTORY_ICO));
		b.setBgImage(BG);
		add(b);

		b = new MenuButton((m_width - MenuButtonSizeW) / 2, m_height / 6 * 4  - MenuButtonSizeH / 2, MenuButtonSizeW, MenuButtonSizeH,
				ID_ENV.WEAPON_SHOP);
		b.setFgImage(ImageLoader.loadImage(WEAPON_SHOP_ICO));
		b.setBigFGImage(ImageLoader.loadImage(WEAPON_SHOP_BICO));
		b.setBgImage(BG);
		add(b);

		b = new MenuButton((m_width - MenuButtonSizeW) / 2, m_height / 6 * 5 - MenuButtonSizeH / 2, MenuButtonSizeW, MenuButtonSizeH,
				ID_ENV.MAGIC_SHOP);
		b.setFgImage(ImageLoader.loadImage(MAGIC_SHOP_ICO));
		b.setBigFGImage(ImageLoader.loadImage(MAGIC_SHOP_BICO));
		b.setBgImage(BG);
		add(b);
	}

}
