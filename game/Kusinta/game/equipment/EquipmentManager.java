package equipment;

public class EquipmentManager {

	public static enum Conso {
		SmallHealthPotion, BigHealthPotion, SmallStatPotion, BigStatPotion
	};

	public static enum Stuff {
		Armor, Belt, Gloves, Grieves, Helmet, Bow
	};

	EquipmentImageManager imgManager;

	public EquipmentManager() {
		imgManager = new EquipmentImageManager();
	}

	public Equipment newEquipment() throws Exception {
		Stuff[] equipmentTable = Stuff.values();
		Stuff equipment = equipmentTable[(int) (Math.random() * (equipmentTable.length))];
		switch (equipment) {
		case Armor:
			Armor armor = new Armor(imgManager.StuffImage.get(Stuff.Armor));
			armor.applyMultiplier();
			return armor;
		case Belt:
			Belt belt = new Belt(imgManager.StuffImage.get(Stuff.Belt));
			belt.applyMultiplier();
			return belt;
		case Gloves:
			Gloves gloves = new Gloves(imgManager.StuffImage.get(Stuff.Gloves));
			gloves.applyMultiplier();
			return gloves;
		case Grieves:
			Grieves grieves = new Grieves(imgManager.StuffImage.get(Stuff.Grieves));
			grieves.applyMultiplier();
			return grieves;
		case Helmet:
			Helmet helmet = new Helmet(imgManager.StuffImage.get(Stuff.Helmet));
			helmet.applyMultiplier();
			return helmet;
		case Bow:
			if ((int) (Math.random() * 2) == 0) {
				LongBow bow = new LongBow(imgManager.StuffImage.get(Stuff.Bow));
				bow.applyMultiplier();
				return bow;
			} else {
				ShortBow bow = new ShortBow(imgManager.StuffImage.get(Stuff.Bow));
				bow.applyMultiplier();
				return bow;
			}
		default:
			System.out.println("The current equipment is not part of the equipment list");
			return null;
		}
	}

	public Consumable newConsumable(Conso consumable) throws Exception {
		switch (consumable) {
		case SmallHealthPotion:
			SmallHealthPotion sp = new SmallHealthPotion();
			sp.setImage(imgManager.PotionImage.get(Conso.SmallHealthPotion));
			return sp;
		case BigHealthPotion:
			BigHealthPotion bp = new BigHealthPotion();
			bp.setImage(imgManager.PotionImage.get(Conso.BigHealthPotion));
			return bp;
		default:
			System.out.println("This consumable doesn't exist");
			return null;
		}
	}

}
