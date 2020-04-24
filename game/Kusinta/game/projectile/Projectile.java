package projectile;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import automaton.Entity;
import game.Coord;
import player.Player;

public abstract class Projectile extends Entity {
	static final int OK_STATE = 1;
	static final int HIT_STATE = 2;
	public static final int SIZE = 86;

	Coord m_coord;
	int m_state;
	double m_angle;
	Player m_shooter;
	Direction m_direction;

	Image image;

	public Projectile(Automaton projectileAutomaton, int x, int y, double angle, Player player) {
		super(projectileAutomaton);
		m_coord = new Coord(x,y);
		m_angle = angle;

		m_direction = player.m_direction;
		m_shooter = player;
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
}
