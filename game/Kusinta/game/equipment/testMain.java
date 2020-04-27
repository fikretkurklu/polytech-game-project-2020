package equipment;

import equipment.Stat.Stats;

public class testMain {

	public static void main(String[] args) {
		Armor m = new Armor();
		int rarity = m.getModification(Stats.Rarity);
		System.out.println(rarity);
		int weight = m.getModification(Stats.Weight);
		System.out.println(weight);
		m.applyMultiplier();
	}

}
