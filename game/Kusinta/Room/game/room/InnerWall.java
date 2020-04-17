package game.room;

import java.io.IOException;

public class InnerWall extends Element {

	private static final String image_path="";
	
	
	public InnerWall() throws IOException {
		super(true, true);
		loadImage(image_path);
	}

}
