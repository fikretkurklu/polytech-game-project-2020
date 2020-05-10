package equipment;

import java.awt.Image;

import equipment.EquipmentManager.Stuff;
import equipment.Stat.Stats;

public class Armor extends Equipment {
	
	public Armor(Image img) throws Exception {
		super();
		imageEquip = img;
		int rarity = statTable.get(Stats.Rarity);
		statTable.put(Stats.Price, 100+rarity*300+200*(rarity-1));
		statTable.put(Stats.Health, 10 + 10*rarity);
		statTable.put(Stats.Resistance, 1 + 4*rarity);
	}
	
	
	public Stuff toStuff() {
		return Stuff.Armor;
	}
	
}
