package village;

import java.util.HashMap;
import java.util.Map;

import equipment.Equipment;
import equipment.EquipmentManager.Stuff;
import game.Coord;
import player.Player;

public class InventoryPanel extends Panel {
	private final String BG = "resources/Village/inventoryBG.jpg";
	private HashMap<Stuff, Equipment> equipements;

	public InventoryPanel(int x, int y, int w, int h, Player p) {
		super(x, y, w, h, p);
		int Scroll_w = w / Scroll.WIDTH_RATIO;
		int Scroll_h = h / Scroll.HEIGHT_RATIO;
		setImage(BG);
		m_scroll = new Scroll(w / 2 - Scroll_w / 2 , 0 ,Scroll_w ,Scroll_h , "INVENTORY");
		equipements = m_player.getEquipment();
		int buttonSize = m_width / 10;
		
		Scroll_w = w/3;
		Scroll_h = h/4;
		m_EquipemenScroll = new EquipementScroll(0, h - Scroll_h, Scroll_w, Scroll_h);
		Coord center = new Coord(m_width / 2 - buttonSize / 2, m_height / 2 - buttonSize / 2);
		Button b;
		b = new InventoryButton(center.X(), center.Y(), buttonSize, buttonSize, Stuff.Armor, m_player, m_EquipemenScroll);
		add(b);
		b = new InventoryButton(center.X(), center.Y() - buttonSize, buttonSize, buttonSize, Stuff.Helmet, m_player, m_EquipemenScroll);
		add(b);
		b = new InventoryButton(center.X(), center.Y() + buttonSize, buttonSize, buttonSize, Stuff.Belt, m_player, m_EquipemenScroll);
		add(b);
		b = new InventoryButton(center.X(), center.Y() + 2 * buttonSize, buttonSize, buttonSize, Stuff.Grieves, m_player, m_EquipemenScroll);
		add(b);
		b = new InventoryButton(center.X() - buttonSize, center.Y(), buttonSize, buttonSize, Stuff.Gloves, m_player, m_EquipemenScroll);
		add(b);
		b = new InventoryButton(center.X() + buttonSize, center.Y(), buttonSize, buttonSize, Stuff.Bow, m_player, m_EquipemenScroll);
		add(b);
	}
	
	public void setButton() {
		for (Map.Entry<Stuff, Equipment> e :  equipements.entrySet()) {
			Stuff k = e.getKey();
			if (e.getValue() != null) {
				for (Button b : m_elem) {
					InventoryButton Ib = ((InventoryButton) b);
					if (Ib.ID == k) {
						Ib.setEquipement(e.getValue());
						break;
					}
				}
			}
		}
	}
	
}
