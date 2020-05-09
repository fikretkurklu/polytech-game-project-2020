package equipment;

import java.awt.Image;
import java.util.HashMap;

import entityFactory.ImageLoader;
import equipment.EquipmentManager.Conso;
import equipment.EquipmentManager.Stuff;

public class EquipmentImageManager {

	HashMap<Stuff, Image> StuffImage;
	HashMap<Conso, Image> PotionImage;
	
	public EquipmentImageManager(){
		StuffImage = new HashMap<Stuff, Image>();
		PotionImage = new HashMap<Conso, Image>();
		try {
			Image imgArmor = ImageLoader.loadImage("resources/Equipment/Stuff/Iron Armor.png");
			Image imgGloves = ImageLoader.loadImage("resources/Equipment/Stuff/Gloves.png");
			Image imgHelmet = ImageLoader.loadImage("resources/Equipment/Stuff/Helm.png");
			Image imgGrieves = ImageLoader.loadImage("resources/Equipment/Stuff/Iron Boot.png");
			Image imgBow = ImageLoader.loadImage("resources/Equipment/Stuff/Bow.png");
			Image imgBelt = ImageLoader.loadImage("resources/Equipment/Stuff/Belt.png");
			Image imgSPotion = ImageLoader.loadImage("resources/Equipment/Potion/Green Potion.png");
			Image imgBPotion = ImageLoader.loadImage("resources/Equipment/Potion/Red Potion.png");
			PotionImage.put(Conso.BigHealthPotion, imgBPotion);
			PotionImage.put(Conso.SmallHealthPotion, imgSPotion);
			StuffImage.put(Stuff.Armor, imgArmor);
			StuffImage.put(Stuff.Gloves, imgGloves);
			StuffImage.put(Stuff.Helmet, imgHelmet);
			StuffImage.put(Stuff.Grieves, imgGrieves);
			StuffImage.put(Stuff.Bow, imgBow);
			StuffImage.put(Stuff.Belt, imgBelt);
		} catch (Exception e) {
			System.out.println("error while creating Equipement Image Manager");
		}
		
	}
	
}
