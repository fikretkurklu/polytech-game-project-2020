package equipment;

import equipment.EquipmentManager.Stuff;

public abstract class Bow extends Equipment {
	public Bow() {
		super();
	}

	@Override
	public Stuff toStuff() {
		// TODO Auto-generated method stub
		return Stuff.Bow;
	}
}
