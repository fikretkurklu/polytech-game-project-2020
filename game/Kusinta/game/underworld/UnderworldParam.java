package underworld;

public interface UnderworldParam {
	String backgroundFile = "resources/Underworld/Sky.png";
	String mapFile = "resources/Underworld/Sample/map6.sample";
	int nbAmbiance = 1;
	String[] platformImage = {"resources/Underworld/Platform1.png", "resources/Underworld/Platform2.png", "resources/Underworld/Platform3.png", "resources/Underworld/Platform4.png", "resources/Underworld/Platform5.png"};
	String[] cloudImage = {"resources/Underworld/Cloud.png", "resources/Underworld/CloudLeftUp.png", "resources/Underworld/CloudRightUp.png","resources/Underworld/CloudLeftDown.png","resources/Underworld/CloudRightDown.png"};
	String[] playerSoulImage = {"resources/Underworld/PlayerSoul/PS1.png", "resources/Underworld/PlayerSoul/PS2.png", "resources/Underworld/PlayerSoul/PS3.png", "resources/Underworld/PlayerSoul/PS4.png"};
	String[] playerSoulEscapeImage = {"resources/Underworld/PlayerSoul/PSE1.png", "resources/Underworld/PlayerSoul/PSE2.png", "resources/Underworld/PlayerSoul/PSE3.png", "resources/Underworld/PlayerSoul/PSE4.png"};
	String[] lureApparitionImage = {"resources/Underworld/Lure/spirit-recruit-0.png", "resources/Underworld/Lure/spirit-recruit-1.png", "resources/Underworld/Lure/spirit-recruit-2.png", "resources/Underworld/Lure/spirit-recruit-3.png",
			"resources/Underworld/Lure/spirit-recruit-4.png", "resources/Underworld/Lure/spirit-recruit-5.png", "resources/Underworld/Lure/spirit-recruit-6.png"};
	String[] lureImage = {"resources/Underworld/Lure/lost-ghost-s-1.png", "resources/Underworld/Lure/lost-ghost-s-2.png", "resources/Underworld/Lure/lost-ghost-s-3.png", "resources/Underworld/Lure/lost-ghost-s-4.png"};
	String[] ghostImage = {"resources/Underworld/Ghost/flame-shadow-s-1.png", "resources/Underworld/Ghost/flame-shadow-s-2.png", "resources/Underworld/Ghost/flame-shadow-s-3.png", "resources/Underworld/Ghost/flame-shadow-s-atk-1.png", "resources/Underworld/Ghost/flame-shadow-s-atk-2.png", "resources/Underworld/Ghost/flame-shadow-s-atk-3.png", "resources/Underworld/Ghost/flame-shadow-s-atk-4.png", "resources/Underworld/Ghost/flame-shadow-s-atk-5.png", "resources/Underworld/Ghost/flame-shadow-s-atk-6.png"};
	String[] playerSoulDeathImage = {"resources/Underworld/PlayerSoul/D1.png", "resources/Underworld/PlayerSoul/D2.png", "resources/Underworld/PlayerSoul/D3.png", "resources/Underworld/PlayerSoul/D4.png", "resources/Underworld/PlayerSoul/D6.png", "resources/Underworld/PlayerSoul/D7.png"};
	String fragmentSprite = "resources/Underworld/Fragment/CrystalsWhite.png";
	String gateSprite = "resources/Underworld/Gate/Gate.png";
	String deathSprite = "resources/Underworld/PlayerSoul/D5.png";
	
	int fragmentAnimationSize = 7;
	int gateApparitionAnimationSize = 24;
	int gateAnimationSize = 30;
	
	int sizePlayerAnimation = playerSoulImage.length;
	int sizePlayerDashAnimation = lureApparitionImage.length + sizePlayerAnimation;
	int sizePlayerEscapeAnimation = sizePlayerAnimation + sizePlayerDashAnimation;
	int sizePlayerDeathAnimation = sizePlayerEscapeAnimation + 34;

	int sizeLureAnimation = lureImage.length;
	int sizeLureApearingAnimation = lureApparitionImage.length + sizeLureAnimation;
	int sizeLureDisaparitionAnimation = lureApparitionImage.length + sizeLureApearingAnimation;
}
