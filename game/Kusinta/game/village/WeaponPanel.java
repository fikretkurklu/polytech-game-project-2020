package village;


public class WeaponPanel extends Panel {

	private String IMAGE_WEAPON_SHOP = "resources/Village/HUD/weaponShopBG.jpg";
	
	public WeaponPanel(int x, int y, int w, int h) {
		super(x, y, w, h);
		setImage(IMAGE_WEAPON_SHOP);
		int Scroll_w = w / Scroll.WIDTH_RATIO;
		int Scroll_h = h / Scroll.HEIGHT_RATIO;
		m_scroll = new Scroll(w / 2 - Scroll_w / 2 , 0 ,Scroll_w ,Scroll_h , "WEAPON SHOP");
	}
}
