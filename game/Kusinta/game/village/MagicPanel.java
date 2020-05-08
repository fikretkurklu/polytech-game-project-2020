package village;

import equipment.EquipmentManager;
import player.Player;

public class MagicPanel extends Panel {
	
	private String IMAGE_MAGIC_SHOP = "resources/Village/magicShopBG.jpg";
	EquipmentManager EM;
	private final int NB_MAX_CONSUMABLE = 14;
	
	public MagicPanel(int x, int y, int w, int h, Player p) {
		super(x, y, w, h, p);
		
		setImage(IMAGE_MAGIC_SHOP);
		int Scroll_w = w / Scroll.WIDTH_RATIO;
		int Scroll_h = h / Scroll.HEIGHT_RATIO;
		m_scroll = new Scroll(w / 2 - Scroll_w / 2 , 0 ,Scroll_w ,Scroll_h , "MAGIC SHOP");
		
		drawConsumable();
		add(new RollButton(m_width / 10, m_height / 10, m_width/8, m_width/8, this, p));
	}

	public void drawConsumable() {
		
	}
}
