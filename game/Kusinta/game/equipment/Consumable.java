package equipment;

import java.awt.Image;

public abstract class Consumable extends Equipment {
	
	/*
	 * Consumable grants the player a temporary bonus
	 * 
	 */

	// This boolean is used to know if the consumable effect is activated
	boolean inUse;
	long duration;

	public Consumable() {
		super();
		inUse = false;
	}
	
	public void setImage(Image image) {
		imageEquip = image;
	}
		

}
