package opponent;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import automaton.Automaton;
import automaton.Direction;
import environnement.Element;
import game.Coord;
import game.Model;
import player.Character;
import projectile.Arrow;

public abstract class Opponent extends Character {

	public static final int SIZE = (int) (1.5 * Element.SIZE);

	protected boolean moving;

	protected Arrow collidedWith;

	Key m_key;

	int m_width, m_height;
	
	int SPEED_WALK_TICK = 4;
	long m_moveElapsed;

	public Opponent(Automaton automaton, Coord C, Direction dir, Model model, int maxLife, int life,
			int attackSpeed, int resistance, int strength) throws IOException {
		super(automaton, C, dir, model, maxLife, life, attackSpeed, resistance, strength);

		m_key = null;

		moving = false;
	}

	@Override
	public void tick(long elapsed) {
		m_moveElapsed += elapsed;
		if (m_moveElapsed > SPEED_WALK_TICK) {
			m_moveElapsed -= SPEED_WALK_TICK;
			m_automaton.step(this);
		}
		if (this instanceof WalkingOpponent) {
			super.tick(elapsed);
		}
	}

	public Image loadImage(String path) throws Exception {
		File imageFile = new File(path);
		Image image;
		if (imageFile.exists()) {
			image = ImageIO.read(imageFile);
			image = image.getScaledInstance(SIZE, SIZE, 0);
			return image;
		} else {
			throw new Exception("Error while loading image: path = " + path);
		}
	}

	public void setCollidedWith(Arrow a) {
		collidedWith = a;
	}

	public void setKey(Key k) {
		m_key = k;
	}

	public void dropKey() {
		if (m_key != null) {
			try {
				if (m_key instanceof NormalKey) {
					m_model.setKey(new NormalKey(m_model.keyDropAutomaton, m_coord.X(), m_coord.Y(), m_model));
				} else {
					m_model.setBossKey(new BossKey(m_model.keyDropAutomaton, m_coord.X(), m_coord.Y(), m_model));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			setKey(null);
		}
	}

}
