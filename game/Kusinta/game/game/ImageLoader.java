package game;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import environnement.Element;
import underworld.UnderworldParam;

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
	
	/**
	 * Method for loading basic image
	 * @param path path to the image to laod
	 * @param width with of the final image
	 * @param height height of the final image
	 * @return the method return an Image variable which contains the image loaded
	 * @throws Exception when an error occur when trying to load the image
	 */
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
	
	/**
	 * Method for loading image elements needed in the class Soul.java
	 * @param image_index
	 * @param imageElapsed
	 * @param sizeAnimation
	 * @param sizeDeathAnimation
	 * @param sizeDashAnimation
	 * @param sizeEscapeAnimation
	 * @return
	 */
	public static Image[] loadImagePlayerSoul(int image_index, long imageElapsed, int sizeAnimation, int sizeDeathAnimation, int sizeDashAnimation, int sizeEscapeAnimation) {
		Image m_images[] = new Image[sizeDeathAnimation];
		File imageFile;
		image_index = 0;
		imageElapsed = 0;
		try {
			for (int i = 0; i < sizeAnimation; i++) {
				imageFile = new File(UnderworldParam.playerSoulImage[i]);
				m_images[i] = ImageIO.read(imageFile);
			}
			for (int j = sizeAnimation; j < sizeDashAnimation; j++) {
				imageFile = new File(UnderworldParam.lureApparitionImage[j - sizeAnimation]);
				m_images[j] = ImageIO.read(imageFile);
			}
			for (int k = sizeDashAnimation; k < sizeEscapeAnimation; k++) {
				imageFile = new File(UnderworldParam.playerSoulEscapeImage[k - sizeDashAnimation]);
				m_images[k] = ImageIO.read(imageFile);
			}
			for (int l = sizeEscapeAnimation; l < 6 + sizeEscapeAnimation; l++) {
				imageFile = new File(UnderworldParam.playerSoulDeathImage[l - sizeEscapeAnimation]);
				m_images[l] = ImageIO.read(imageFile);
			}
			BufferedImage[] deathTmp = ImageLoader.loadBufferedSprite(UnderworldParam.deathSprite, 7, 7);
			int index = 6 + sizeEscapeAnimation;
			for (int m = 14; m < 42; m++) {
				m_images[index] = deathTmp[m];
				index++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return m_images;
	}
	
	/**
	 * Method for loading image elements needed in the class Lure.java
	 * @param apparitionImages
	 * @param disaparitionImages
	 * @param sizeApearingAnimation
	 * @param sizeAnimation
	 * @param imageIndex
	 * @param imageElapsed
	 * @return
	 */
	public static Image[] loadImageLure(Image[] apparitionImages, Image[] disaparitionImages, int sizeApearingAnimation, int sizeAnimation, int imageIndex, long imageElapsed) {
		Image m_images[] = new Image[sizeAnimation];
		apparitionImages = new Image[sizeApearingAnimation];
		disaparitionImages = new Image[sizeApearingAnimation];
		File imageFile;
		imageIndex = 0;
		imageElapsed = 0;
		try {
			for (int i = 0; i < sizeApearingAnimation; i++) {
				imageFile = new File(UnderworldParam.lureApparitionImage[i]);
				apparitionImages[i] = ImageIO.read(imageFile);
				disaparitionImages[6 - i] = ImageIO.read(imageFile);
			}
			for (int j = 0; j < sizeAnimation; j++) {
				imageFile = new File(UnderworldParam.lureImage[j]);
				m_images[j] = ImageIO.read(imageFile);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return m_images;
	}
	
	/**
	 * Method for loading image elements needed in the class Ghost.java
	 * @param size
	 * @param width
	 * @param height
	 * @param image_index
	 * @param imageElapsed
	 * @param leftOrientation
	 * @return
	 */
	public static Image[] loadImageGhost(int size, int width, int height, int image_index, long imageElapsed, boolean leftOrientation) {
		width = size;
		height = size;
		int len = UnderworldParam.ghostImage.length;
		Image images[] = new Image[len];
		File imageFile;
		Image image;
		image_index = 0;
		imageElapsed = 0;
		leftOrientation = false;
		try {
			for (int i = 0; i < len; i++) {
				imageFile = new File(UnderworldParam.ghostImage[i]);
				image = ImageIO.read(imageFile);
				images[i] = image.getScaledInstance(size, size, 0);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return images;
	}
}
