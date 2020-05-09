package village;

import equipment.BigHealthPotion;
import equipment.Equipment;
import equipment.SmallHealthPotion;
import equipment.Stat.Stats;
import player.Player;

public class EquipementButton extends Button {

	Player m_player;
	Equipment m_equipement;
	EquipementScroll scroll;
	private final String EmptyImg = "resources/Village/EmptyCase.png";

	public EquipementButton(int x, int y, int w, int h, Player p, EquipementScroll scroll) {
		super(x, y, w, h);
		m_player = p;
		m_equipement = null;
		this.scroll = scroll;
		setBgImage(EmptyImg);

	}

	public void setEquipement(Equipment equipement) {
		m_equipement = equipement;
		setFgImage(equipement.getImage());
	}

	@Override
	public void action() throws Exception {
		if (m_equipement != null) {
			if (m_player.getMoney() >= m_equipement.getModification(Stats.Price)) {
				if (m_equipement instanceof SmallHealthPotion) {
					m_player.getSmallConsumables().add(((SmallHealthPotion) m_equipement));
					m_player.setMoney(-m_equipement.getModification(Stats.Price));
				} else if (m_equipement instanceof BigHealthPotion) {
					m_player.getBigConsumables().add(((BigHealthPotion) m_equipement));
					m_player.setMoney(-m_equipement.getModification(Stats.Price));
				} else {
					Equipment lastEquipement = m_player.addEquipment(m_equipement);
					if (lastEquipement != null) {
						m_player.setMoney(lastEquipement.getModification(Stats.Price));
					}
					m_player.setMoney(-m_equipement.getModification(Stats.Price));
					m_equipement = null;
					setFgImage(null);
				}
				
			}
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
