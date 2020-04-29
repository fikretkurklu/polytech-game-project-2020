package equipment;

public class EquipmentManager {

	public static enum Conso {
		SmallHealthPotion, BigHealthPotion, SmallStatPotion, BigStatPotion
	};

	public static enum Stuff {
		Armor, Belt, Gloves, Grieves, Helmet, Bow
	};

	public Equipment newEquipment() throws Exception {
		Stuff[] equipmentTable = Stuff.values();
		Stuff equipment = equipmentTable[(int) (Math.random() * (equipmentTable.length))];
		switch (equipment) {
		case Armor:
			Armor armor = new Armor();
			armor.applyMultiplier();
			return armor;
		case Belt:
			Belt belt = new Belt();
			belt.applyMultiplier();
			return belt;
		case Gloves:
			Gloves gloves = new Gloves();
			gloves.applyMultiplier();
			return gloves;
		case Grieves:
			Grieves grieves = new Grieves();
			grieves.applyMultiplier();
			return grieves;
		case Helmet:
			Helmet helmet = new Helmet();
			helmet.applyMultiplier();
			return helmet;
		case Bow:
			if ((int)(Math.random() * 2) == 0) {
				LongBow bow = new LongBow();
				bow.applyMultiplier();
				return bow;
			} else {
				ShortBow bow = new ShortBow();
				bow.applyMultiplier();
				return bow;
			}
		default:
			System.out.println("The current equipment is not part of the equipment list");
			return null;
		}
	}

	public Consumable newConsumable() throws Exception {
		Conso[] conso = Conso.values();
		Conso consumable = conso[(int) (Math.random() * (conso.length))];
		switch (consumable) {
		case SmallHealthPotion :
			System.out.println("HealthPotion created");
			return new SmallHealthPotion();
		case BigHealthPotion :
			System.out.println("BigHealthPotion created");
			return new BigHealthPotion();
		case SmallStatPotion :
			System.out.println("StatPotion created");
			return new SmallStatPotion();
		case BigStatPotion :
			System.out.println("BigStatPotion created");
			return new BigStatPotion();
		default:
			System.out.println("This consumable doesn't exist");
			return null;
		}
	}

}
