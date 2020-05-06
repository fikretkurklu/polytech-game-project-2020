package projectile;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import environnement.Element;
import game.Coord;
import opponent.Opponent;
import player.Player;
import projectile.Projectile.State;
import player.Character;

public class Metor extends Projectile {

	public static final int SIZE = (int) (1.5 * Element.SIZE);

	int m_height;
	int m_width;
	int m_imageElapsed, m_image_index;

	int ANIMATION_SPEED = 20;

	Image[] images;

	public Metor(Automaton arrowAutomaton, Coord c, double angle, Character shooter, Direction direction)
			throws Exception {
		super(arrowAutomaton, c, angle, shooter, direction);

		images = m_shooter.getProjectileImages();
		m_height = SIZE;
		m_width = m_height * images[0].getWidth(null) / images[0].getHeight(null);

		if (m_direction == Direction.E) {
			hitBox = new Rectangle((int) (m_coord.X() + (m_width / 2) * Math.cos(m_angle) * 1.5),
					(int) (m_coord.Y() - (m_width / 2) * Math.sin(m_angle)), 10, 10);
		} else {
			hitBox = new Rectangle((int) ((m_coord.X() - (m_width / 2) * Math.cos(m_angle) * 1)),
					(int) ((m_coord.Y() - (m_width / 2) * Math.sin(m_angle))), 10, 10);
		}
	}

	public void paint(Graphics g) {
		Graphics2D bg = (Graphics2D) g.create(hitBox.x - m_width / 2, hitBox.y - m_height / 2, m_width * 2,
				m_height * 2);

		if (images != null) {
			if (m_direction == Direction.E) {
				bg.rotate(-m_angle - Math.PI/2, m_width / 2, m_height / 2);
				bg.drawImage(images[m_image_index], 0, -m_height/2, m_width, m_height, null);
			} else {
				bg.rotate(m_angle + Math.PI/2, m_width / 2, m_height / 2);
				bg.drawImage(images[m_image_index], m_width, -m_height/2, -m_width, m_height, null);
			}
		}
		bg.dispose();
	}

	public void tick(long elapsed) {
		m_automaton.step(this);
		m_imageElapsed += elapsed;
		if (m_imageElapsed > ANIMATION_SPEED) {
			m_imageElapsed = 0;

			if (m_image_index >= images.length - 1) {
				m_shooter.removeProjectile(this);
			}

			if (m_image_index > 43 && m_State == State.OK_STATE) {
				m_image_index = 12;
			} else {
				m_image_index++;
			}
		}

	}

	public boolean cell(Direction dir, Category cat) {
		boolean b = super.cell(dir, cat);
		if (b) {
			if (cat == Category.P) {
				this.setCollidingWith(m_model.getPlayer());
				collidingWith.loseLife(m_strength);
			}
		}
		return b;
	}

	public Coord getCoord() {
		return m_coord;
	}

	@Override
	public boolean explode() {
		m_State = State.HIT_STATE;
		return true;
	}

}