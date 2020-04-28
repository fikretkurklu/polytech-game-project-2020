package equipment;

import equipment.Stat.Stats;

public class Belt extends Equipment {

	public Belt() throws Exception {
		super();
		setImagePath();
		int rarity = statTable.get(Stats.Rarity);
		statTable.put(Stats.Price, 50+rarity*25);
		statTable.put(Stats.Health, 5);
		statTable.put(Stats.Resistance, 5);
		statTable.put(Stats.Strengh, 5);
	}

	@Override
	public void setImagePath() {
		imagePath = "resources/Equipment/Stuff/Belt.png";
	}

}
