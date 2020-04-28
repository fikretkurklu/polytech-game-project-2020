package equipment;

import equipment.Stat.Stats;

public class Armor extends Equipment {

	private final static String imagePath = "";
	
	public Armor() throws Exception {
		super();
		loadImage(imagePath);
		int rarity = statTable.get(Stats.Rarity);
		statTable.put(Stats.Price, 100+rarity*50);
		statTable.put(Stats.Weight, 200-rarity*20);
		statTable.put(Stats.Health, 20);
		statTable.put(Stats.Resistance, 20);
	}
	
}
