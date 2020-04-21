package room;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class torch extends Decor{

	public torch(boolean isSolid, boolean isVisible) {
		super(isSolid, isVisible);
		isAnimated = true;
		loadImages();
	}
	
	@Override
	public void tick(long elapsed) {
		if (isAnimated) {
			m_imageElapsed += elapsed;
		    if (m_imageElapsed > 200) {
		      m_imageElapsed = 0;
		      m_imageIndex = (m_imageIndex + 1) % m_images.length;
		      __image = m_images[m_imageIndex];
		      getCoord().setX(getCoord().X() + (SIZE - __image.getWidth(null) / 2));
		      getCoord().setY(getCoord().Y() + (SIZE - __image.getHeight(null) / 2));
		    }
		}
	
	}
	
	private void loadImages() {
		m_images = new BufferedImage[4];
		try {
			m_images[0] = ImageIO.read(new File("resources/Room/decors/torch2_1.png"));
			m_images[1] = ImageIO.read(new File("resources/Room/decors/torch2_2.png"));
			m_images[2] = ImageIO.read(new File("resources/Room/decors/torch2_3.png"));
			m_images[3] = ImageIO.read(new File("resources/Room/decors/torch2_4.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
