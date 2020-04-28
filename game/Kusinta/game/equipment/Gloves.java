package equipment;

import equipment.Stat.Stats;

public class Gloves extends Equipment{

	private final static String imagePath = "";
	
	public Gloves() throws Exception {
		super();
		loadImage(imagePath);
		int rarity = statTable.get(Stats.Rarity);
		statTable.put(Stats.Price, 50+rarity*25);
		statTable.put(Stats.Weight, 50-rarity*5);
		statTable.put(Stats.Strengh, 5);
		statTable.put(Stats.Resistance, 5);
	}
	
}
