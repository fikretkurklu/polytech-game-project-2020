package equipment;

import equipment.Stat.Stats;
import game.Coord;

public class testMain {

	public static void main(String[] args) {
		Coord c = new Coord(2,3);
		Armor m = new Armor(c);
		int rarity = m.getModification(Stats.Rarity);
		System.out.println(rarity);
		int weight = m.getModification(Stats.Weight);
		System.out.println(weight);
	}

}
