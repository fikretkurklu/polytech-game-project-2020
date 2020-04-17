package game.room;

public class InnerWallImageManager implements ImageManager{
	
	public InnerWallImageManager (int ambiance, boolean useImageTable) {
		if (useImageTable) {
			switch(ambiance) {
			case(1) :
				String[] imgDir1_1 = {"","",""};
				String[] imgDir2_1 = {"","",""};
				String[] imgDir3_1 = {"","",""};
				String[] imgDir4_1 = {"","",""};
				imageTable.put("1", imgDir1_1);
				imageTable.put("2", imgDir2_1);
				imageTable.put("3", imgDir3_1);
				imageTable.put("4", imgDir4_1);
			case(2) :
				String[] imgDir1_2 = {"","",""};
				String[] imgDir2_2 = {"","",""};
				String[] imgDir3_2 = {"","",""};
				String[] imgDir4_2 = {"","",""};
				imageTable.put("1", imgDir1_2);
				imageTable.put("2", imgDir2_2);
				imageTable.put("3", imgDir3_2);
				imageTable.put("4", imgDir4_2);
			case(3) :
				String[] imgDir1_3 = {"","",""};
				String[] imgDir2_3 = {"","",""};
				String[] imgDir3_3 = {"","",""};
				String[] imgDir4_3 = {"","",""};
				imageTable.put("1", imgDir1_3);
				imageTable.put("2", imgDir2_3);
				imageTable.put("3", imgDir3_3);
				imageTable.put("4", imgDir4_3);
			case(4) :
				String[] imgDir1_4 = {"","",""};
				String[] imgDir2_4 = {"","",""};
				String[] imgDir3_4 = {"","",""};
				String[] imgDir4_4 = {"","",""};
				imageTable.put("1", imgDir1_4);
				imageTable.put("2", imgDir2_4);
				imageTable.put("3", imgDir3_4);
				imageTable.put("4", imgDir4_4);	
			}
		}
	}
	
}
