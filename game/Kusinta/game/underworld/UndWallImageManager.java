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
		String[] imgIOW;
		String[] imgRWB;
		String[] imgOWD;
		String[] imgN;
		String[] imgS;
		String[] imgE;
		String[] imgW;
		switch(ambiance) {
		case(1) :
			imgBar = new String[]{"resources/Underworld/Wall/wallSide.png"};
			imgHorizSide = new String[]{"resources/Underworld/Wall/wallSideHozizontal.png"};
			imgLeftSide = new String[]{"resources/Underworld/Wall/leftSide_ow.png"};
			imgRightSide = new String[]{"resources/Underworld/Wall/rightSide_ow.png"};
			imgWall = new String[]{"resources/Underworld/Wall/outerWall.png"};
			imgIOW = new String[]{"resources/Underworld/Wall/wallSideHorizontalInner.png"};
			imgRWB = new String[]{"resources/Underworld/Wall/right_WB.png"};
			imgOWD = new String[]{"resources/Underworld/Wall/test_OW.png"};
			imgN = new String[]{"resources/Room/A1/OuterWallN.png"};
			imgS = new String[]{"resources/Room/A1/OuterWallS.png"};
			imgW = new String[]{"resources/Room/A1/OuterWallW.png"};
			imgE = new String[]{"resources/Room/A1/OuterWallE.png"};
			imageTable.put("B", imgBar);
			imageTable.put("HS", imgHorizSide);
			imageTable.put("LS", imgLeftSide);
			imageTable.put("RS", imgRightSide);
			imageTable.put("W", imgWall);
			imageTable.put("IOW", imgIOW);
			imageTable.put("RWB", imgRWB);
			imageTable.put("OWD", imgOWD);
			imageTable.put("N", imgN);
			imageTable.put("S", imgS);
			imageTable.put("W", imgW);
			imageTable.put("E", imgE);
			break;
		case(2) :
			imgBar = new String[]{"resources/Underworld/Wall/wallSide.png"};
			imgHorizSide = new String[]{"resources/Underworld/Wall/wallSideHozizontal.png"};
			imgLeftSide = new String[]{"resources/Underworld/Wall/leftSide_ow.png"};
			imgRightSide = new String[]{"resources/Underworld/Wall/rightSide_ow.png"};
			imgWall = new String[]{"resources/Underworld/Wall/outerWall.png"};
			imgIOW = new String[]{"resources/Underworld/Wall/wallSideHorizontalInner.png"};
			imageTable.put("B", imgBar);
			imageTable.put("HS", imgHorizSide);
			imageTable.put("LS", imgLeftSide);
			imageTable.put("RS", imgRightSide);
			imageTable.put("W", imgWall);
			imageTable.put("IOW", imgIOW);
			break;
		case(3) :
			imgBar = new String[]{"resources/Underworld/Wall/wallSide.png"};
			imgHorizSide = new String[]{"resources/Underworld/Wall/wallSideHozizontal.png"};
			imgLeftSide = new String[]{"resources/Underworld/Wall/leftSide_ow.png"};
			imgRightSide = new String[]{"resources/Underworld/Wall/rightSide_ow.png"};
			imgWall = new String[]{"resources/Underworld/Wall/outerWall.png"};
			imgIOW = new String[]{"resources/Underworld/Wall/wallSideHorizontalInner.png"};
			imageTable.put("B", imgBar);
			imageTable.put("HS", imgHorizSide);
			imageTable.put("LS", imgLeftSide);
			imageTable.put("RS", imgRightSide);
			imageTable.put("W", imgWall);
			imageTable.put("IOW", imgIOW);
			break;
		case(4) :
			imgBar = new String[]{"resources/Underworld/Wall/wallSide.png"};
			imgHorizSide = new String[]{"resources/Underworld/Wall/wallSideHozizontal.png"};
			imgLeftSide = new String[]{"resources/Underworld/Wall/leftSide_ow.png"};
			imgRightSide = new String[]{"resources/Underworld/Wall/rightSide_ow.png"};
			imgWall = new String[]{"resources/Underworld/Wall/outerWall.png"};
			imgIOW = new String[]{"resources/Underworld/Wall/wallSideHorizontalInner.png"};
			imageTable.put("B", imgBar);
			imageTable.put("HS", imgHorizSide);
			imageTable.put("LS", imgLeftSide);
			imageTable.put("RS", imgRightSide);
			imageTable.put("W", imgWall);
			imageTable.put("IOW", imgIOW);
		}
	}
}
