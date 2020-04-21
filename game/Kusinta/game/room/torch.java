package room;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import automaton.Category;
import automaton.Direction;
import game.Coord;

public class torch extends Decor{

	public torch(Coord coord) {
		super(false, true, true, coord);
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
		m_images = new BufferedImage[4];
		try {
			m_imageIndex = 0;
			m_images[0] = ImageIO.read(new File("resources/Room/decors/torch2_1.png"));
			m_images[1] = ImageIO.read(new File("resources/Room/decors/torch2_2.png"));
			m_images[2] = ImageIO.read(new File("resources/Room/decors/torch2_3.png"));
			m_images[3] = ImageIO.read(new File("resources/Room/decors/torch2_4.png"));
			__image = m_images[m_imageIndex];
			getCoord().setX(getCoord().X() + (SIZE - __image.getWidth(null) / 2));
		      getCoord().setY(getCoord().Y() + (SIZE - __image.getHeight(null) / 2));
		} catch (IOException e) {
			System.out.println("Erreur while longind torch animation");
			e.printStackTrace();
		}
	}

	@Override
	public boolean move(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean jump(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pop(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean wizz(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean power() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pick(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean turn(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean get() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean store() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean explode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean egg(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hit(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mydir(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cell(Direction dir, Category cat) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closest(Category cat, Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean gotstuff() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean gotpower() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean key(int keyCode) {
		// TODO Auto-generated method stub
		return false;
	}

}
