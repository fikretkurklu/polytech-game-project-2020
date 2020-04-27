package equipment;

import java.awt.Image;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;

import equipment.Stat.Stats;

public abstract class Equipment {
	
	public static final int SIZE = 86;
	
	//This HashMap is used to contain the bonus that the equipment will give
	protected HashMap<Stats, Integer> statTable;
	protected Image __img;
	
	/*
	 * The equipment is built by randomly picking a rarity
	 * The rarity will affect the object drop of stats
	 * The basic price and stats of an object depend of his type
	 * 
	 */
	
	public Equipment() {
		int rarity = (int) (Math.random()*9+1);
		statTable = new HashMap<Stats, Integer>();
		statTable.put(Stats.Rarity, rarity);
		statTable.put(Stats.Price, 0);
		statTable.put(Stats.Weight, 0);
		statTable.put(Stats.Resistance, 0);
		statTable.put(Stats.Strengh, 0);
		statTable.put(Stats.AttackSpeed, 0);
		statTable.put(Stats.Health, 0);
		statTable.put(Stats.WeightReduction, 0);
	}
	
	/*
	 * This method returns the integer associated with the statistic you give
	 * That is to say the value of this statistic on the weapon
	 */
	
	public int getModification(Stats s) {
		return statTable.get(s);
	}
	
	/*
	 *This method is used to load the image for the equipment and resize it to the given size
	 */
	
	public void loadImage(String path) throws Exception {
		File imageFile = new File(path);
		if (imageFile.exists()) {
			__img = ImageIO.read(imageFile);
			__img= __img.getScaledInstance(SIZE, SIZE, java.awt.Image.SCALE_SMOOTH);
		} else {
			throw new Exception("Error while loading image: path = " + path);
		}
	}
	
	/*
	 * This method allow to resize the object to follow the window resize
	 * 
	 */
	
	public void resize(int width, int height) {
		if (__img.getHeight(null) != height || __img.getWidth(null) != width) {
			__img = __img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		}
	}
	
}
