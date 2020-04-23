package environnement;

import java.util.HashMap;

public abstract class ImageManager {

	protected HashMap<String, String[]> imageTable;
	protected boolean useImageTable;
	protected String[] imgPath;
	
	public String get(String orientation) throws Exception {
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
