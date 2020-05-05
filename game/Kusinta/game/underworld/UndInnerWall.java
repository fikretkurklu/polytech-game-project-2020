package underworld;

import environnement.Element;
import game.Coord;
import game.ImageLoader;

public class UndInnerWall extends Element {
	public UndInnerWall(Coord coord, UndInnerWallManager IWImageManager) throws Exception {
		super(true, true, coord);
		String path = IWImageManager.get("");
		if (path != null) {
			__image = ImageLoader.loadImage(path, SIZE);
		}
	}
}
