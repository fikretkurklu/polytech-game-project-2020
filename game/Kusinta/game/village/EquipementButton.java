package village;

import equipment.Equipment;
import player.Player;

public class EquipementButton extends Button {

	Player m_player;
	Equipment m_equipement;
	EquipementScroll scroll;

	public EquipementButton(int x, int y, int w, int h, Player p, Equipment equipement, EquipementScroll scroll) {
		super(x, y, w, h);
		m_player = p;
		m_equipement = equipement;
		this.scroll = scroll;
		setImage(equipement.getImagePath());
	}

	@Override
	public void action() throws Exception {
		// TODO Auto-generated method stub

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
