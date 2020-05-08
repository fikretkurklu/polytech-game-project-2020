package village;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

import game.ImageLoader;
import player.Player;
import village.Village.ID_ENV;

public class MenuPanel extends Panel {

	private String INVENTORY_ICO = "resources/Village/HUD/inventory.png";
	private String ADVENTURE_ICO = "resources/Village/HUD/adventure.png";
	private String MAGIC_SHOP_ICO = "resources/Village/HUD/magicShop.png";
	private String WEAPON_SHOP_ICO = "resources/Village/HUD/weaponShop.png";
	private String INFIRMARY_ICO = "resources/Village/HUD/infirmary.png";
	private String BG = "resources/Village/HUD/MenuBG.png";
	
	
	
	public MenuPanel(int x, int y, int w, int h, Player p) {
		super(x, y, w, h, p);
		int MenuButtonSizeW = (int) (m_width * 0.3);
		int MenuButtonSizeH = m_height / 11;
		setImage("resources/Village/HUD/menuBG.jpg");

		MenuButton b;
		b = new MenuButton((m_width - MenuButtonSizeW) / 2, m_height / 6 - MenuButtonSizeH / 2, MenuButtonSizeW, MenuButtonSizeH,
				ID_ENV.ADVENTURE);
		b.setFgImage(ImageLoader.loadImage(ADVENTURE_ICO));
		b.setBgImage(BG);
		add(b);

		b = new MenuButton((m_width - MenuButtonSizeW) / 2, m_height / 6 * 2 - MenuButtonSizeH / 2, MenuButtonSizeW, MenuButtonSizeH,
				ID_ENV.INVENTORY);
		b.setFgImage(ImageLoader.loadImage(INVENTORY_ICO));
		b.setBgImage(BG);
		add(b);

		b = new MenuButton((m_width - MenuButtonSizeW) / 2, m_height / 6 * 3 - MenuButtonSizeH / 2, MenuButtonSizeW, MenuButtonSizeH,
				ID_ENV.WEAPON_SHOP);
		b.setFgImage(ImageLoader.loadImage(WEAPON_SHOP_ICO));
		b.setBgImage(BG);
		add(b);

		b = new MenuButton((m_width - MenuButtonSizeW) / 2, m_height / 6 * 4 - MenuButtonSizeH / 2, MenuButtonSizeW, MenuButtonSizeH,
				ID_ENV.MAGIC_SHOP);
		b.setFgImage(ImageLoader.loadImage(MAGIC_SHOP_ICO));
		b.setBgImage(BG);
		add(b);

		b = new MenuButton((m_width - MenuButtonSizeW) / 2, m_height / 6 * 5 - MenuButtonSizeH / 2, MenuButtonSizeW, MenuButtonSizeH,
				ID_ENV.INFIRMARY);
		b.setFgImage(ImageLoader.loadImage(INFIRMARY_ICO));
		b.setBgImage(BG);
		add(b);		
	}

}
