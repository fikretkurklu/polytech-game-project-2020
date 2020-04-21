package room;

import automaton.Category;
import automaton.Direction;
import game.Coord;

public class Door extends Element {

	Room m_room;
	
	public Door(DoorImageManager DImageManager) throws Exception {
		super(true, true);
		String path = DImageManager.get("", DImageManager.useImageTable);
		if (path != null) {
			loadImage(path);
		}
	}
	
	public Door(Coord coord, DoorImageManager DImageManager) throws Exception {
		super(false, true, coord);
		String path = DImageManager.get("", DImageManager.useImageTable);
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

