package equipment;

import equipment.Stat.Stats;

public class StatPotion extends Consumable {

	/*
	 * StatPotion give a random stat to the player between Strength, AttackSpeed and Resistance  
	 * 
	 */
	
	int statChoice;

	public StatPotion() throws Exception {
		super();
		setImagePath();
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
			statTable.put(Stats.Strengh, 0);
			break;
		case (1):
			statTable.put(Stats.Resistance, 0);
		case (2):
			statTable.put(Stats.AttackSpeed, 0);
		}

	}

	@Override
	public void setImagePath() {
		imagePath = "resources/Pixel Art Icon Pack - RPG/Potion/Green Potion 2.png";
	}

	@Override
	public String getImagePath() {
		return imagePath;
	}

}
