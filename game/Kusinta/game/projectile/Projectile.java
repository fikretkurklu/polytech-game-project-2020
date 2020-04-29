package projectile;

import java.awt.Image;
import java.awt.image.BufferedImage;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import automaton.Entity;
import game.Coord;
import game.Model;
import player.Character;

public abstract class Projectile extends Entity {
	static final int OK_STATE = 1;
	static final int HIT_STATE = 2;
	public static final int SIZE = 86;

	protected double m_angle;
	protected Direction m_direction;
	
	protected int m_State;
	
	protected Character m_shooter;
	protected Model m_model;
	
	protected long m_dead_time;
	
	protected float m_alpha;
	
	protected Coord hitBox;
	
	protected BufferedImage bImage;
	
	protected Image image;

	public Projectile(Image projectileImage, Automaton projectileAutomaton, int x, int y, double angle, Character shooter, Model model, Direction direction) {
		super(projectileAutomaton);
		
		image = projectileImage;
		m_coord = new Coord(x,y);
		m_angle = angle;
		m_direction = direction;
		
		m_shooter = shooter;
		m_model = model;
		
		m_State = OK_STATE;
		
		m_alpha = 1f;
		
	}
	
	@Override
	public boolean cell(Direction dir, Category cat) {
		boolean c = !(m_model.m_room.isBlocked(m_coord.X(), m_coord.Y()));
		if (m_State == HIT_STATE) {
			return !c;
		}
		if (!c) {
			m_State = HIT_STATE;
		}
		return c;
	}
	
	public void tick(long elapsed) {
		m_automaton.step(this);
	}
	
	public long getDeadTime() {
		return m_dead_time;
	}
	
	public int getState() {
		return m_State;
	}
	
	public float getAlpha() {
		return m_alpha;
	}
	
	public void setAlpha(float alpha) {
		m_alpha = alpha;
	}
}
