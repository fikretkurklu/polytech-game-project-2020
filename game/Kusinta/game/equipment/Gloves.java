package equipment;

import equipment.EquipmentManager.Stuff;
import equipment.Stat.Stats;

public class Gloves extends Equipment{
	
	public Gloves() throws Exception {
		super();
		setImagePath();
		int rarity = statTable.get(Stats.Rarity);
		statTable.put(Stats.Price, 50+rarity*25);
		statTable.put(Stats.Strengh, 10);
		statTable.put(Stats.Resistance, 5);
	}

	@Override
	public void setImagePath() {
		imagePath = "resources/Equipment/Stuff/Wooden Shield.png";
	}

	@Override
	public Stuff toStuff() {
		// TODO Auto-generated method stub
		return Stuff.Gloves;
	}
	
}
