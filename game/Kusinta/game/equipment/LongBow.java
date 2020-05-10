package equipment;

import java.awt.Image;

import equipment.Stat.Stats;

public class LongBow extends Bow {

	/*
	 * The LongBow is a dealing good damage but his attack speed is low
	 * 
	 */
	
	public LongBow(Image img) throws Exception {
		super();
		imageEquip = img;
		int rarity = statTable.get(Stats.Rarity);
		statTable.put(Stats.Price, 200+700*rarity+300*(rarity-1));
		statTable.put(Stats.Strengh, 10 + 5*rarity);
		statTable.put(Stats.AttackSpeed, 200);
		
	}


}
