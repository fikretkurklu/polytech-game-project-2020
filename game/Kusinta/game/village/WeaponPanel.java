package village;

import equipment.EquipmentManager;
import player.Player;

public class WeaponPanel extends Panel {

	private String IMAGE_WEAPON_SHOP = "resources/Village/HUD/weaponShopBG.jpg";
	EquipmentManager EM;
	private final int NB_MAX_EQUIPEMENT = 14;
	

	public WeaponPanel(int x, int y, int w, int h, Player p) {
		super(x, y, w, h, p);
		setImage(IMAGE_WEAPON_SHOP);
		
		int Scroll_w = w / Scroll.WIDTH_RATIO;
		int Scroll_h = h / Scroll.HEIGHT_RATIO;
		m_scroll = new Scroll(w / 2 - Scroll_w / 2, 0, Scroll_w, Scroll_h, "WEAPON SHOP");
		
		m_EquipemenScroll = new EquipementScroll(w / 3, (int)(Scroll_h * 1.5), w/3, h/3);
		EM = new EquipmentManager();
		drawEquipement();
		add(new RollButton(m_width / 10, m_height / 10, m_width/8, m_width/8, this, p));
		
	}
	
	public void drawEquipement() {
		int buttonSize = m_width / ((NB_MAX_EQUIPEMENT + 4 )/ 2);
		try {
			for (int i = 0; i < NB_MAX_EQUIPEMENT / 2; i++) {
				Button b;
				b = new EquipementButton(buttonSize * (i + 1), m_height - buttonSize * 2, buttonSize, buttonSize, m_player,
						EM.newEquipment(), m_EquipemenScroll);
				add(b);
			}
			for (int i = 0; i < NB_MAX_EQUIPEMENT / 2; i++) {
				Button b = new EquipementButton(buttonSize * (i+1), m_height - buttonSize, buttonSize, buttonSize, m_player,
						EM.newEquipment(), m_EquipemenScroll);
				add(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
