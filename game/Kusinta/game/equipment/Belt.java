package equipment;

import java.awt.Image;

import equipment.EquipmentManager.Stuff;
import equipment.Stat.Stats;

public class Belt extends Equipment {

	public Belt(Image img) throws Exception {
		super();
		imageEquip = img;
		int rarity = statTable.get(Stats.Rarity);
		statTable.put(Stats.Price, 100+rarity*100);
		statTable.put(Stats.Health, 5);
		statTable.put(Stats.Resistance, 5);
		statTable.put(Stats.Strengh, 5);
	}

	@Override
	public Stuff toStuff() {
		// TODO Auto-generated method stub
		return Stuff.Belt;
	}

}
