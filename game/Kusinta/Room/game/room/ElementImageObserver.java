package game.room;

import java.awt.Image;
import java.awt.image.ImageObserver;

public class ElementImageObserver implements ImageObserver {
	
	Coord m_coord;
	Image m_img;
	boolean isChanged;
	
	public ElementImageObserver (Coord coord, Image img) {
		m_coord = coord;
		m_img = img;
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		isChanged = false;
		Coord c = new Coord(x, y);
		if (!m_coord.isEqual(c)) {
			m_coord = c;
			isChanged = true;
		}
		if (m_img.getWidth(null) != width || m_img.getHeight(null) != height) {
			m_img = img.getScaledInstance(width, height, 0);
			isChanged = true;
		}
		return isChanged;
	}

}
