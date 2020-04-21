package game.room;

public class DoorImageManager extends ImageManager {

	/*
	 * 
	 * The Door image manager which defines the sprites used for doors
	 * 
	 * 
	 */

	public DoorImageManager(int ambiance) {
		useImageTable = false;
		switch(ambiance) {
		case(1) :
			String[] path_1 = {"resources/Room/A1/Door1.png","resources/Room/A1/Door2.png"};
			this.imgPath = path_1;
			break;
		case(2) :
			String[] path_2 = {"resources/Room/A2/Door1.png"};
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
