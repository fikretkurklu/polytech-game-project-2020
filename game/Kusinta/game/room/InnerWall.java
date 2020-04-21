package room;

import automaton.Category;
import automaton.Direction;
import game.Coord;

/*
 * 
 * Dans le cas d'un InnerWall, les images sont dans un tableau de String
 * 
 */

public class InnerWall extends Element {
	
	public InnerWall(InnerWallImageManager IWImageManager) throws Exception {
		super(true, true);
		String path = IWImageManager.get("", IWImageManager.useImageTable);
		if (path != null) {
			loadImage(path);
		}
	}
	
	public InnerWall(Coord coord, InnerWallImageManager IWImageManager) throws Exception {
		super(true, true, coord);
		String path = IWImageManager.get("", IWImageManager.useImageTable);
		if (path != null) {
			loadImage(path);
		}
	}

	@Override
	public boolean move(Direction dir) {
		return false;
	}

	@Override
	public boolean jump(Direction dir) {
		return false;
	}

	@Override
	public boolean pop(Direction dir) {
		return false;
	}

	@Override
	public boolean wizz(Direction dir) {
		return false;
	}

	@Override
	public boolean power() {
		return false;
	}

	@Override
	public boolean pick(Direction dir) {
		return false;
	}

	@Override
	public boolean turn(Direction dir) {
		return false;
	}

	@Override
	public boolean get() {
		return false;
	}

	@Override
	public boolean store() {
		return false;
	}

	@Override
	public boolean explode() {
		return false;
	}

	@Override
	public boolean egg(Direction dir) {
		return false;
	}

	@Override
	public boolean hit(Direction dir) {
		return false;
	}

	@Override
	public boolean mydir(Direction dir) {
		return false;
	}

	@Override
	public boolean cell(Direction dir, Category cat) {
		return false;
	}

	@Override
	public boolean closest(Category cat, Direction dir) {
		return false;
	}

	@Override
	public boolean gotstuff() {
		return false;
	}

	@Override
	public boolean gotpower() {
		return false;
	}

	@Override
	public boolean key(int keyCode) {
		return false;
	}


}
