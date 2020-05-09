package equipment;

import java.awt.Image;

import equipment.Stat.Stats;

public class ShortBow extends Bow {
	
	/*
	 * The short bow shoots quickly but with low dmg
	 * 
	 */
	
	public ShortBow(Image img) throws Exception {
		super();
		imageEquip = img;
		int rarity = statTable.get(Stats.Rarity);
		statTable.put(Stats.Price, 400+200*rarity);
		statTable.put(Stats.Strengh, 5);
		statTable.put(Stats.AttackSpeed, 600);
		
	}
	
}
