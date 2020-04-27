package equipment;

public interface Stat {

	/*
	 * -Rarity influence the drop rate of stats on a weapon
	 * -Price is the amount of money required for the item
	 * -Weight is the weight of the item which influence the player mouvement speed
	 * -Resistance is a percentage of damage reduction
	 * -Strengh is the stat that influence the damage the player will deal with his arrow
	 * -AttackSpeed is a stat that affects the speed at which the player throws arrow
	 * -Health influences the player's global health
	 * -WeightReduction is a percentage that reduces the player's weight to increase his mouvement speed
	 * 
	 */
	
	public enum Stats {Rarity, Price, Weight, Resistance, Strengh, AttackSpeed, Health, WeightReduction, DropMultiplier}; 
	
}
