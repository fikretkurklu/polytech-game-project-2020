package equipment;

import equipment.Stat.Stats;
import game.Coord;

public class Belt extends Equipment {

	public Belt(Coord coord) {
		super(coord);
		statTable.put(Stats.Price, 50);
		statTable.put(Stats.Weight, 30);
		statTable.put(Stats.Health, 5);
		statTable.put(Stats.Resistance, 5);
		
		int rarity = statTable.get(Stats.Rarity);
		//drop de l'objet - loi mathématique associée
		//int[] dropTable = {}
		
		//TODO associer la fonction mathématique
		
		//effet de la rareté sur l'ensemble
		
		//TODO faire l'effet de la rareté
	}
}
