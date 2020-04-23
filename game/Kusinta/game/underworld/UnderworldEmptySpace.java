package underworld;

import game.Coord;
import environnement.Element;

public class UnderworldEmptySpace extends Element {

	public UnderworldEmptySpace(Coord coord, UnderworldEmptySpaceImageManager ESImageManager) throws Exception {
		super(false, true, coord);
		String path = ESImageManager.get("");
		if (path != null) {
			loadImage(path);
		}
	}
}
