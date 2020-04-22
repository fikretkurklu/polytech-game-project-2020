package room;

import game.Coord;

public class Lamp extends Decor{

	public Lamp(Coord coord) {
		super(false,true, false, coord);
		int n = (int) (Math.random() * 3) + 1;
		String image_path = "resources/Room/decors/lamp"+n+".png";
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
