package game.room;

public class Door extends Element {

	public Door(DoorImageManager DImageManager) throws Exception {
		super(true, true);
		String path = DImageManager.get("", DImageManager.useImageTable);
		if (path != null) {
			loadImage(path);
		}
	}
	
	public Door(Coord coord, DoorImageManager DImageManager) throws Exception {
		super(false, true, coord);
		String path = DImageManager.get("", DImageManager.useImageTable);
		if (path != null) {
			loadImage(path);
		}
	}

}

