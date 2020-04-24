package underworld;

import java.util.HashMap;

import environnement.ImageManager;

public class UndWallImageManager  extends ImageManager{
	public UndWallImageManager(int ambiance) {
		useImageTable =  true;
		imageTable = new HashMap<String, String[]>();
		String[] imgBar;
		String[] imgHorizSide;
		String[] imgLeftSide;
		String[] imgRightSide;
		String[] imgWall;
		switch(ambiance) {
		case(1) :
			imgBar = new String[]{"resources/Underworld/Wall/wallSide.png"};
			imgHorizSide = new String[]{"resources/Underworld/Wall/wallSideHozizontal.png"};
			imgLeftSide = new String[]{"resources/Underworld/Wall/leftSide_ow.png"};
			imgRightSide = new String[]{"resources/Underworld/Wall/rightSide_ow.png"};
			imgWall = new String[]{"resources/Underworld/Wall/outerWall.png"};
			imageTable.put("B", imgBar);
			imageTable.put("HS", imgHorizSide);
			imageTable.put("LS", imgLeftSide);
			imageTable.put("RS", imgRightSide);
			imageTable.put("W", imgWall);
			break;
		case(2) :
			imgBar = new String[]{"resources/Room/A1/OuterWallN.png"};
			imgHorizSide = new String[]{"resources/Room/A1/OuterWallS.png"};
			imgLeftSide = new String[]{"resources/Room/A1/OuterWallW.png"};
			imgRightSide = new String[]{"resources/Room/A1/OuterWallE.png"};
			imgWall = new String[]{"resources/Room/A1/OuterWallNE.png"};
			imageTable.put("B", imgBar);
			imageTable.put("HS", imgHorizSide);
			imageTable.put("LS", imgLeftSide);
			imageTable.put("RS", imgRightSide);
			imageTable.put("W", imgWall);
			break;
		case(3) :
			imgBar = new String[]{"resources/Room/A1/OuterWallN.png"};
			imgHorizSide = new String[]{"resources/Room/A1/OuterWallS.png"};
			imgLeftSide = new String[]{"resources/Room/A1/OuterWallW.png"};
			imgRightSide = new String[]{"resources/Room/A1/OuterWallE.png"};
			imgWall = new String[]{"resources/Room/A1/OuterWallNE.png"};
			imageTable.put("B", imgBar);
			imageTable.put("HS", imgHorizSide);
			imageTable.put("LS", imgLeftSide);
			imageTable.put("RS", imgRightSide);
			imageTable.put("W", imgWall);
			break;
		case(4) :
			imgBar = new String[]{"resources/Room/A1/OuterWallN.png"};
			imgHorizSide = new String[]{"resources/Room/A1/OuterWallS.png"};
			imgLeftSide = new String[]{"resources/Room/A1/OuterWallW.png"};
			imgRightSide = new String[]{"resources/Room/A1/OuterWallE.png"};
			imgWall = new String[]{"resources/Room/A1/OuterWallNE.png"};
			imageTable.put("B", imgBar);
			imageTable.put("HS", imgHorizSide);
			imageTable.put("LS", imgLeftSide);
			imageTable.put("RS", imgRightSide);
			imageTable.put("W", imgWall);
		}
	}
}
