package equipment;

import equipment.Stat.Stats;
import game.Coord;

public class Armor extends Equipment {

	
	public Armor(Coord coord) {
		super(coord);
		statTable.put(Stats.Price, 100);
		statTable.put(Stats.Weight, 200);
		statTable.put(Stats.Health, 20);
		statTable.put(Stats.Resistance, 10);
		
		int rarity = statTable.get(Stats.Rarity);
		//drop de l'objet - loi mathématique associée
		//int[] dropTable = {}
		
		//TODO associer la fonction mathématique
		
		//effet de la rareté sur l'ensemble
		
		//TODO faire l'effet de la rareté
	}
	
}
