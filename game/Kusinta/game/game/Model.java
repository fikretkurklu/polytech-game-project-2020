package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;


public class Model {
	
	int m_x, m_y, m_width, m_height;
	
	TimerListener tListener;
	
	Player m_player;
//	Opponent[] m_opponents;
	
	public Model() throws IOException {
		tListener = new TimerListener();
	}
	
	public void tick(long elapsed) {
		long ratio = elapsed / tListener.getTickTime() + 1;
		m_player.setRatio(ratio);
		
		
	}
	
	public boolean isColliding() {
		return false;
	}
	
	
	public void paint(Graphics g, int width, int height) {
		
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
	
	private class TimerListener implements ActionListener{
		
		private int tick_time = 10;
		private long m_last;
		Timer t;
		
		public TimerListener() {
			t= new Timer(tick_time, this);
			t.start();
		}

		public long getTickTime() {
			return tick_time;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			long now = System.currentTimeMillis();
			tick(now - m_last);
			t.restart();
			m_last = now;
		}
		
	}
	
}
