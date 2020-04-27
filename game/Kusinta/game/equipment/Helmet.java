package equipment;

import equipment.Stat.Stats;

public class Helmet extends Equipment{

	public Helmet() {
		super();
		statTable.put(Stats.Price, 100);
		statTable.put(Stats.Weight, 100);
		statTable.put(Stats.Health, 5);
		statTable.put(Stats.Resistance, 10);
		statTable.put(Stats.Strengh, 5);
		
		int rarity = statTable.get(Stats.Rarity);
		//drop de l'objet - loi mathématique associée
		//int[] dropTable = {}
		
		//TODO associer la fonction mathématique
		
		//effet de la rareté sur l'ensemble
		
		//TODO faire l'effet de la rareté
	}
}
