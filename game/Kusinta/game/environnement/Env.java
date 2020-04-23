package environnement;

import java.awt.Graphics;

import automaton.AutomatonLibrary;

public abstract class Env {
	public static final int VILLAGE = 0;
	public static final int ROOM = 1;
	public static final int UNDERWORLD = 2;
	
	public int m_type;
	public AutomatonLibrary m_AL;
	protected int m_width;
	protected int m_height;
	public Env(int type, AutomatonLibrary AL, int w, int h) {
		m_type = type;
		m_AL = AL;
		m_width = w;
		m_height = h;
	}
	
	
	public abstract void paint(Graphics g, int width, int height);
	
	public abstract void tick(long elapsed);
}
