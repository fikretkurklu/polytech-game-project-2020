package village;

public class InfirmaryPanel extends Panel {

	public InfirmaryPanel(int x, int y, int w, int h) {
		super(x, y, w, h);
		int Scroll_w = w / Scroll.WIDTH_RATIO;
		int Scroll_h = h / Scroll.HEIGHT_RATIO;
		m_scroll = new Scroll(w / 2 - Scroll_w / 2 , 0 ,Scroll_w ,Scroll_h , "INFIRMARY");
	}

}