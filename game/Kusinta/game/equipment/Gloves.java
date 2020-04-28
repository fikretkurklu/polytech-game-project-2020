package equipment;

import equipment.Stat.Stats;

public class Gloves extends Equipment{
	
	public Gloves() throws Exception {
		super();
		setImagePath();
		int rarity = statTable.get(Stats.Rarity);
		statTable.put(Stats.Price, 50+rarity*25);
		statTable.put(Stats.Weight, 50-rarity*5);
		statTable.put(Stats.Strengh, 5);
		statTable.put(Stats.Resistance, 5);
	}

	@Override
	public void setImagePath() {
		imagePath = "resources/Pixel Art Icon Pack - RPG/Weapon & Tool/Wooden Shield.png";
	}
	
}
