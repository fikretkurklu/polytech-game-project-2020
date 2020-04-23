package room;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import automaton.Automaton;
import environnement.Decor;
import game.Coord;

public class Torch extends Decor{

	public Torch(Coord coord, Room room, Automaton automaton) {
		super(false,true, true, coord, room, automaton);
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
		    }
		}
	
	}
	
	private void loadImages() {
		m_images = new Image[4];
		try {
			File imageFile;
			m_imageIndex = 0;			
			imageFile = new File("resources/Room/decors/torch2_1.png");
			m_images[0] = ImageIO.read(imageFile);
			m_images[0] = m_images[0].getScaledInstance(SIZE, SIZE, 0);
			imageFile = new File("resources/Room/decors/torch2_2.png");
			m_images[1] = ImageIO.read(imageFile);
			m_images[1] = m_images[1].getScaledInstance(SIZE, SIZE, 0);
			imageFile = new File("resources/Room/decors/torch2_3.png");
			m_images[2] = ImageIO.read(imageFile);
			m_images[2] = m_images[2].getScaledInstance(SIZE, SIZE, 0);
			imageFile = new File("resources/Room/decors/torch2_4.png");
			m_images[3] = ImageIO.read(imageFile);
			m_images[3] = m_images[3].getScaledInstance(SIZE, SIZE, 0);
			__image = m_images[m_imageIndex];
			
			getCoord().setX(getCoord().X() + (SIZE - __image.getWidth(null)) / 2);
		    getCoord().setY(getCoord().Y() + (SIZE - __image.getHeight(null)) / 2);
		} catch (IOException e) {
			System.out.println("Erreur while longind torch animation");
			e.printStackTrace();
		}
	}

}
