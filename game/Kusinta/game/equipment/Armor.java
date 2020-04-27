package equipment;

import equipment.Stat.Stats;

public class Armor extends Equipment {

	
	public Armor() {
		super();
		statTable.put(Stats.Price, 100);
		statTable.put(Stats.Weight, 200);
		statTable.put(Stats.Health, 20);
		statTable.put(Stats.Resistance, 10);
		
		int rarity = statTable.get(Stats.Rarity);
		float sigma = 50;
		float mu = 10;
		for (int i = 0; i < 10; i++) {
			float x = (i+1)*10;
			float e = (float) -1/2 * (float) Math.pow((float) ((x-mu)/sigma), 2);
			float d = (float) (1/(sigma*Math.sqrt(2*Math.PI)));
			float loiNormale = (float) (d * Math.exp(e));
			System.out.println(x);
			System.out.println(d);
			System.out.println(e);
			System.out.println(loiNormale);
		}
		
		//float loiNormale = (float) (1/(sigma*Math.sqrt(2*Math.PI)) * Math.exp(-1/2 * Math.pow((rarity-mu)/sigma, 2))); 
		//System.out.println(loiNormale);
		//drop de l'objet - loi mathématique associée
		//int[] dropTable = {}
		
		//TODO associer la fonction mathématique
		
		//effet de la rareté sur l'ensemble
		
		//TODO faire l'effet de la rareté
	}
	
}
