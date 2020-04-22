package underworld;

import game.Coord;
import room.Element;

public class MapEmptySpace extends Element {

	public MapEmptySpace(Coord coord, MapEmptySpaceImageManager ESImageManager) throws Exception {
		super(false, true, coord);
		String path = ESImageManager.get("", ESImageManager.getUseImageTable());
		if (path != null) {
			loadImage(path);
		}
	}
}
