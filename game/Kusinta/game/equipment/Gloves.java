package equipment;

import java.awt.Image;

import equipment.EquipmentManager.Stuff;
import equipment.Stat.Stats;

public class Gloves extends Equipment{
	
	public Gloves(Image img) throws Exception {
		imageEquip = img;
		int rarity = statTable.get(Stats.Rarity);
		statTable.put(Stats.Price, 0+rarity*250);
		statTable.put(Stats.Strengh, 1 + 3*rarity);
		statTable.put(Stats.Resistance, 1 + 4*rarity);
	}

	@Override
	public Stuff toStuff() {
		// TODO Auto-generated method stub
		return Stuff.Gloves;
	}
	
}
