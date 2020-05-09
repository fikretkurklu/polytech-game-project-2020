package projectile;

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
import player.Character.CurrentStat;

public class MagicProjectile extends Projectile {

	public static final int SIZE = (int) (1.5 * Element.SIZE);

	public MagicProjectile(Automaton projectileAutomaton, Coord c, double angle, Character shooter, Direction direction,
			Image[] bImages, HashMap<Action, int[]> indiceAction) throws Exception {
		super(projectileAutomaton, c, angle, shooter, direction, bImages, indiceAction);

		m_height = SIZE;
		m_width = (int) (m_height * ratio);
		hitBox = new Rectangle(m_coord.X() - 5, m_coord.Y() - 5, 10, 10);

		m_stepTick = 4;
		setSpeed(10);
		

	}

	public void paint(Graphics g) {

		Image img = getImage();
		Graphics2D bg = (Graphics2D) g.create(m_coord.X() - m_width / 2, m_coord.Y() - m_height / 2, m_width * 2,
				m_height * 2);

		int w = m_width;
		int h = m_height;
		if (m_direction == Direction.E) {
			bg.rotate(-m_angle, m_width / 2, m_height / 2);
			bg.drawImage(img, 0, 0, w, h, null);
		} else {
			bg.rotate(m_angle, m_width / 2, m_height / 2);
			bg.drawImage(img, m_width, 0, -w, h, null);
		}
		bg.dispose();

	}

	public void tick(long elapsed) {
		super.tick(elapsed);
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
				m_model.getPlayer().loseLife(m_shooter.getStat(CurrentStat.Strength) - tmpPlayerResistance);
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
