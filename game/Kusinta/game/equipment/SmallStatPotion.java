package equipment;

import equipment.EquipmentManager.Stuff;
import equipment.Stat.Stats;

public class SmallStatPotion extends Consumable {

	/*
	 * StatPotion give a random stat to the player between Strength, AttackSpeed and Resistance  
	 * 
	 */
	
	int statChoice;

	public SmallStatPotion() throws Exception {
		super();
		setImagePath();
		statTable.put(Stats.Price, 50);
		statChoice = (int) (Math.random() * 3);
	}

	@Override
	public void setModification() {
		switch (statChoice) {
		case (0):
			statTable.put(Stats.Strengh, 20);
			break;
		case (1):
			statTable.put(Stats.Resistance, 20);
		case (2):
			statTable.put(Stats.AttackSpeed, 10);
		}

	}

	@Override
	public void resetModification() {
		switch (statChoice) {
		case (0):
			statTable.put(Stats.Strengh, -20);
			break;
		case (1):
			statTable.put(Stats.Resistance, -20);
		case (2):
			statTable.put(Stats.AttackSpeed, -10);
		}

	}

	@Override
	public void setImagePath() {
		imagePath = "resources/Equipment/Potion/Green Potion.png";
	}

	@Override
	public String getImagePath() {
		return imagePath;
	}

	@Override
	public Stuff toStuff() {
		// TODO Auto-generated method stub
		return null;
	}

}
