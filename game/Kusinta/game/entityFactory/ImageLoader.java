package entityFactory;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import environnement.Element;


public class ImageLoader {

	/**
	 * Method for loading image for projectile
	 * @param path path to the image to load used for projectile
	 * @return the method return an Image variable which contains the projectile image
	 * @throws Exception when an error occur when trying to load the image
	 */
	public static Image loadImageProjectile(String path) throws Exception {
		File imageFile = new File(path);
		Image imageProjectile = null;
		if (imageFile.exists()) {
			imageProjectile = ImageIO.read(imageFile);
			double ratio = (double) imageProjectile.getHeight(null) / (double) imageProjectile.getWidth(null);
			imageProjectile = imageProjectile.getScaledInstance((int) (1.5 * Element.SIZE * ratio),
					(int) (1.5 * Element.SIZE), 0);
			
		} else {
			throw new Exception("Error while loading image: path = " + path);
		}
		
		return imageProjectile;
	}
	
	/**
	 * Method for loading basic image
	 * @param path path to the image to load
	 * @param size the size of the image
	 * @return the method return an Image variable which contains the image loaded
	 * @throws Exception when an error occur when trying to load the image
	 */
	public static Image loadImage(String path, int size) throws Exception {
		File imageFile = new File(path);
		Image img;
		if (imageFile.exists()) {
			img = ImageIO.read(imageFile);
			img = img.getScaledInstance(size, size, 0);
		} else {
			throw new Exception("Error while loading image: path = " + path);
		}
		return img;
	}
	
	public static Image loadImage(String path, int width, int height) throws Exception {
		File imageFile = new File(path);
		if (imageFile.exists()) {
			Image img;
			img = ImageIO.read(imageFile);
			img = img.getScaledInstance(width, height, 0);
			return img;
		} else {
			throw new Exception("Error while loading image: path = " + path);
		}
		
		
	}
	
	/**
	 * Method for loading basic image
	 * @param path path to the image to laod
	 * @param width with of the final image
	 * @param height height of the final image
	 * @return the method return an Image variable which contains the image loaded
	 * @throws Exception when an error occur when trying to load the image
	 */
	public static Image loadImage(String path){
		try {
			File imageFile = new File(path);
			if (imageFile.exists()) {
				return ImageIO.read(imageFile);
			} 
			return null;
		} catch (IOException e) {
			System.out.println("Error while loading image: path = " + path);
			return null;
		}
	}
	
	/**
	 * Method for loading sprite, Buffered type
	 * @param filename the name of the file/path to the image to load
	 * @param nrows number of rows in sprite array
	 * @param ncols number of colons in sprite array
	 * @return the method return a BufferedImage array which contains the Sprite loaded
	 * @throws IOException when an error occur when trying to load the image
	 */
	public static BufferedImage[] loadBufferedSprite(String filename, int nrows, int ncols) throws IOException {
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
	
	/**
	 * Method for loading sprite, Image type
	 * @param filename the name of the file/path to the image to load
	 * @param nrows number of rows in sprite array
	 * @param ncols number of colons in sprite array
	 * @return the method return a Image array which contains the Sprite loaded
	 * @throws IOException when an error occur when trying to load the image
	 */
	public static Image[] loadImageSprite(String filename, int nrows, int ncols) throws IOException {
		File imageFile = new File(filename);
		if (imageFile.exists()) {
			BufferedImage image = ImageIO.read(imageFile);
			int width = image.getWidth(null) / ncols;
			int height = image.getHeight(null) / nrows;

			Image[] images = new Image[nrows * ncols];
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
	
	public static Image[] loadImageSprite(String filename, int nrows, int ncols, int finalWidth, int finalHeight) throws IOException {
		File imageFile = new File(filename);
		if (imageFile.exists()) {
			BufferedImage image = ImageIO.read(imageFile);
			int width = image.getWidth(null) / ncols;
			int height = image.getHeight(null) / nrows;

			Image[] images = new Image[nrows * ncols];
			for (int i = 0; i < nrows; i++) {
				for (int j = 0; j < ncols; j++) {
					int x = j * width;
					int y = i * height;
					images[(i * ncols) + j] = image.getSubimage(x, y, width, height);
					images[(i * ncols) + j] = images[(i * ncols) + j].getScaledInstance(finalWidth, finalHeight, 0);
				}
			}
			return images;
		}
		return null;
	}
}
