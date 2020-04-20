package game.room;

import java.util.HashMap;

public abstract class ImageManager {

	HashMap<String, String[]> imageTable;
	boolean useImageTable;
	String[] imgPath;
	
	public String get(String orientation, boolean useImageTable) {
		String[] pathTable;
		if (useImageTable) {
			pathTable = imageTable.get(orientation);
		} else {
			pathTable = imgPath;
		}
		int randomNum = (int) (Math.random()*pathTable.length);
		String img = pathTable[randomNum];
		return img;
	}
	
}
