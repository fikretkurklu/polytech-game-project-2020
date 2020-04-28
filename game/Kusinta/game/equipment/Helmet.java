package equipment;

import equipment.Stat.Stats;

public class Helmet extends Equipment{
	
	public Helmet() throws Exception {
		super();
		setImagePath();
		int rarity = statTable.get(Stats.Rarity);
		statTable.put(Stats.Price, 100+50*rarity);
		statTable.put(Stats.Weight, 100-10*rarity);
		statTable.put(Stats.Health, 5);
		statTable.put(Stats.Resistance, 10);
		statTable.put(Stats.Strengh, 5);

	}

	@Override
	public void setImagePath() {
		imagePath = "resources/Pixel Art Icon Pack - RPG/Equipment/Helm.png";	
	}
}
