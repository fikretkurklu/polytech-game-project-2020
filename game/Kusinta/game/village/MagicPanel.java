package village;

import equipment.EquipmentManager;
import equipment.EquipmentManager.Conso;
import equipment.SmallHealthPotion;
import player.Player;

public class MagicPanel extends Panel {
	
	private String IMAGE_MAGIC_SHOP = "resources/Village/magicShopBG.jpg";
	EquipmentManager EM;
	private final int NB_MAX_CONSUMABLE = 2;
	
	public MagicPanel(int x, int y, int w, int h, Player p) {
		super(x, y, w, h, p);
		
		setImage(IMAGE_MAGIC_SHOP);
		int Scroll_w = w / Scroll.WIDTH_RATIO;
		int Scroll_h = h / Scroll.HEIGHT_RATIO;
		m_scroll = new Scroll(w / 2 - Scroll_w / 2 , 0 ,Scroll_w ,Scroll_h , "MAGIC SHOP");
		
		m_EquipemenScroll = new EquipementScroll(w / 3, (int)(Scroll_h * 1.5), w/3, h/3);
		EM = new EquipmentManager();
		
		add(new RollButton(m_width / 10, m_height / 10, m_width/8, m_width/8, this, p));
		createButton();
		drawConsumable();

	}
	
	public void createButton() {
		int buttonSize = m_width / ((NB_MAX_CONSUMABLE + 4 )/ 2);
		try {
			for (int i = 0; i < NB_MAX_CONSUMABLE / 2; i++) {
				Button b;
				b = new EquipementButton(buttonSize * (i + 1), m_height - buttonSize * 2, buttonSize, buttonSize, m_player, m_EquipemenScroll);
				add(b);
			}
			for (int i = 0; i < NB_MAX_CONSUMABLE / 2; i++) {
				Button b = new EquipementButton(buttonSize * (i+1), m_height - buttonSize, buttonSize, buttonSize, m_player, m_EquipemenScroll);
				add(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void drawConsumable() {
		try {
//			((EquipementButton)m_elem.get(0)).setEquipement(EM.newConsumable(Conso.SmallHealthPotion));
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		for (Button e : m_elem) {
			if (e instanceof EquipementButton) {
				try {
					((EquipementButton) e).setEquipement(EM.newConsumable(Conso.SmallHealthPotion));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}
