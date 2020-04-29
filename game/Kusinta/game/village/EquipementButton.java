package village;

import equipment.Equipment;
import equipment.Stat.Stats;
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
		if (isActif) {
			if (m_player.getMoney() >= m_equipement.getModification(Stats.Price)) {
				Equipment lastEquipement = m_player.addEquipment(m_equipement);
				if (lastEquipement != null) {
					m_player.setMoney(lastEquipement.getModification(Stats.Price));
				}
				isActif = false;
				m_player.setMoney(- m_equipement.getModification(Stats.Price));
			}
		}

	}

	@Override
	public void growImg() {
		if (isActif) {
			super.growImg();
			scroll.setEquipement(m_equipement);
		}

	}

	@Override
	public void reduceImg() {
		if (isActif) {
			super.reduceImg();
			scroll.setEquipement(null);
		}
	}
}
