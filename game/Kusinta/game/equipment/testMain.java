package equipment;

import equipment.Stat.Stats;

public class testMain {

	public static void main(String[] args) {
		Armor m = new Armor();
		int rarity = m.getModification(Stats.Rarity);
		System.out.println(rarity);
		int res = m.getModification(Stats.Resistance);
		System.out.println(res);
		m.applyMultiplier();
		int res2 = m.getModification(Stats.Resistance);
		System.out.println(res2);
	}

}
