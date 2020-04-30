package village;

import equipment.Equipment;
import equipment.EquipmentManager.Stuff;
import equipment.Stat.Stats;
import player.Player;

public class InventoryButton extends Button {
	private final String EmptyImg = "resources/Village/HUD/EmptyCase.png";
	Equipment m_equipement;
	EquipementScroll scroll;
	Stuff ID;
	Player m_player;

	public InventoryButton(int x, int y, int w, int h, Stuff id, Player p, EquipementScroll scroll) {
		super(x, y, w, h);
		this.scroll = scroll;
		ID = id;
		m_player = p;
		m_equipement = null;
		setBgImage(EmptyImg);
	}

	@Override
	public void action() throws Exception {
		if (m_equipement != null) {
			m_player.setMoney(m_equipement.getModification(Stats.Price));
			m_player.removeEquipment(m_equipement);
			setEquipement(null);
		}
	}

	public void setEquipement(Equipment e) {
		m_equipement = e;
		if (e != null) {
			setFgImage(m_equipement.getImagePath());
		} else {
			setFgImage(null);
		}
	}

	@Override
	public void growImg() {
		super.growImg();
		scroll.setEquipement(m_equipement);

	}

	@Override
	public void reduceImg() {
		super.reduceImg();
		scroll.setEquipement(null);
	}
}
