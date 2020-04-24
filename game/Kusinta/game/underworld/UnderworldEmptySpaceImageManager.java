package underworld;

import environnement.ImageManager;



public class UnderworldEmptySpaceImageManager extends ImageManager{
	public UnderworldEmptySpaceImageManager(int ambiance) {
		useImageTable = false;
		switch(ambiance) {
		case(1) :
			String[] path_1 = {"resources/Underworld/Floor/floor2.png"};
			imgPath = path_1;
			break;
		case(2) :
			String[] path_2 = {"resources/Underworld/empty1.png"};
			imgPath = path_2;
			break;
		case(3) :
			String[] path_3 = {"","",""};
			imgPath = path_3;
		case(4) :
			String[] path_4 = {"","",""};
			imgPath = path_4;
			break;
		}
	}
}
