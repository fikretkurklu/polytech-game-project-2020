package game.room;

import java.io.IOException;

public class OuterWall extends Element {

	private static final String image_path1="";
	private static final String image_path2="";
	private static final String image_path3="";
	private static final String image_path4="";
	
	private int orientation;
	
	public OuterWall() throws IOException {
		super(true);
		if (orientation == 1) {
			load_image(image_path1);
		} else if (orientation == 2) {
			load_image(image_path2);
		} else if (orientation == 3) {
			load_image(image_path3);
		} else if (orientation == 4) {
			load_image(image_path4);
		}
	}

	
}
