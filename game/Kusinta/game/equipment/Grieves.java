package equipment;

import equipment.Stat.Stats;

public class Grieves extends Equipment {
	
	public Grieves() throws Exception {
		super();
		setImagePath();
		int rarity = statTable.get(Stats.Rarity);
		statTable.put(Stats.Price, 50+25*rarity);
		statTable.put(Stats.Resistance, 10);
		statTable.put(Stats.Health, 5);
	}

	@Override
	public void setImagePath() {
		imagePath = "resources/Equipment/Stuff/Iron Boot.png";
	}
}
