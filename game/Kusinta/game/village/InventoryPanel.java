package village;

public class InventoryPanel extends Panel {

	public InventoryPanel(int x, int y, int w, int h) {
		super(x, y, w, h);
		int Scroll_w = w / Scroll.WIDTH_RATIO;
		int Scroll_h = h / Scroll.HEIGHT_RATIO;
		m_scroll = new Scroll(w / 2 - Scroll_w / 2 , 0 ,Scroll_w ,Scroll_h , "INVENTORY");
	}

}
