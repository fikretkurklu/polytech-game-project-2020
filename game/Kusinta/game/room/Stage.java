package room;

import game.Coord;

public class Stage extends Decor{

	public Stage(Coord coord) {
		super(false,true, false, coord);
		int n = (int) (Math.random() * 2) + 1;
		String image_path = "resources/Room/decors/stage"+n+".png";
		try {
			super.loadImage(image_path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void tick(long elapsed) {
		// TODO Auto-generated method stub
	}

}
