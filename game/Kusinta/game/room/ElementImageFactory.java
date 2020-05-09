package room;

import java.awt.Image;
import java.util.HashMap;

import environnement.Element;
import game.ImageLoader;

public class ElementImageFactory {
	
	private String room_path = "resources/Room/";
	int ambiance;
	
	HashMap<TypeBG, Image> hmImage;
	
	public enum TypeBG { OWN, OWS, OWE, OWW, OWNE, OWNW, OWSE, OWSW, IW, ES}
	
	public ElementImageFactory() throws Exception {
		ambiance = (int) (Math.random() * 3 + 1);
		hmImage = new HashMap<ElementImageFactory.TypeBG, Image>();
		loadHashMap();
	}
	
	public void loadHashMap() throws Exception {
		for (TypeBG t : TypeBG.values()) {
			String path = room_path + "A" + ambiance + "/" + t.toString() + ".png";
			Image i = ImageLoader.loadImage(path, Element.SIZE);
			hmImage.put(t, i);
		}
	}
	
	public Image getImage(TypeBG t) {
		return hmImage.get(t);
	}
	
	
}

