package environnement;

import java.awt.Graphics;

public abstract class Env {
	public static final int VILLAGE = 0;
	public static final int ROOM = 1;
	public static final int UNDERWORLD = 2;
	
	public int m_type;
	public AutomatonLibrary m_AL;
	public Env(int type, AutomatonLibrary AL) {
		m_type = type;
		m_AL = AL;
	}
	
	public abstract void paint(Graphics g);
	
	public abstract void tick(long elapsed);
}
