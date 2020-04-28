package equipment;

import java.awt.Image;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;

import equipment.Stat.Stats;

public abstract class Equipment {

	public static final int SIZE = 86;

	// This HashMap is used to contain the bonus that the equipment will give
	protected HashMap<Stats, Integer> statTable;
	protected Image __img;

	/*
	 * The equipment is built by randomly picking a rarity The rarity will affect
	 * the object drop of stats The basic price and stats of an object depend of his
	 * type
	 * 
	 */

	public Equipment() {
		int rarity = (int) (Math.random() * 9 + 1);
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
	 * This method sets the multiplier to be used in the weapon drop of stats
	 * 
	 */

	public float setMultiplier() {
		int rarity = statTable.get(Stats.Rarity);
		float var_borne = (float) rarity / 10 * 2;
		float borne_inf = (float) (1 + var_borne);
		float borne_sup = (borne_inf) * 2;
		return (float) (Math.random() * (borne_sup - borne_inf) + borne_inf);
	}

	/*
	 * This method is used to apply a different multiplier to all Statistic of the
	 * equipment (the price will evolve with the different multiplier encountered)
	 * The weight and the rarity will not change with the different mutipliers
	 * 
	 */

	public void applyMultiplier() {
		Stats[] statistic = Stats.values();
		for (int i = 0; i < statistic.length - 1; i++) {
			float multiplier = setMultiplier();
			Stats s = statistic[i];
			if (s != null) {
				int val_init = statTable.get(s);
				int val_price = statTable.get(Stats.Price);
				if (!s.equals(Stats.Weight) && !s.equals(Stats.Rarity)) {
					statTable.put(s, (int) (val_init * multiplier));
					if (val_init != 0) {
						statTable.put(Stats.Price, (int) (val_price + 20 * multiplier));

					}
				}
			}
		}
	}

	/*
	 * This method returns the integer associated with the statistic you give That
	 * is to say the value of this statistic on the weapon
	 */

	public int getModification(Stats s) {
		return statTable.get(s);
	}

	/*
	 * This method is used to load the image for the equipment and resize it to the
	 * given size
	 */

	public void loadImage(String path) throws Exception {
		File imageFile = new File(path);
		if (imageFile.exists()) {
			__img = ImageIO.read(imageFile);
			__img = __img.getScaledInstance(SIZE, SIZE, java.awt.Image.SCALE_SMOOTH);
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
