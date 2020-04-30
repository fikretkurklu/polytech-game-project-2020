package village;

import player.Player;

public class VillagePanel extends Panel{
	private String IMAGE_VILLAGE = "resources/Village/HUD/villageBG.jpg";
	
	public VillagePanel(int x, int y, int w, int h, Player p) {
		super(x, y, w, h, p);
		setImage(IMAGE_VILLAGE);
		int Scroll_w = w / Scroll.WIDTH_RATIO;
		int Scroll_h = h / Scroll.HEIGHT_RATIO;
		m_scroll = new Scroll(w / 2 - Scroll_w / 2 , 0 ,Scroll_w ,Scroll_h , "VILLAGE");
	}

}
