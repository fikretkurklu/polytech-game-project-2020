package equipment;

import java.util.HashMap;
import equipment.Stat.Stats;

public abstract class Equipment {
	
	//This HashMap is used to contain the bonus that the equipment will give
	private HashMap<Stats, Integer> statTable;
	
	/*
	 * The equipment is built based on his rarity
	 * This basic price and weight depends of the rarity given
	 */
	
	public Equipment(int rarity) {
		statTable = new HashMap<Stats, Integer>();
		statTable.put(Stats.Rarity, rarity);
		statTable.put(Stats.Price, 0);
		statTable.put(Stats.Weight, 0);
		
	}
	
}
