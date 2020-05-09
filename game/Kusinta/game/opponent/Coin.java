package opponent;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.HashMap;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import automaton.Entity;
import game.Coord;
import game.Model;
import environnement.Element;

public class Coin extends Entity {

	public static final int SIZE = (int) (1.5 * Element.SIZE / 2);

	private static final double G = 9.81;

	int m_value;

	int position, aller;

	protected boolean falling;
	long m_time;
	private int y_gravity;


	public Coin(Automaton automaton, Coord c,  Model model, Image[] bImages, HashMap<Action, int[]> hmActions) {
		super(automaton, bImages, hmActions);

		m_coord = new Coord(c);

		m_value = 0;

		m_time = 0;
		falling = false;

		position = 10;
		aller = -1;
		m_model = model;
		
		m_height = SIZE;
		m_width = (int) (ratio * m_height);
		
		hitBox = new Rectangle(m_coord.X() - 15, m_coord.Y() - 30, 30, 30);
	}
	 
	public void setMoney(int money) {
		m_value = money;
	}

	@Override
	public boolean cell(Direction dir, Category cat) {
		Rectangle playerHitBox = m_model.m_player.getHitBox();
		int w = m_width / 4;
		int h = m_height / 4;

		if (playerHitBox.contains(m_coord.X() + (w / 2), m_coord.Y() - (h / 2))) {
			return true;
		}

		return false;
	}

	@Override
	public boolean wizz(Direction dir) {
		m_model.m_player.setMoney(m_value);
		m_model.getM_coinToDelete().add(this);
		return false;
	}

	public void paint(Graphics g) {
		
		Image img = getImage();
		int w = m_width;
		int h = m_height;

		if (!falling) {
			if (position >= 10) {
				aller = -1;
			} else if (position <= 0) {
				aller = 1;
			}
			position = (position + aller);
		}
		
		g.drawImage(img, m_coord.X(), m_coord.Y() - h + position, w, h, null);
	}

	public void tick(long elapsed) {
		m_imageElapsed += elapsed;
		if (m_imageElapsed > 200) {
			m_imageElapsed = 0;
			m_imageIndex ++;
			if (m_imageIndex >= currentIndex.length) {
				m_imageIndex = 0;
			}
		}
		m_automaton.step(this);

		if (elapsed < 10) {
			if (!(m_model.m_room.isBlocked(hitBox.x, m_coord.Y() + 5) || m_model.m_room.isBlocked(hitBox.x + hitBox.width, m_coord.Y() + 5))) {

				if (!falling) {
					y_gravity = m_coord.Y();
					m_time = 0;
				} else {
					m_time += elapsed;
				}
				falling = true;
				if (m_time >= 10)
					gravity(m_time);
			} else if (falling) {
				int topBlock = m_model.m_room.blockTop(m_coord.X(), m_coord.Y() + 6) - 5;
				hitBox.translate(0, -(m_coord.Y() - topBlock));
				m_coord.setY(topBlock);
				m_time = 0;
				falling = false;
			}
			if (!falling) {
				if (m_model.m_room.isBlocked(m_coord.X(), m_coord.Y())) {
					int blockTop = m_model.m_room.blockTop(m_coord.X(), m_coord.Y() + 6) - 5;
					hitBox.translate(0, -(m_coord.Y() - blockTop));
					m_coord.setY(blockTop);
				}
			}
		}
	}

	private void gravity(long t) {
		int newY = (int) ((0.5 * G * Math.pow(t, 2) * 0.0005)) + y_gravity;
		hitBox.translate(0, -(m_coord.Y() - newY));
		m_coord.setY(newY);
	}

}
