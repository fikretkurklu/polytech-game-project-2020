package room;

import automaton.Category;
import automaton.Direction;
import game.Coord;

public class OuterWall extends Element {

	/*
	 * 
	 * 
	 * This class represent the outside of the wall, with its sprite and behavior 
	 * 
	 * 
	 */
	
	private String m_orientation;
	
	public OuterWall(OuterWallImageManager OWImageManager) throws Exception {
		super(true, true);
		m_orientation = "N";
		String path = OWImageManager.get(m_orientation,OWImageManager.useImageTable);
		if (path != null) {
			loadImage(path);
		}
	}
	
	public OuterWall(Coord coord, OuterWallImageManager OWImageManager, String orientation) throws Exception {
		super(true, true, coord);
		m_orientation = orientation;
		String path = OWImageManager.get(m_orientation,OWImageManager.useImageTable);
		if (path != null) {
			loadImage(path);
		}		
	}
	
	public void setOrientation(String orientation) {
		m_orientation = orientation;
	}
	
	public String getOrientation() {
		return m_orientation;
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
