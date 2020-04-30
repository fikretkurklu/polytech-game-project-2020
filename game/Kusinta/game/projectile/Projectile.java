package projectile;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import automaton.Entity;
import game.Coord;
import game.Model;
import opponent.Opponent;
import player.Character;

public abstract class Projectile extends Entity {

	protected int SPEED = 9;
	
	protected enum State {OK_STATE, HIT_STATE};
	public static final int SIZE = 86;

	protected double m_angle;
	protected Direction m_direction;
	protected int m_strength;

	int moving;

	protected State m_State;
	
	protected Character m_shooter;
	protected Character collidingWith;
	protected Model m_model;

	protected long m_dead_time;

	protected float m_alpha;

	protected Coord hitBox;

	protected BufferedImage bImage;

	protected Image image;

	public Projectile( Automaton projectileAutomaton, int x, int y, double angle, Character shooter, Model model, Direction direction) {
		super(projectileAutomaton);
		
		m_coord = new Coord(x,y);
		m_angle = angle;
		m_direction = direction;

		m_shooter = shooter;
		m_model = model;
		
		m_State = State.OK_STATE;
		
		m_alpha = 1f;

		m_dead_time = 0;

		moving = 0;
		
		m_strength = 15;

	}

	@Override
	public boolean move(Direction dir) {
		int tmpX = m_coord.X();
		int tmpY = m_coord.Y();

		if (moving == 0) {
			if (m_direction.toString().equals("E")) {
				m_coord.setX((int) (m_coord.X() + SPEED * Math.cos(m_angle)));
				m_coord.setY((int) (m_coord.Y() - SPEED * Math.sin(m_angle)));
			} else {
				m_coord.setX((int) (m_coord.X() - SPEED * Math.cos(m_angle)));
				m_coord.setY((int) (m_coord.Y() - SPEED * Math.sin(m_angle)));
			}
		}
		moving = (moving + 1) % 3;

		hitBox.translate(m_coord.X() - tmpX, m_coord.Y() - tmpY);

		return true;
	}

	@Override
	public boolean cell(Direction dir, Category cat) {
		boolean c;
		if (cat.toString().equals("_")) {
			c = ((m_model.m_room.isBlocked(m_coord.X(), m_coord.Y()))
					|| (m_model.m_room.isBlocked(hitBox.X(), hitBox.Y())));
			if (c) {
				m_State = State.HIT_STATE;
				return true;
			}
			if (m_shooter instanceof Opponent) {
				c = m_model.getPlayer().getHitBox().contains(hitBox.X(), hitBox.Y())
						|| m_model.getPlayer().getHitBox().contains(m_coord.X(), m_coord.Y());
				if(c) {
					m_State = State.HIT_STATE;
					this.setCollidingWith(m_model.getPlayer());
					return true;
				}
			} else {
				LinkedList<Opponent> opponents = m_model.getOpponent();
				for(Opponent op : opponents) {
					c = op.getHitBox().contains(hitBox.X(), hitBox.Y())
							|| op.getHitBox().contains(m_coord.X(), m_coord.Y());
					if(c) {
						m_State = State.HIT_STATE;
						this.setCollidingWith(op);
						return true;
					}
				}
			}
		} else {
			c = !((m_model.m_room.isBlocked(m_coord.X(), m_coord.Y()))
					|| (m_model.m_room.isBlocked(hitBox.X(), hitBox.Y())));
			if (m_State == State.HIT_STATE) {
				return !c;
			}
			if (!c) {
				m_State = State.HIT_STATE;
			}
			return c;
		}
		return false;
	}

	public Image loadImage(String path) throws Exception {
		File imageFile = new File(path);
		Image img;
		if (imageFile.exists()) {
			img = ImageIO.read(imageFile);
			img = img.getScaledInstance(SIZE, SIZE, 0);
			return img;
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
	
	public State getState() {
		return m_State;
	}

	@Override
	public boolean explode() {
		if (m_dead_time == 0) {
			m_dead_time = System.currentTimeMillis();
		}
		return true;
	}
	
	public void setCollidingWith(Character cha) {
		if(collidingWith != cha) {
			collidingWith = cha;
			collidingWith.loseLife(m_strength);
		}
	}
	
	public void setSpeed(int speed) {
		SPEED = speed;
	}
	
	public abstract void paint(Graphics g) ;
}
