package game;

public class Coord {

	private int x;
	private int y;
	
	public Coord(){
		x = 0;
		y = 0;
	}
	
	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Coord(Coord c) {
		x = c.X();
		y = c.Y();
	}
	
	public int X() {
		return x;
	}
	
	public int Y() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setCoord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean isEqual(Coord c) {
		return (X() == c.X() && Y() == c.Y());
	}
	
	public void translate(int dx, int dy) {
		x += dx;
		y += dy;
	}
	
	public void translateX(int dx) {
		x += dx;
	}
	
	public void translateY(int dy) {
		y += dy;
	}
	
}

