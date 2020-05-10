package equipment;

import java.awt.Image;

import equipment.EquipmentManager.Stuff;
import equipment.Stat.Stats;

public class Helmet extends Equipment{
	
	public Helmet(Image img) throws Exception {
		super();
		imageEquip = img;
		int rarity = statTable.get(Stats.Rarity);
		statTable.put(Stats.Price, 100+300*rarity+200*(rarity-1));
		statTable.put(Stats.Health, 10 + 5*rarity);
		statTable.put(Stats.Resistance, 2 + 3*rarity);
		statTable.put(Stats.Strengh, 1 + 2*rarity);
	}

	@Override
	public Stuff toStuff() {
		// TODO Auto-generated method stub
		return Stuff.Helmet;
	}
}
