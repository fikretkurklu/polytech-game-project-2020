package equipment;

import equipment.Stat.Stats;

public class LongBow extends Equipment {

	/*
	 * The LongBow is a dealing good damage but his attack speed is low
	 * 
	 */
	
	public LongBow() {
		super();
		statTable.put(Stats.Price, 200);
		statTable.put(Stats.Weight, 100);
		statTable.put(Stats.Strengh, 20);
		statTable.put(Stats.AttackSpeed, 5);
		
		int rarity = statTable.get(Stats.Rarity);
		//drop de l'objet - loi mathématique associée
		//int[] dropTable = {}
		
		//TODO associer la fonction mathématique
		
		//effet de la rareté sur l'ensemble
		
		//TODO faire l'effet de la rareté
	}
}
