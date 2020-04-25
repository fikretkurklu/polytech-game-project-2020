package equipment;

import game.Coord;

public abstract class Consumable extends Equipment {

	//TODO discussion sur les consommables
	//Faut-il que le joueur vérifie à chaque tick s'il a des modication de stats ?
	
	
	/*
	 * Consumable grants the player a temporary bonus
	 * 
	 */

	// This boolean is used to know if the consumable effect is activated
	boolean inUse;
	long duration;

	public Consumable(Coord coord) {
		super(coord);
		inUse = false;
	}
	
	/*
	 * This method is used to follow the potion effect 
	 * 
	 */

	public void tick(long elapsed) {
		if (inUse) {
			duration += elapsed;
			if (elapsed >= 1000) {
				resetModification();
				inUse = false;
				duration = 0;
			}
		} 
	}

	/*
	 * This method is used to consume the consumable effect
	 * 
	 */
		
	public void use() {
		inUse = true;
		setModification();
	}
		
	/*
	 * This method is used to set the modification of the potion. Those
	 * modifications are not set in the constructor because the stat modication
	 * should only be applied when the potion is used.
	 * 
	 */

	public abstract void setModification();

	/*
	 * This method resets all the stats used by the consumable to 0
	 * 
	 */
	
	public abstract void resetModification();
}
