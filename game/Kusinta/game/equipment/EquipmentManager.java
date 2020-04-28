package equipment;

public class EquipmentManager {

	protected enum Conso{HealthPotion, StatPotion};
	protected enum Stuff{Armor, Belt, Gloves, Grieves, Helmet, LongBow, ShortBow};
	
	public Equipment newEquipment() throws Exception {
		Stuff[] equipmentTable = Stuff.values();
		Stuff equipment = equipmentTable[(int) (Math.random()*(equipmentTable.length))];
		switch(equipment) {
		case Armor :
			System.out.println("Armor created");
			return new Armor();
		case Belt :
			System.out.println("Belt created");
			return new Belt();
		case Gloves :
			System.out.println("Gloves created");
			return new Gloves();
		case Grieves :
			System.out.println("Grieves created");
			return new Grieves();
		case Helmet :
			System.out.println("Helmet created");
			return new Helmet();
		case LongBow :
			System.out.println("LongBow created");
			return new LongBow();
		case ShortBow :
			System.out.println("ShortBow created");
			return new ShortBow();
		default:
			System.out.println("The current equipment is not part of the equipment list");
			return null;
		}
	}
	
	public Equipment newEquipement(String equipment) throws Exception {
		switch(equipment) {
		case ("Armor") :
			return new Armor();
		case ("Helmet") :
			return new Helmet();
		case ("Gloves") :
			return new Gloves();
		case ("Grieves") :
			return new Grieves();
		case ("Belt") :
			return new Belt();
		case ("LongBow") :
			return new LongBow();
		case("ShortBow") :
			return new ShortBow();
		default :
			System.out.println("The given name is not an equipement");
			return null;
		}
	}
	
	//public Consumable newConsumable();
	
	//public Consumable newConsumable(String consumable);
	
}
