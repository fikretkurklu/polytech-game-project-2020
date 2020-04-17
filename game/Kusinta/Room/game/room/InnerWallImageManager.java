package game.room;

public class InnerWallImageManager extends ImageManager{
	
	public InnerWallImageManager (int ambiance) {
		switch(ambiance) {
		case(1) :
			String[] imgDir1 = {"","",""};
			String[] imgDir2 = {"","",""};
			String[] imgDir3= {"","",""};
			String[] imgDir4 = {"","",""};
			imageTable.put("1", imgDir1);
			imageTable.put("2", imgDir2);
			imageTable.put("3", imgDir3);
			imageTable.put("4", imgDir4);
		case(2) :
			String[] imgDir1 = {"","",""};
			String[] imgDir2 = {"","",""};
			String[] imgDir3= {"","",""};
			String[] imgDir4 = {"","",""};
			imageTable.put("1", imgDir1);
			imageTable.put("2", imgDir2);
			imageTable.put("3", imgDir3);
			imageTable.put("4", imgDir4);
		}
	}
	
}
