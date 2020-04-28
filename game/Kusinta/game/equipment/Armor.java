package equipment;

import equipment.Stat.Stats;

public class Armor extends Equipment {
	
	public Armor() throws Exception {
		super();
		setImagePath();
		int rarity = statTable.get(Stats.Rarity);
		statTable.put(Stats.Price, 100+rarity*50);
		statTable.put(Stats.Weight, 200-rarity*20);
		statTable.put(Stats.Health, 20);
		statTable.put(Stats.Resistance, 20);
	}

	@Override
	public void setImagePath() {
		imagePath = "resources/Pixel Art Icon Pack - RPG/Equipment/Iron Armor.png";	
	}	

	
}
