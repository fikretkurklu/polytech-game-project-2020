package equipment;


import equipment.EquipmentManager.Stuff;
import equipment.Stat.Stats;
import player.Character;

public class SmallHealthPotion extends Consumable {

	/*
	 * HealthPotion grants the player a bonus of health
	 * 
	 */
	
	public SmallHealthPotion() throws Exception {
		super();
		statTable.put(Stats.Price, 50);
	}

System.out.println
	

	public void useOn(Character c) {
		int l = (statTable.get(Stats.Health)).intValue();
		c.winLife(l);
	}




	@Override
	public Stuff toStuff() {
		// TODO Auto-generated method stub
		return null;
	}
}
