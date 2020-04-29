package village;

import player.Player;

public class InfirmaryPanel extends Panel {
	public final String BG = "resources/Village/HUD/infirmaryBG.jpg";
	public InfirmaryPanel(int x, int y, int w, int h, Player p) {
		super(x, y, w, h, p);
		int Scroll_w = w / Scroll.WIDTH_RATIO;
		int Scroll_h = h / Scroll.HEIGHT_RATIO;
		m_scroll = new Scroll(w / 2 - Scroll_w / 2 , 0 ,Scroll_w ,Scroll_h , "INFIRMARY");
		setImage(BG);
	}

}
