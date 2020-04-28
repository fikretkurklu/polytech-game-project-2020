package equipment;

import equipment.Stat.Stats;

public class Belt extends Equipment {

	private final static String imagePath = "";
	
	public Belt() throws Exception {
		super();
		loadImage(imagePath);
		int rarity = statTable.get(Stats.Rarity);
		statTable.put(Stats.Price, 50+rarity*25);
		statTable.put(Stats.Weight, 30-3*rarity);
		statTable.put(Stats.Health, 5);
		statTable.put(Stats.Resistance, 5);
	}
}
