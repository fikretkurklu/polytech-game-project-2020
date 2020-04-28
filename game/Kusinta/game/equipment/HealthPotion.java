package equipment;

import equipment.Stat.Stats;

public class HealthPotion extends Consumable {

	/*
	 * HealthPotion grants the player a bonus of health
	 * 
	 */
	
	private final static String imagePath = "";
	
	public HealthPotion() throws Exception {
		super();
		loadImage(imagePath);
	}

	@Override
	public void setModification() {
		statTable.put(Stats.Health,20);
	}

	@Override
	public void resetModification() {
		statTable.put(Stats.Health, 0);
	}

}
