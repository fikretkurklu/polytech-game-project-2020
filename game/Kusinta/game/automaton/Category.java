package automaton;

public class Category {

	String m_category;

	public static final String As = "A";
	public static final String Cs = "C";
	public static final String Ds = "D";
	public static final String Gs = "G";
	public static final String Js = "J";
	public static final String Ms = "M";
	public static final String Os = "O";
	public static final String Ps = "P";
	public static final String Ts = "T";
	public static final String Vs = "V";
	public static final String ATs = "@";
	public static final String UNDERSCOREs = "_";

	public static final Category A = new Category(As);
	public static final Category C = new Category(Cs);
	public static final Category D = new Category(Ds);
	public static final Category G = new Category(Gs);
	public static final Category J = new Category(Js);
	public static final Category M = new Category(Ms);
	public static final Category O = new Category(Os);
	public static final Category P = new Category(Ps);
	public static final Category T = new Category(Ts);
	public static final Category V = new Category(Vs);
	public static final Category AT = new Category(ATs);
	public static final Category UNDERSCORE = new Category(UNDERSCOREs);

	public Category(String category) {
		m_category = category;
	}

	public String toString() {
		return m_category;
	}

}
