package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.room.Room;


public class Model {
	int m_x, m_y, m_width, m_height;
	Room m_room;
	Model() throws IOException {
		m_room = new Room();
	}
	
	public void tick(long elapsed) {
		
	}
	
	public void paint(Graphics g, int width, int height) {
		m_room.paint(g);
	}
	
	/*
	 * Loading a sprite
	 */
	public static BufferedImage[] loadSprite(String filename, int nrows, int ncols) throws IOException {
	    File imageFile = new File(filename);
	    if (imageFile.exists()) {
	      BufferedImage image = ImageIO.read(imageFile);
	      int width = image.getWidth(null) / ncols;
	      int height = image.getHeight(null) / nrows;

	      BufferedImage[] images = new BufferedImage[nrows * ncols];
	      for (int i = 0; i < nrows; i++) {
	        for (int j = 0; j < ncols; j++) {
	          int x = j * width;
	          int y = i * height;
	          images[(i * ncols) + j] = image.getSubimage(x, y, width, height);
	        }
	      }
	      return images;
	    }
	    return null;
	  }
}
