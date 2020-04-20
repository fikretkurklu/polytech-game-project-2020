package game.room;

public class InnerWallImageManager extends ImageManager{
	
	public InnerWallImageManager (int ambiance) {
		switch(ambiance) {
		case(1) :
			String[] path_1 = {"","",""};
			this.imgPath = path_1;
		case(2) :
			String[] path_2 = {"","",""};
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
