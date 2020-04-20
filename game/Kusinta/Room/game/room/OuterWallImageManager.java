package game.room;

import java.util.HashMap;

public class OuterWallImageManager extends ImageManager {

	public OuterWallImageManager (int ambiance) {
			imageTable = new HashMap<String, String[]>();
			switch(ambiance) {
			case(1) :
				String[] imgN_1 = {"resources/Salle/A1/OuterWallN.png"};
				String[] imgS_1 = {"resources/Salle/A1/OuterWallS.png"};
				String[] imgW_1 = {"resources/Salle/A1/OuterWallW.png"};
				String[] imgE_1 = {"resources/Salle/A1/OuterWallE.png"};
				String[] imgNE_1 = {"resources/Salle/A1/OuterWallNE.png"};
				String[] imgNW_1 = {"resources/Salle/A1/OuterWallNW.png"};
				String[] imgSE_1 = {"resources/Salle/A1/OuterWallSE.png"};
				String[] imgSW_1 = {"resources/Salle/A1/OuterWallSW.png"};
				imageTable.put("1", imgN_1);
				imageTable.put("2", imgS_1);
				imageTable.put("3", imgW_1);
				imageTable.put("4", imgE_1);
				imageTable.put("5", imgNE_1);
				imageTable.put("6", imgNW_1);
				imageTable.put("7", imgSE_1);
				imageTable.put("8", imgSW_1);
			case(2) :
				String[] imgN_2 = {"","",""};
				String[] imgS_2 = {"","",""};
				String[] imgW_2 = {"","",""};
				String[] imgE_2 = {"","",""};
				String[] imgNE_2 = {"","",""};
				String[] imgNW_2 = {"","",""};
				String[] imgSE_2 = {"","",""};
				String[] imgSW_2 = {"","",""};
				imageTable.put("1", imgN_2);
				imageTable.put("2", imgS_2);
				imageTable.put("3", imgW_2);
				imageTable.put("4", imgE_2);
				imageTable.put("5", imgNE_2);
				imageTable.put("6", imgNW_2);
				imageTable.put("7", imgSE_2);
				imageTable.put("8", imgSW_2);
			case(3) :
				String[] imgN_3 = {"","",""};
				String[] imgS_3 = {"","",""};
				String[] imgW_3 = {"","",""};
				String[] imgE_3 = {"","",""};
				String[] imgNE_3 = {"","",""};
				String[] imgNW_3 = {"","",""};
				String[] imgSE_3 = {"","",""};
				String[] imgSW_3 = {"","",""};
				imageTable.put("1", imgN_3);
				imageTable.put("2", imgS_3);
				imageTable.put("3", imgW_3);
				imageTable.put("4", imgE_3);
				imageTable.put("5", imgNE_3);
				imageTable.put("6", imgNW_3);
				imageTable.put("7", imgSE_3);
				imageTable.put("8", imgSW_3);
			case(4) :
				String[] imgN_4 = {"","",""};
				String[] imgS_4 = {"","",""};
				String[] imgW_4 = {"","",""};
				String[] imgE_4 = {"","",""};
				String[] imgNE_4 = {"","",""};
				String[] imgNW_4 = {"","",""};
				String[] imgSE_4 = {"","",""};
				String[] imgSW_4 = {"","",""};
				imageTable.put("1", imgN_4);
				imageTable.put("2", imgS_4);
				imageTable.put("3", imgW_4);
				imageTable.put("4", imgE_4);
				imageTable.put("5", imgNE_4);
				imageTable.put("6", imgNW_4);
				imageTable.put("7", imgSE_4);
				imageTable.put("8", imgSW_4);
			}
			String[] path = {"","",""};
			this.imgPath = path;
		}

	
	
	
}
