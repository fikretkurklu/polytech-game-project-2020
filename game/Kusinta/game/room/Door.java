package room;

import java.awt.Graphics;
import java.awt.Rectangle;

import automaton.Automaton;
import environnement.Decor;
import game.Coord;
import game.ImageLoader;

public class Door extends Decor {

	int m_width, m_height;

	public Door(Coord coord, DoorImageManager DImageManager, Room room, Automaton automaton) throws Exception {
		super(false, false, true, coord, room, automaton);
		String path = DImageManager.get("");
		if (path != null) {
			__image = ImageLoader.loadImage(path, SIZE);
		}
		m_width = (int) (__image.getWidth(null) * 1.5);
		m_height = (int) (__image.getHeight(null) * 1.5);

		m_hitBox = new Rectangle(m_coord.X() - 20, (m_coord.Y() + __image.getHeight(null)) - m_height - 20,
				m_width + 40, m_height + 20);
	}

	@Override
	public boolean activate() {
		System.out.println("Activate");

		m_room.isChanged = true;
		return m_room.isChanged;
	}

	@Override
	public void tick(long elapsed) {
		// TODO Auto-generated method stub

	}

	public void paint(Graphics g) {
		if (__isVisible) {
			int w = (int) (1.5 * m_width);
			int h = (int) (1.5 * m_height);
			int ratioX = (m_hitBox.width / 2 - w / 2);
			int x = m_hitBox.x + ratioX;
			int y = m_hitBox.y + m_hitBox.height - h;
			g.drawImage(__image, x, y, w, h, null);
//			g.drawRect(m_hitBox.x, m_hitBox.y, m_hitBox.width, m_hitBox.height);
		}

	}

}
