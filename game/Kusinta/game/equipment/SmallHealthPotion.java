package equipment;

import equipment.EquipmentManager.Stuff;
import equipment.Stat.Stats;

public class SmallHealthPotion extends Consumable {

	/*
	 * HealthPotion grants the player a bonus of health
	 * 
	 */
	
	public SmallHealthPotion() throws Exception {
		super();
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
	public Stuff toStuff() {
		// TODO Auto-generated method stub
		return null;
	}

}
