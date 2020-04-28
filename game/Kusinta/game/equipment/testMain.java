package equipment;

import equipment.Stat.Stats;

public class testMain {

	public static void main(String[] args) throws Exception {
		/*
		Armor m = new Armor();
		int rarity = m.getModification(Stats.Rarity);
		System.out.println("Rarity = " +rarity);
		int res = m.getModification(Stats.Resistance);
		System.out.println("Resistance = "+res);
		int price = m.getModification(Stats.Price);
		System.out.println("Price = "+price);
		m.applyMultiplier();
		int res2 = m.getModification(Stats.Resistance);
		System.out.println("Resistance 2 = "+res2);
		int price2 = m.getModification(Stats.Price);
		System.out.println("Price 2 = "+price2);
		*/
		for (int i = 0; i < 10; i++) {
			EquipmentManager m2 = new EquipmentManager();
			m2.newEquipment();
		}
	}

}
