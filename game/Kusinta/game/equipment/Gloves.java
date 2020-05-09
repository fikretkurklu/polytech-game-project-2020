package equipment;

import java.awt.Image;

import equipment.EquipmentManager.Stuff;
import equipment.Stat.Stats;

public class Gloves extends Equipment{
	
	public Gloves(Image img) throws Exception {
		imageEquip = img;
		int rarity = statTable.get(Stats.Rarity);
		statTable.put(Stats.Price, 0+rarity*250);
		statTable.put(Stats.Strengh, 10);
		statTable.put(Stats.Resistance, 5);
	}

	@Override
	public Stuff toStuff() {
		// TODO Auto-generated method stub
		return Stuff.Gloves;
	}
	
}
