package game;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import environnement.Element;
import underworld.Ghost;
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
	public static Image loadImage(String path) throws Exception {
		File imageFile = new File(path);
		
		if (imageFile.exists()) {
			return ImageIO.read(imageFile);
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
	public static Image[] loadPlayerImage() {
		Image[] images = new Image[UnderworldParam.sizePlayerDeathAnimation];
		File imageFile;
		try {
			for (int i = 0; i < UnderworldParam.sizePlayerAnimation; i++) {
				imageFile = new File(UnderworldParam.playerSoulImage[i]);
				images[i] = ImageIO.read(imageFile);
			}
			for (int j = UnderworldParam.sizePlayerAnimation; j < UnderworldParam.sizePlayerDashAnimation; j++) {
				imageFile = new File(UnderworldParam.lureApparitionImage[j - UnderworldParam.sizePlayerAnimation]);
				images[j] = ImageIO.read(imageFile);
			}
			for (int k = UnderworldParam.sizePlayerDashAnimation; k < UnderworldParam.sizePlayerEscapeAnimation; k++) {
				imageFile = new File(UnderworldParam.playerSoulEscapeImage[k - UnderworldParam.sizePlayerDashAnimation]);
				images[k] = ImageIO.read(imageFile);
			}
			for (int l = UnderworldParam.sizePlayerEscapeAnimation; l < 6 + UnderworldParam.sizePlayerEscapeAnimation; l++) {
				imageFile = new File(UnderworldParam.playerSoulDeathImage[l - UnderworldParam.sizePlayerEscapeAnimation]);
				images[l] = ImageIO.read(imageFile);
			}
			BufferedImage[] deathTmp = ImageLoader.loadBufferedSprite(UnderworldParam.deathSprite, 7, 7);
			int index = 6 + UnderworldParam.sizePlayerEscapeAnimation;
			for (int m = 14; m < 42; m++) {
				images[index] = deathTmp[m];
				index++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return images;
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
	public static Image[] loadLureImage() {
		Image[] images = new Image[UnderworldParam.sizeLureDisaparitionAnimation];
		File imageFile;
		try {
			for (int i = 0; i < UnderworldParam.sizeLureAnimation; i++) {
				imageFile = new File(UnderworldParam.lureImage[i]);
				images[i] = ImageIO.read(imageFile);
			}
			for (int j = UnderworldParam.sizeLureAnimation; j < UnderworldParam.sizeLureApearingAnimation; j++) {
				imageFile = new File(UnderworldParam.lureApparitionImage[j - UnderworldParam.sizeLureAnimation]);
				images[j] = ImageIO.read(imageFile);
			}
			int l = UnderworldParam.lureApparitionImage.length - 1;
			for (int k = UnderworldParam.sizeLureApearingAnimation; k < UnderworldParam.sizeLureDisaparitionAnimation; k++) {
				imageFile = new File(UnderworldParam.lureApparitionImage[l]);
				images[k] = ImageIO.read(imageFile);
				l--;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return images;
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
	public static Image[] loadGhostImage() {
		int len = UnderworldParam.ghostImage.length;
		Image[] images;
		images = new Image[len];
		File imageFile;
		Image image;
		for (int i = 0; i < len; i++) {
			imageFile = new File(UnderworldParam.ghostImage[i]);
			try {
				image =  ImageIO.read(imageFile);
				images[i] = image.getScaledInstance(Ghost.SIZE, Ghost.SIZE, 0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return images;
	}
}
