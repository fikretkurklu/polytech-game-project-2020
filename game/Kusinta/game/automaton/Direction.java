package automaton;

public class Direction {

	String m_dir;

	public static final String Ns = "N";
	public static final String Ss = "S";
	public static final String Es = "E";
	public static final String Ws = "W";
	public static final String Fs = "F";
	public static final String Bs = "B";
	public static final String Ls = "L";
	public static final String Rs = "R";
	public static final String Hs = "H";
	public static final String NEs = "NE";
	public static final String NWs = "NW";
	public static final String SEs = "SE";
	public static final String SWs = "SW";

	public static Direction N = new Direction(Ns);
	public static Direction S = new Direction(Ss);
	public static Direction E = new Direction(Es);
	public static Direction W = new Direction(Ws);
	public static Direction F = new Direction(Fs);
	public static Direction B = new Direction(Bs);
	public static Direction L = new Direction(Ls);
	public static Direction R = new Direction(Rs);
	public static Direction H = new Direction(Hs);
	public static Direction NE = new Direction(NEs);
	public static Direction NW = new Direction(NWs);
	public static Direction SE = new Direction(SEs);
	public static Direction SW = new Direction(SWs);

	public Direction(String dir) {
		m_dir = dir;
	}

	public String toString() {
		return m_dir;
	}

	public void setDirection(String dir) {
		m_dir = dir;
	}
}
