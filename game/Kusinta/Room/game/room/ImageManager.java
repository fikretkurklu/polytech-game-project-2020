package game.room;

import java.util.HashMap;

public abstract class ImageManager {

	HashMap<String, String[]> imageTable;
	boolean useImagetable;
	String[] imgPath;
	
	public String[] get(String orientation, boolean useImageTable) {
		if (useImageTable) {
			String[] path = imageTable.get(orientation);
			return path;
		} else {
			return imgPath;
		}
	}
	
}
