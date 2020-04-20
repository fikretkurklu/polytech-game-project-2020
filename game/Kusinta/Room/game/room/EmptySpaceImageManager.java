package game.room;

public class EmptySpaceImageManager extends ImageManager {
	
	/*
	 * 
	 * The Empty Space image manager which defines the sprites used for empty spaces
	 * 
	 * 
	 */

	public EmptySpaceImageManager(int ambiance) {
		useImageTable = false;
		switch(ambiance) {
		case(1) :
			String[] path_1 = {"resources/Salle/A1/EmptySpace1.png","resources/Salle/A1/EmptySpace2.png"};
			this.imgPath = path_1;
		case(2) :
			String[] path_2 = {"resources/Salle/A2/EmptySpace1.png"};
			this.imgPath = path_2;			
		case(3) :
			String[] path_3 = {"","",""};
			this.imgPath = path_3;	
		case(4) :
			String[] path_4 = {"","",""};
			this.imgPath = path_4;				
		}
	}
	
}
