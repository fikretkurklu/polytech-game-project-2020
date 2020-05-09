package village;

import player.Player;

public class RollButton extends Button {

	private final String BG = "resources/Village/RollB.png";
	Panel m_panel;
	Player m_player;
	private int PRICE = 50;

	public RollButton(int x, int y, int w, int h, Panel panel, Player p) {
		super(x, y, w, h);
		m_panel = panel;
		m_player = p;
		setBgImage(BG);
	}

	@Override
	public void action() throws Exception {
		if (m_panel instanceof WeaponPanel) {
			if (m_player.getMoney() > PRICE) {
				((WeaponPanel) m_panel).drawEquipement();
				m_player.setMoney(-PRICE);
			}
		}

	}

}
