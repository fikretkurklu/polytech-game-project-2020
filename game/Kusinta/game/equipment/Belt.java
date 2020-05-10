package equipment;

import java.awt.Image;

import equipment.EquipmentManager.Stuff;
import equipment.Stat.Stats;

public class Belt extends Equipment {

	public Belt(Image img) throws Exception {
		super();
		imageEquip = img;
		int rarity = statTable.get(Stats.Rarity);
		statTable.put(Stats.Price, 0+rarity*250);
		statTable.put(Stats.Health, 2+5*rarity);
		statTable.put(Stats.Resistance, 1 + 4*rarity);
		statTable.put(Stats.Strengh, 1 + 2*rarity);
	}

	@Override
	public Stuff toStuff() {
		// TODO Auto-generated method stub
		return Stuff.Belt;
	}

}
