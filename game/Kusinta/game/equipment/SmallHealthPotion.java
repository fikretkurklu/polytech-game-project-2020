package equipment;

import java.awt.Image;

import equipment.EquipmentManager.Stuff;
import equipment.Stat.Stats;
import player.Character;

public class SmallHealthPotion extends Consumable {

	/*
	 * HealthPotion grants the player a bonus of health
	 * 
	 */
	
	public SmallHealthPotion(Image img) throws Exception {
		super();
		imageEquip = img;
		statTable.put(Stats.Price, 50);
		setModification();
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

	public void useOn(Character c) {
		int l = (statTable.get(Stats.Health)).intValue();
		c.winLife(l);
	}
}
