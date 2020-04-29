package village;

import java.awt.Graphics;
import player.Player;
import village.Village.ID_ENV;

public class MenuPanel extends Panel {

	private String INVENTORY_ICO = "resources/Village/HUD/inventory.png";
	private String ADVENTURE_ICO = "resources/Village/HUD/adventure.png";
	private String MAGIC_SHOP_ICO = "resources/Village/HUD/magicShop.png";
	private String WEAPON_SHOP_ICO = "resources/Village/HUD/weaponShop.png";
	private String INFIRMARY_ICO = "resources/Village/HUD/infirmary.png";
	
	CoinDraw coinDraw;
	
	public MenuPanel(int x, int y, int w, int h, Player p) {
		super(x, y, w, h);
		int MenuButtonSizeW = (int) (m_width * 0.3);
		int MenuButtonSizeH = m_height / 11;
		setImage("resources/Village/HUD/menuBG.jpg");

		MenuButton b;
		b = new MenuButton((m_width - MenuButtonSizeW) / 2, m_height / 6 - MenuButtonSizeH / 2, MenuButtonSizeW, MenuButtonSizeH,
				ID_ENV.ADVENTURE);
		b.setFgImage(ADVENTURE_ICO);
		add(b);

		b = new MenuButton((m_width - MenuButtonSizeW) / 2, m_height / 6 * 2 - MenuButtonSizeH / 2, MenuButtonSizeW, MenuButtonSizeH,
				ID_ENV.INVENTORY);
		b.setFgImage(INVENTORY_ICO);
		add(b);

		b = new MenuButton((m_width - MenuButtonSizeW) / 2, m_height / 6 * 3 - MenuButtonSizeH / 2, MenuButtonSizeW, MenuButtonSizeH,
				ID_ENV.WEAPON_SHOP);
		b.setFgImage(WEAPON_SHOP_ICO);
		add(b);

		b = new MenuButton((m_width - MenuButtonSizeW) / 2, m_height / 6 * 4 - MenuButtonSizeH / 2, MenuButtonSizeW, MenuButtonSizeH,
				ID_ENV.MAGIC_SHOP);
		b.setFgImage(MAGIC_SHOP_ICO);
		add(b);

		b = new MenuButton((m_width - MenuButtonSizeW) / 2, m_height / 6 * 5 - MenuButtonSizeH / 2, MenuButtonSizeW, MenuButtonSizeH,
				ID_ENV.INFIRMARY);
		b.setFgImage(INFIRMARY_ICO);
		add(b);
		
		coinDraw = new CoinDraw(m_width / 8, m_height - m_height / 10, m_width / 2, m_height / 10, p);
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		coinDraw.paint(g);
	}
	
	@Override
	public void resized(int x, int y, int w, int h) {
		coinDraw.resized((double) w / (double) m_width, (double) h / (double) m_height);
		super.resized(x, y, w, h);
	}

}
