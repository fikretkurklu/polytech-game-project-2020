package projectile;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.HashMap;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import environnement.Element;
import game.Coord;
import player.Character;

public class Meteor extends Projectile {

	public static final int SIZE = (int) (Element.SIZE);

	public Meteor(Automaton projectileAutomaton, Coord c, double angle, Character shooter, Direction direction,
			Image[] bImages, HashMap<Action, int[]> indiceAction) throws Exception {
		super(projectileAutomaton, c, angle, shooter, direction, bImages, indiceAction);

		m_height = SIZE;
		m_width = (int) (m_height * ratio);
		hitBox = new Rectangle(m_coord.X() - SIZE / 5, m_coord.Y() - SIZE / 10, SIZE/2, SIZE/2);

		setSpeed(8);
		
		m_strength = 15;

	}

	public void paint(Graphics g) {
		Graphics2D bg = (Graphics2D) g.create(m_coord.X() - m_width / 2, m_coord.Y() - m_height / 2, m_width * 2,
				m_height * 2);
		Image image = getImage();
		
		
		if (m_direction == Direction.E) {
			bg.rotate(-m_angle, m_width / 2, m_height / 2);
			bg.drawImage(image, 0 , 0, m_width, m_height, null);
			
		} else {
			bg.rotate(m_angle, m_width / 2, m_height / 2);
			bg.drawImage(image, m_width , 0, -m_width, m_height, null);
		}
		bg.dispose();


		g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
		bg.dispose();

	}

	public void tick(long elapsed) {
		m_automaton.step(this);
		m_imageElapsed += elapsed;
		if (m_imageElapsed > 200) {
			m_imageElapsed = 0;
			m_imageIndex++;

			if (m_State == State.OK_STATE) {
				m_imageIndex = 0;
			} else if (m_imageIndex >= currentIndex.length) {
				m_shooter.removeProjectile(this);
			}
		}

	}

	@Override
	public boolean cell(Direction dir, Category cat) {
		boolean b = super.cell(dir, cat);
		if (b) {
			if (cat == Category.P) {
				int tmpPlayerResistance = m_model.m_player.m_currentStatMap.get(Character.CurrentStat.Resistance);
				this.setCollidingWith(m_model.getPlayer());
				collidingWith.loseLife(m_strength - tmpPlayerResistance);
			}
		}
		return b;
	}

	@Override
	public boolean explode() {
		if (m_State == State.OK_STATE) {
			m_State = State.HIT_STATE;
			currentAction = Action.DEATH;
			resetAnim();
		}
		return true;
	}

}
