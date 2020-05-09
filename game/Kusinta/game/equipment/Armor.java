package equipment;

import java.awt.Image;

import equipment.EquipmentManager.Stuff;
import equipment.Stat.Stats;

public class Armor extends Equipment {
	
	public Armor(Image img) throws Exception {
		super();
		imageEquip = img;
		int rarity = statTable.get(Stats.Rarity);
		statTable.put(Stats.Price, 200+rarity*200);
		statTable.put(Stats.Health, 20);
		statTable.put(Stats.Resistance, 20);
	}
	
	
	public Stuff toStuff() {
		return Stuff.Armor;
	}
	
}
