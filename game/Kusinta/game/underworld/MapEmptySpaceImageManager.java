package underworld;

import environnement.ImageManager;

public class MapEmptySpaceImageManager extends ImageManager{
	public MapEmptySpaceImageManager(int ambiance) {
		setUseImageTable(false);
		switch(ambiance) {
		case(1) :
			String[] path_1 = {"resources/Map/background.png"};
			setImgPath(path_1);
			break;
		case(2) :
			String[] path_2 = {"resources/Map/background.png"};
			setImgPath(path_2);
			break;
		case(3) :
			String[] path_3 = {"","",""};
			setImgPath(path_3);
		case(4) :
			String[] path_4 = {"","",""};
			setImgPath(path_4);
			break;
		}
	}
}
