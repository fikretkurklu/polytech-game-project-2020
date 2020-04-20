package game.room;

import java.awt.Image;
import java.awt.image.ImageObserver;

public class ElementImageObserver implements ImageObserver {
	
	Coord m_coord;
	Image m_img;
	
	public ElementImageObserver (Coord coord, Image img) {
		m_coord = coord;
		m_img = img;
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		m_img = img.getScaledInstance(width, height, 0);
		return true;
	}

}
