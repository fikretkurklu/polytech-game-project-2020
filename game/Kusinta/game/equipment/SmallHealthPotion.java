package equipment;

import equipment.Stat.Stats;

public class SmallHealthPotion extends Consumable {

	/*
	 * HealthPotion grants the player a bonus of health
	 * 
	 */
	
	public SmallHealthPotion() throws Exception {
		super();
		setImagePath();
		statTable.put(Stats.Price, 50);
	}

	@Override
	public void setModification() {
		statTable.put(Stats.Health,20);
	}

	@Override
	public void resetModification() {
		statTable.put(Stats.Health, -20);
	}

	@Override
	public void setImagePath() {
		imagePath = "resources/Equipment/Potion/Red Potion.png";
	}

}
