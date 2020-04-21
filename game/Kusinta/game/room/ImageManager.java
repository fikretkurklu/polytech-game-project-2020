package room;

import java.util.HashMap;

public abstract class ImageManager {

	HashMap<String, String[]> imageTable;
	boolean useImageTable;
	String[] imgPath;
	
	public String get(String orientation, boolean useImageTable) throws Exception {
		String[] pathTable;
		if (useImageTable) {
			pathTable = imageTable.get(orientation);
		} else {
			pathTable = imgPath;
		}
		if (pathTable!=null) {
			return pathTable[(int) (Math.random()*pathTable.length)];
		} else {
			throw new Exception("Empty table");
		}
	}
	
}
