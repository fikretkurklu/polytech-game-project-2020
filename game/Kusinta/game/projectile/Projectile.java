package projectile;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

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

	protected Coord m_coord;
	protected int m_State;
	protected double m_angle;
	protected Character m_shooter;
	protected Direction m_direction;
	protected Model m_model;
	protected long m_dead_time;
	protected float m_alpha;
	
	
	Image image;

	public Projectile(Automaton projectileAutomaton, int x, int y, double angle, Character shooter, Model model, Direction direction) {
		super(projectileAutomaton);
		m_coord = new Coord(x,y);
		m_angle = angle;
		m_shooter = shooter;
		m_direction = direction;
		m_model = model;
		m_State = OK_STATE;
		m_alpha = 1f;
		
	}

	@Override
	public boolean jump(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pop(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean wizz(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean power() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pick(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean turn(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean get() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean store() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean egg(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hit(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mydir(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closest(Category cat, Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean gotstuff() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean gotpower() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean key(int keyCode) {
		// TODO Auto-generated method stub
		return false;
	}

	public void loadImage(String path) throws Exception {
		File imageFile = new File(path);
		if (imageFile.exists()) {
			image = ImageIO.read(imageFile);
			image = image.getScaledInstance(SIZE, SIZE, 0);
		} else {
			throw new Exception("Error while loading image: path = " + path);
		}
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
