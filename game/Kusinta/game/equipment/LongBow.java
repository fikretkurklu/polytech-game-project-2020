package equipment;

import equipment.Stat.Stats;

public class LongBow extends Equipment {

	/*
	 * The LongBow is a dealing good damage but his attack speed is low
	 * 
	 */
	
	public LongBow() throws Exception {
		super();
		setImagePath();
		int rarity = statTable.get(Stats.Rarity);
		statTable.put(Stats.Price, 200+100*rarity);
		statTable.put(Stats.Weight, 100-10*rarity);
		statTable.put(Stats.Strengh, 20);
		statTable.put(Stats.AttackSpeed, 5);
		
	}

	@Override
	public void setImagePath() {
		imagePath = "resources/Pixel Art Icon Pack - RPG/Weapon & Tool/Bow.png";
	}
}
