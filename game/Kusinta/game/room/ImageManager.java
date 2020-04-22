package room;

import java.nio.file.Path;
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
	
	public boolean getUseImageTable() {
		return useImageTable;
	}
	
	public String[] getImgPath() {
		return imgPath;
	}
	
	public void setUseImageTable(boolean b) {
		useImageTable = b;
	}
	public void setImgPath(String[] p) {
		imgPath = p;
	}
 	
}
