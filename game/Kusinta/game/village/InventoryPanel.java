package village;

import player.Player;

public class InventoryPanel extends Panel {
	
	private Player m_player;
	public InventoryPanel(int x, int y, int w, int h, Player p) {
		super(x, y, w, h);
		int Scroll_w = w / Scroll.WIDTH_RATIO;
		int Scroll_h = h / Scroll.HEIGHT_RATIO;
		m_scroll = new Scroll(w / 2 - Scroll_w / 2 , 0 ,Scroll_w ,Scroll_h , "INVENTORY");
		m_player = p;
		m_playe
		for (int i = 0, i < m_player.m_)
	}

}
