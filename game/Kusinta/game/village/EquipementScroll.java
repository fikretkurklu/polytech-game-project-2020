package village;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import equipment.Equipment;
import equipment.Stat.Stats;

public class EquipementScroll {
	public final static int WIDTH_RATIO = 3;
	public final static int HEIGHT_RATIO = 3;
	int m_width, m_height, m_x, m_y;
	Image m_img;
	Font font;
	Equipment m_equipement;
	private static final String IMG_PATH = "resources/Village/HUD/Scroll.png";
	
	public EquipementScroll(int x, int y, int w, int h) {
		m_x = x;
		m_y = y;
		m_height = h;
		m_width = w;
		font = new Font("Georgia", Font.BOLD, 20);
		m_equipement = null;
		setImage();
	}
	@SuppressWarnings("unused")
	public void setImage() {
		try {
			File f = new File(IMG_PATH);
			if (f == null)
				System.out.println("Erreur while loading image at : "+ IMG_PATH);
			m_img = ImageIO.read(f);
			m_img = m_img.getScaledInstance(m_width, m_height, java.awt.Image.SCALE_SMOOTH);
			
		} catch (IOException e) {
			m_img = null;
			System.out.println("Error loadinf image");
		}
	}
	public void paint(Graphics g) {
		if (m_equipement != null) {
			g.setFont(font);
			g.drawImage(m_img, m_x, m_y, null);
			Stats[] statistic = Stats.values();
			for (int i = 0; i < statistic.length; i++) {
				String label = statistic[i].name() + " = " +  m_equipement.getModification(statistic[i]);
				g.drawString(label, m_x + m_width / 2 - (label.length() * font.getSize() / 3), m_y + m_height / 2 - font.getSize() * statistic.length / 2 + (i + 1) * font.getSize());
			}	
		}
		
	}
	
	public void resized(double ratio_w, double ratio_h) {
		m_width *= ratio_w;
		m_height *= ratio_h;
		m_x *= ratio_w;
		m_y *= ratio_h;
		if (m_img != null) {
			m_img = m_img.getScaledInstance(m_width, m_height, java.awt.Image.SCALE_SMOOTH);
		}
	}
	
	public void setEquipement(Equipment e) {
		m_equipement = e;
	}

}
