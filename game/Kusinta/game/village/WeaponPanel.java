package village;

public class WeaponPanel extends Panel {

	private String IMAGE_WEAPON_SHOP = "resources/Village/HUD/weaponShopBG.jpg";

	public WeaponPanel(int x, int y, int w, int h) {
		super(x, y, w, h);
		setImage(IMAGE_WEAPON_SHOP);
	}

}
