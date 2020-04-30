package equipment;

import equipment.Stat.Stats;

public class ShortBow extends Bow {
	
	/*
	 * The short bow shoots quickly but with low dmg
	 * 
	 */
	
	public ShortBow() throws Exception {
		super();
		setImagePath();
		int rarity = statTable.get(Stats.Rarity);
		statTable.put(Stats.Price, 200+100*rarity);
		statTable.put(Stats.Strengh, 5);
		statTable.put(Stats.AttackSpeed, 1200);
		
	}

	@Override
	public void setImagePath() {
		imagePath = "resources/Equipment/Stuff/Bow.png";
		
	}
	
}
