package equipment;

import java.awt.Image;
import java.util.HashMap;
import game.ImageLoader;

import equipment.EquipmentManager.Stuff;

public class EquipmentImageManager {

	HashMap<Stuff, Image> StuffImage;
	
	public EquipmentImageManager() throws Exception{
		StuffImage = new HashMap<Stuff, Image>();
		Image imgArmor = ImageLoader.loadImage("resources/Equipment/Stuff/Iron Armor.png");
		Image imgGloves = ImageLoader.loadImage("resources/Equipment/Stuff/Wooden Shield.png");
		Image imgHelmet = ImageLoader.loadImage("resources/Equipment/Stuff/Helm.png");
		Image imgGrieves = ImageLoader.loadImage("resources/Equipment/Stuff/Iron Boot.png");
		Image imgBow = ImageLoader.loadImage("resources/Equipment/Stuff/Bow.png");
		Image imgBelt = ImageLoader.loadImage("resources/Equipment/Stuff/Belt.png");
		StuffImage.put(Stuff.Armor, imgArmor);
		StuffImage.put(Stuff.Armor, imgGloves);
		StuffImage.put(Stuff.Armor, imgHelmet);
		StuffImage.put(Stuff.Armor, imgGrieves);
		StuffImage.put(Stuff.Armor, imgBow);
		StuffImage.put(Stuff.Armor, imgBelt);
	}
	
}
