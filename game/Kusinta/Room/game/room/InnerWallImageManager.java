package game.room;

public class InnerWallImageManager extends ImageManager{
	
	/*
	 * 
	 * The image manager concerning the InnerWall, which use the imgPath cause there is no direciton
	 * 
	 * 
	 */
	
	public InnerWallImageManager (int ambiance) {
		useImageTable = false; 
		switch(ambiance) {
		case(1) :
			String[] path_1 = {"resources/Salle/A1/InnerWall1.png","resources/Salle/A1/InnerWall2.png"};
			this.imgPath = path_1;
		case(2) :
			String[] path_2 = {"resources/Salle/A2/InnerWall1.png","resources/Salle/A2/InnerWall2.png"};
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
