package village;

import equipment.Equipment;
import equipment.Stat.Stats;
import player.Player;

public class EquipementButton extends Button {

	Player m_player;
	Equipment m_equipement;
	EquipementScroll scroll;
	private final String EmptyImg = "resources/Village/HUD/EmptyCase.png";

	public EquipementButton(int x, int y, int w, int h, Player p, Equipment equipement, EquipementScroll scroll) {
		super(x, y, w, h);
		m_player = p;
		m_equipement = equipement;
		this.scroll = scroll;
		setBgImage(EmptyImg);
		setFgImage(equipement.getImagePath());
	}

	@Override
	public void action() throws Exception {
		if (m_equipement != null) {
			if (m_player.getMoney() >= m_equipement.getModification(Stats.Price)) {
				Equipment lastEquipement = m_player.addEquipment(m_equipement);
				if (lastEquipement != null) {
					m_player.setMoney(lastEquipement.getModification(Stats.Price));
				}

				m_player.setMoney(-m_equipement.getModification(Stats.Price));

			}
			m_equipement = null;
			setBgImage(EmptyImg);
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
