package village;

public class VillagePanel extends Panel{
	private String IMAGE_VILLAGE = "resources/Village/HUD/villageBG.jpg";
	
	public VillagePanel(int x, int y, int w, int h) {
		super(x, y, w, h);
		setImage(IMAGE_VILLAGE);
	}

}
