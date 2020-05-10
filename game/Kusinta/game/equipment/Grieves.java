package equipment;

import java.awt.Image;

import equipment.EquipmentManager.Stuff;
import equipment.Stat.Stats;

public class Grieves extends Equipment {
	
	public Grieves(Image img) throws Exception {
		super();
		imageEquip = img;
		int rarity = statTable.get(Stats.Rarity);
		statTable.put(Stats.Price, 50+200*rarity+200*(rarity-1));
		statTable.put(Stats.Resistance, 2 + 5*rarity);
		statTable.put(Stats.Health, 5 + 5*rarity);
	}
	
	@Override
	public Stuff toStuff() {
		// TODO Auto-generated method stub
		return Stuff.Grieves;
	}
}
