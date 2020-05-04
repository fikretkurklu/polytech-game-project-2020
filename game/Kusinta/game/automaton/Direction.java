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

	public static final Direction N = new Direction(Ns);
	public static final Direction S = new Direction(Ss);
	public static final Direction E = new Direction(Es);
	public static final Direction W = new Direction(Ws);
	public static final Direction F = new Direction(Fs);
	public static final Direction B = new Direction(Bs);
	public static final Direction L = new Direction(Ls);
	public static final Direction R = new Direction(Rs);
	public static final Direction H = new Direction(Hs);
	public static final Direction NE = new Direction(NEs);
	public static final Direction NW = new Direction(NWs);
	public static final Direction SE = new Direction(SEs);
	public static final Direction SW = new Direction(SWs);

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
