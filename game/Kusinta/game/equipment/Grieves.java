package equipment;

import equipment.Stat.Stats;

public class Grieves extends Equipment {

	public Grieves() {
		super();
		int rarity = statTable.get(Stats.Rarity);
		statTable.put(Stats.Price, 50+25*rarity);
		statTable.put(Stats.Weight, 50-5*rarity);
		statTable.put(Stats.WeightReduction, 5);
		statTable.put(Stats.Resistance, 5);
		
	}
}
