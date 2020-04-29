package equipment;

import equipment.EquipmentManager.Stuff;
import equipment.Stat.Stats;

public class BigStatPotion extends Consumable {

	/*
	 * StatPotion give a random stat to the player between Strength, AttackSpeed and Resistance  
	 * 
	 */
	
	int statChoice;

	public BigStatPotion() throws Exception {
		super();
		setImagePath();
		statTable.put(Stats.Price, 100);
		statChoice = (int) (Math.random() * 3);
	}

	@Override
	public void setModification() {
		switch (statChoice) {
		case (0):
			statTable.put(Stats.Strengh, 50);
			break;
		case (1):
			statTable.put(Stats.Resistance, 50);
		case (2):
			statTable.put(Stats.AttackSpeed, 30);
		}

	}

	@Override
	public void resetModification() {
		switch (statChoice) {
		case (0):
			statTable.put(Stats.Strengh, -50);
			break;
		case (1):
			statTable.put(Stats.Resistance, -50);
		case (2):
			statTable.put(Stats.AttackSpeed, -30);
		}

	}

	@Override
	public void setImagePath() {
		imagePath = "resources/Equipment/Potion/Green Potion 3.png";
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
