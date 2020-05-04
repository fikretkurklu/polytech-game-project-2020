package projectile;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import environnement.Element;
import game.Coord;
import player.Character;

public class MagicProjectile extends Projectile {

	public static final int SIZE = (int) (1.5 * Element.SIZE);

	Image[] images;
	int m_imageElapsed, m_image_index;

	int moving;
	int DIMENSION;

	int m_height;
	int m_width;

	public MagicProjectile(Automaton projectileAutomaton, int x, int y, double angle, Character shooter,
			Direction direction) throws Exception {
		super(projectileAutomaton, x, y, angle, shooter, direction);

		images = m_shooter.getProjectileImages();

		DIMENSION = SIZE / (images[0].getHeight(null));

		float ratio = (float) (images[0].getWidth(null) * 4) / (float) (5 * images[0].getHeight(null));

		m_height = DIMENSION * images[0].getHeight(null);
		m_width = (int) (ratio * images[0].getWidth(null));

		if (m_direction == Direction.E) {
			hitBox = new Coord((int) (m_coord.X() + (m_width / 2) * Math.cos(m_angle)),
					(int) (m_coord.Y() - (m_width / 2) * Math.sin(m_angle)));
		} else {
			hitBox = new Coord((int) (m_coord.X() - (m_width / 2) * Math.cos(m_angle)),
					(int) (m_coord.Y() - (m_width / 2) * Math.sin(m_angle)));
		}

		m_imageElapsed = 0;
		m_image_index = 0;

		setSpeed(4);

	}

	public void paint(Graphics g) {

		Image img = images[m_image_index];
		
		Graphics2D bg = (Graphics2D) g.create(m_coord.X() - m_width / 2, m_coord.Y() - m_height/2, m_width* 2, m_height * 2);


		if (images != null) {
			int w = m_width;
			int h = m_height;
			if (m_direction == Direction.E) {
				bg.rotate(-m_angle, m_width / 2, m_height/2);
				bg.drawImage(img, 0, 0, w, h, null);
			} else {
				bg.rotate(m_angle, m_width / 2, m_height / 2);
				bg.drawImage(img, m_width, 0, -w, h, null);
			}
		}
		bg.dispose();

	}

	public void tick(long elapsed) {
		m_automaton.step(this);
		m_imageElapsed += elapsed;
		if (m_imageElapsed > 200) {
			m_imageElapsed = 0;

			if (m_image_index == 12) {
				m_shooter.removeProjectile(this);
			}

			if (m_State == State.OK_STATE) {
				m_image_index = 0;
			} else {
				m_image_index++;
			}
		}

	}

	@Override
	public boolean cell(Direction dir, Category cat) {
		boolean c;
		if (cat.toString().equals("_")) {
			c = (m_model.m_room.isBlocked(m_coord.X(), m_coord.Y()));
			if (c) {
				m_State = State.HIT_STATE;
				return true;
			}

			c = m_model.getPlayer().getHitBox().contains(m_coord.X(), m_coord.Y());
			if (c) {
				m_State = State.HIT_STATE;
				this.setCollidingWith(m_model.getPlayer());
				return true;
			}

		} else {
			c = !((m_model.m_room.isBlocked(m_coord.X(), m_coord.Y())));
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

}
