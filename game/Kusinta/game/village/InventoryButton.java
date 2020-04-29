package village;

import equipment.Equipment;
import equipment.EquipmentManager.Stuff;

public class InventoryButton extends Button {
	private final String EmptyImg = "resources/Village/HUD/EmptyCase.png";
	Equipment m_equipement;
	EquipementScroll scroll;
	Stuff ID;

	public InventoryButton(int x, int y, int w, int h, Stuff id, Equipment equipement, EquipementScroll scroll) {
		super(x, y, w, h);
		m_equipement = equipement;
		this.scroll = scroll;
		ID = id;
		setBgImage(EmptyImg);
	}

	@Override
	public void action() throws Exception {

	}

	public void setEquipement(Equipment e) {
		m_equipement = e;
		setFgImage(m_equipement.getImagePath());
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
