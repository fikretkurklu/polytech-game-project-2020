package game.room;

import java.util.HashMap;

public class OuterWallImageManager extends ImageManager {
	
	/*
	 * 
	 * 	 * In the HashMap :
	 * - N is associated to North
	 * - S is associated to South
	 * - W is associated to West
	 * - E is associated to East
	 * - NE is associated to North East
	 * - NW is associated to North West
	 * - SE is associated to South East
	 * - SW is associated to South West
	 * 
	 * 
	 */

	public OuterWallImageManager (int ambiance) {
		useImageTable =  true;
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
			imageTable.put("N", imgN_1);
			imageTable.put("S", imgS_1);
			imageTable.put("W", imgW_1);
			imageTable.put("E", imgE_1);
			imageTable.put("NE", imgNE_1);
			imageTable.put("NW", imgNW_1);
			imageTable.put("SE", imgSE_1);
			imageTable.put("SW", imgSW_1);
		case(2) :
			String[] imgN_2 = {"resources/Salle/A2/OuterWallN.png"};
			String[] imgS_2 = {"resources/Salle/A2/OuterWallS.png"};
			String[] imgW_2 = {"resources/Salle/A2/OuterWallW.png"};
			String[] imgE_2 = {"resources/Salle/A2/OuterWallE.png"};
			String[] imgNE_2 = {"resources/Salle/A2/OuterWallNE.png"};
			String[] imgNW_2 = {"resources/Salle/A2/OuterWallNW.png"};
			String[] imgSE_2 = {"resources/Salle/A2/OuterWallSE.png"};
			String[] imgSW_2 = {"resources/Salle/A2/OuterWallSW.png"};
			imageTable.put("N", imgN_2);
			imageTable.put("S", imgS_2);
			imageTable.put("W", imgW_2);
			imageTable.put("E", imgE_2);
			imageTable.put("NE", imgNE_2);
			imageTable.put("NW", imgNW_2);
			imageTable.put("SE", imgSE_2);
			imageTable.put("SW", imgSW_2);
		case(3) :
			String[] imgN_3 = {"","",""};
			String[] imgS_3 = {"","",""};
			String[] imgW_3 = {"","",""};
			String[] imgE_3 = {"","",""};
			String[] imgNE_3 = {"","",""};
			String[] imgNW_3 = {"","",""};
			String[] imgSE_3 = {"","",""};
			String[] imgSW_3 = {"","",""};
			imageTable.put("N", imgN_3);
			imageTable.put("S", imgS_3);
			imageTable.put("W", imgW_3);
			imageTable.put("E", imgE_3);
			imageTable.put("NE", imgNE_3);
			imageTable.put("NW", imgNW_3);
			imageTable.put("SE", imgSE_3);
			imageTable.put("SW", imgSW_3);
		case(4) :
			String[] imgN_4 = {"","",""};
			String[] imgS_4 = {"","",""};
			String[] imgW_4 = {"","",""};
			String[] imgE_4 = {"","",""};
			String[] imgNE_4 = {"","",""};
			String[] imgNW_4 = {"","",""};
			String[] imgSE_4 = {"","",""};
			String[] imgSW_4 = {"","",""};
			imageTable.put("N", imgN_4);
			imageTable.put("S", imgS_4);
			imageTable.put("W", imgW_4);
			imageTable.put("E", imgE_4);
			imageTable.put("NE", imgNE_4);
			imageTable.put("NW", imgNW_4);
			imageTable.put("SE", imgSE_4);
			imageTable.put("SW", imgSW_4);
		}
	}

	
	
	
}
