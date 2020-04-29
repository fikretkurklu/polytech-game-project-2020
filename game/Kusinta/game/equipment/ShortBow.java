package equipment;

import equipment.EquipmentManager.Stuff;
import equipment.Stat.Stats;

public class ShortBow extends Equipment {
	
	/*
	 * The short bow shoots quickly but with low dmg
	 * 
	 */
	
	public ShortBow() throws Exception {
		super();
		setImagePath();
		int rarity = statTable.get(Stats.Rarity);
		statTable.put(Stats.Price, 200+100*rarity);
		statTable.put(Stats.Strengh, 5);
		statTable.put(Stats.AttackSpeed, 600);
		
	}

	@Override
	public void setImagePath() {
		imagePath = "resources/Equipment/Stuff/Bow.png";
		
	}

	@Override
	public Stuff toStuff() {
		// TODO Auto-generated method stub
		return Stuff.ShortBow;
	}
	
}
