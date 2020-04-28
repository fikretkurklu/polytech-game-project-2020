package equipment;

import equipment.Stat.Stats;

public class ShortBow extends Equipment {

	private final static String imagePath = "";
	
	public ShortBow() throws Exception {
		super();
		loadImage(imagePath);
		int rarity = statTable.get(Stats.Rarity);
		statTable.put(Stats.Price, 200+100*rarity);
		statTable.put(Stats.Weight, 200-20*rarity);
		statTable.put(Stats.Strengh, 5);
		statTable.put(Stats.AttackSpeed, 20);
		
	}
	
}
