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
		statTable.put(Stats.Price, 200+700*rarity+300*(rarity-1));
		statTable.put(Stats.Strengh, 1 + 3*rarity);
		statTable.put(Stats.AttackSpeed, 200+200*rarity);
		
	}
	
}
