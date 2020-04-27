package equipment;

import equipment.Stat.Stats;

public class ShortBow extends Equipment {

	public ShortBow() {
		super();
		statTable.put(Stats.Price, 100);
		statTable.put(Stats.Weight, 200);
		statTable.put(Stats.Strengh, 5);
		statTable.put(Stats.AttackSpeed, 20);
		
		int rarity = statTable.get(Stats.Rarity);
		//drop de l'objet - loi mathématique associée
		//int[] dropTable = {}
		
		//TODO associer la fonction mathématique
		
		//effet de la rareté sur l'ensemble
		
		//TODO faire l'effet de la rareté
	}
	
}
