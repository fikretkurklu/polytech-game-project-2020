package underworld;

import room.ImageManager;

public class MapEmptySpaceImageManager extends ImageManager{
	public MapEmptySpaceImageManager(int ambiance) {
		useImageTable = false;
		switch(ambiance) {
		case(1) :
			String[] path_1 = {"resources/Room/A1/EmptySpace1.png","resources/Room/A1/EmptySpace2.png"};
			this.imgPath = path_1;
			break;
		case(2) :
			String[] path_2 = {"resources/Room/A2/EmptySpace1.png"};
			this.imgPath = path_2;		
			break;
		case(3) :
			String[] path_3 = {"","",""};
			this.imgPath = path_3;	
		case(4) :
			String[] path_4 = {"","",""};
			this.imgPath = path_4;	
			break;
		}
	}
}
