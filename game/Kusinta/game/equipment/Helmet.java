package equipment;

import java.awt.Image;

import equipment.EquipmentManager.Stuff;
import equipment.Stat.Stats;

public class Helmet extends Equipment{
	
	public Helmet(Image img) throws Exception {
		super();
		imageEquip = img;
		int rarity = statTable.get(Stats.Rarity);
		statTable.put(Stats.Price, 150+150*rarity);
		statTable.put(Stats.Health, 10);
		statTable.put(Stats.Resistance, 10);
		statTable.put(Stats.Strengh, 5);
	}

	@Override
	public Stuff toStuff() {
		// TODO Auto-generated method stub
		return Stuff.Helmet;
	}
}
