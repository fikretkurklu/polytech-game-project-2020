package equipment;

import equipment.EquipmentManager.Stuff;
import equipment.Stat.Stats;
import player.Character;

public class BigHealthPotion extends Consumable {

	/*
	 * HealthPotion grants the player a bonus of health
	 * 
	 */
	
	public BigHealthPotion() throws Exception {
		super();
		statTable.put(Stats.Price, 100);
		setModification();
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
	public Stuff toStuff() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void useOn(Character c) {
		int l = (statTable.get(Stats.Health)).intValue();
		c.winLife(l);
	}
	
}
