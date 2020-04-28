package equipment;

import equipment.Stat.Stats;

public class Grieves extends Equipment {
	
	public Grieves() throws Exception {
		super();
		setImagePath();
		int rarity = statTable.get(Stats.Rarity);
		statTable.put(Stats.Price, 50+25*rarity);
		statTable.put(Stats.Weight, 50-5*rarity);
		statTable.put(Stats.WeightReduction, 5);
		statTable.put(Stats.Resistance, 5);
		
	}

	@Override
	public void setImagePath() {
		imagePath = "resources/Pixel Art Icon Pack - RPG/Equipment/Iron Boot.png";
	}
}
