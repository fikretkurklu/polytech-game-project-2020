package game;

public class Direction {
	
	String EAST = "East";
	String NORTH = "North";
	String WEST = "West";
	String SOUTH = "South";

	String m_dir;
	
	public Direction(String dir) {
		m_dir = dir;
	}
	
	public String toString() {
		return m_dir;
	}
	
}
