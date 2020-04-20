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
		String[] imgN;
		String[] imgS;
		String[] imgE;
		String[] imgW;
		String[] imgNE;
		String[] imgNW;
		String[] imgSE;
		String[] imgSW;
		switch(ambiance) {
		case(1) :
			imgN = new String[]{"resources/Salle/A1/OuterWallN.png"};
			imgS = new String[]{"resources/Salle/A1/OuterWallS.png"};
			imgW = new String[]{"resources/Salle/A1/OuterWallW.png"};
			imgE = new String[]{"resources/Salle/A1/OuterWallE.png"};
			imgNE = new String[]{"resources/Salle/A1/OuterWallNE.png"};
			imgNW = new String[]{"resources/Salle/A1/OuterWallNW.png"};
			imgSE = new String[]{"resources/Salle/A1/OuterWallSE.png"};
			imgSW = new String[]{"resources/Salle/A1/OuterWallSW.png"};
			imageTable.put("N", imgN);
			imageTable.put("S", imgS);
			imageTable.put("W", imgW);
			imageTable.put("E", imgE);
			imageTable.put("NE", imgNE);
			imageTable.put("NW", imgNW);
			imageTable.put("SE", imgSE);
			imageTable.put("SW", imgSW);
			break;
		case(2) :
			imgN = new String[]{"resources/Salle/A2/OuterWallN.png"};
			imgS = new String[]{"resources/Salle/A2/OuterWallS.png"};
			imgW = new String[]{"resources/Salle/A2/OuterWallW.png"};
			imgE = new String[]{"resources/Salle/A2/OuterWallE.png"};
			imgNE = new String[]{"resources/Salle/A2/OuterWallNE.png"};
			imgNW = new String[]{"resources/Salle/A2/OuterWallNW.png"};
			imgSE = new String[]{"resources/Salle/A2/OuterWallSE.png"};
			imgSW = new String[]{"resources/Salle/A2/OuterWallSW.png"};
			imageTable.put("N", imgN);
			imageTable.put("S", imgS);
			imageTable.put("W", imgW);
			imageTable.put("E", imgE);
			imageTable.put("NE", imgNE);
			imageTable.put("NW", imgNW);
			imageTable.put("SE", imgSE);
			imageTable.put("SW", imgSW);
			break;
		case(3) :
			imgN = new String[]{"","",""};
			imgS = new String[]{"","",""};
			imgW = new String[]{"","",""};
			imgE = new String[]{"","",""};
			imgNE = new String[]{"","",""};
			imgNW = new String[]{"","",""};
			imgSE = new String[]{"","",""};
			imgSW = new String[]{"","",""};
			imageTable.put("N", imgN);
			imageTable.put("S", imgS);
			imageTable.put("W", imgW);
			imageTable.put("E", imgE);
			imageTable.put("NE", imgNE);
			imageTable.put("NW", imgNW);
			imageTable.put("SE", imgSE);
			imageTable.put("SW", imgSW);
			break;
		case(4) :
			imgN = new String[]{"","",""};
			imgS = new String[]{"","",""};
			imgW = new String[]{"","",""};
			imgE = new String[]{"","",""};
			imgNE = new String[]{"","",""};
			imgNW = new String[]{"","",""};
			imgSE = new String[]{"","",""};
			imgSW = new String[]{"","",""};
			imageTable.put("N", imgN);
			imageTable.put("S", imgS);
			imageTable.put("W", imgW);
			imageTable.put("E", imgE);
			imageTable.put("NE", imgNE);
			imageTable.put("NW", imgNW);
			imageTable.put("SE", imgSE);
			imageTable.put("SW", imgSW);
			break;
		}
	}

	
	
	
}
