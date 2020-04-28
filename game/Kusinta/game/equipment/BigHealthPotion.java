package equipment;

import equipment.Stat.Stats;

public class BigHealthPotion extends Consumable {

	/*
	 * HealthPotion grants the player a bonus of health
	 * 
	 */
	
	public BigHealthPotion() throws Exception {
		super();
		setImagePath();
		statTable.put(Stats.Price, 100);
	}

	@Override
	public void setModification() {
		statTable.put(Stats.Health,50);
	}

	@Override
	public void resetModification() {
		statTable.put(Stats.Health, -50);
	}

	@Override
	public void setImagePath() {
		imagePath = "resources/Equipment/Potion/Red Potion 3.png";
	}
	
}
