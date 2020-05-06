package opponent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import game.Coord;
import game.ImageLoader;
import game.Model;
import projectile.Projectile.proj;

public class FlyingOpponent extends Opponent {

	Image[] death;
	Image[] flight;
	Image[] attack;
	int m_image_index, m_imageElapsed;

	public FlyingOpponent(Automaton automaton, Coord C, Direction dir, Model model) throws Exception {

		super(automaton, C, dir, model, 100, 100, 1000, 100, 5);

		m_imageElapsed = 0;

		shooting = false;

		imageProjectiles = new Image[13];
		for (int i = 0; i < 13; i++) {
			imageProjectiles[i] = ImageLoader.loadImage("resources/oppenent/jin/Magic_Attack" + (i + 1) + ".png", SIZE);
		}
		death = new Image[6];
		for (int i = 0; i < 6; i++) {
			death[i] = ImageLoader.loadImage("resources/oppenent/jin/Death" + (i + 1) + ".png", SIZE);
		}

		flight = new Image[4];
		for (int i = 0; i < 4; i++) {
			flight[i] = ImageLoader.loadImage("resources/oppenent/jin/Flight" + (i + 1) + ".png", SIZE);
		}

		attack = new Image[4];
		for (int i = 0; i < 4; i++) {
			attack[i] = ImageLoader.loadImage("resources/oppenent/jin/Attack" + (i + 1) + ".png", SIZE);
		}

		float ratio = (float) ((float) flight[0].getWidth(null)) / (float) (flight[0].getHeight(null));

		m_height = SIZE;
		m_width = (int) (m_height * ratio);
		
		int w = (int) (m_width / 1.7);
		int h = (int) (m_height / 1.3);

		hitBox = new Rectangle(m_coord.X() - w / 2, m_coord.Y() - h - 10, w, h);

		m_image_index = 0;

		m_money = 200;

		m_moveElapsed = 0;

		X_MOVE = 2;

	}

	public boolean explode() {
		return true;
	}

	@Override
	public boolean egg(Direction dir) {
		if (gotpower() && !shooting) {

			m_image_index = 0;

			shooting = true;

			Coord playerCoord = m_model.getPlayer().getCoord();
			int player_x = playerCoord.X();

			if (player_x > m_coord.X()) {
				turn(Direction.E);
			} else {
				turn(Direction.W);
			}

			return true;
		}
		return false;
	}

	@Override
	public void paint(Graphics g) {
		Image image;
		if (!gotpower()) {
			image = death[m_image_index];
		} else if (shooting) {
			image = attack[m_image_index];
		} else {
			image = flight[m_image_index];
		}
		if (m_direction == Direction.E) {
			g.drawImage(image, m_coord.X() - (m_width / 2), m_coord.Y() - m_height, m_width, m_height, null);
		} else {
			g.drawImage(image, m_coord.X() + (m_width / 2), m_coord.Y() - m_height, -m_width, m_height, null);
		}
		g.setColor(Color.DARK_GRAY);
		g.fillRect(hitBox.x, hitBox.y - 10, hitBox.width, 10);
		if ((m_currentStatMap.get(CurrentStat.Life)) > 50) {
			g.setColor(Color.GREEN);
		} else if ((m_currentStatMap.get(CurrentStat.Life)) > 25) {
			g.setColor(Color.ORANGE);
		} else {
			g.setColor(Color.RED);
		}
		float w = hitBox.width * ((float) (m_currentStatMap.get(CurrentStat.Life)) / 100);
		g.fillRect(hitBox.x, hitBox.y - 10, (int) w, 10);
		g.setColor(Color.LIGHT_GRAY);
		g.drawRect(hitBox.x, hitBox.y - 10, hitBox.width, 10);
		
		for (int i = 0; i < m_projectiles.size(); i ++) {
			m_projectiles.get(i).paint(g);
		}
	}

	@Override
	public void tick(long elapsed) {
		super.tick(elapsed);

		m_imageElapsed += elapsed;
		float attackspeed = 200;
		if (shooting) {
			attackspeed = m_currentStatMap.get(CurrentStat.Attackspeed);
			attackspeed = 200 / (attackspeed / 1000);
		}
		if (m_imageElapsed > attackspeed) {
			m_imageElapsed = 0;

			if (!gotpower()) {
				if (m_image_index == 5) {
					m_model.getOpponent().remove(this);
					dropKey();
					m_model.addCoin(new Coin(m_model.coinDropAutomaton, m_coord.X(), m_coord.Y(), m_money, m_model));

				}
				m_image_index = (m_image_index + 1) % 6;
			} else {
				m_image_index = (m_image_index + 1) % 4;
			}
			if (shooting) {
				if (m_image_index == 3) {
					Coord playerCoord = m_model.getPlayer().getCoord();
					super.shoot(playerCoord.X(), playerCoord.Y() - m_model.getPlayer().getHeight() / 2,
							proj.MAGIC_PROJECTILE);
				}
			}
		}

		for (int i = 0; i < m_projectiles.size(); i ++) {
			m_projectiles.get(i).tick(elapsed);
		}
	}

	@Override
	public boolean closest(Category cat, Direction dir) {
		if (m_model.actualMode == Model.mode.ROOM) {
			boolean d = m_model.getPlayer().gotpower();
			if (d) {

				Coord playerCoord = m_model.getPlayer().getCoord();
				int player_x = playerCoord.X();
				int player_y = playerCoord.Y() - m_model.getPlayer().getHeight() / 2;
				int x = player_x - m_coord.X();
				int y = (m_coord.Y() - m_height / 2) - player_y;

				int distance = (int) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

				if (distance <= 400) {

					double r = (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
					double angle = (float) Math.asin(Math.abs(y) / r);

					if (player_y > m_coord.Y() - m_height / 2) {
						angle = -angle;
					}

					int i = 0;

					while (i < distance) {
						int checkX;
						int checkY = (int) (m_coord.Y() - m_height / 2 - i * Math.sin(angle));
						if (player_x > m_coord.X()) {
							checkX = (int) (m_coord.X() + i * Math.cos(angle));
						} else {
							checkX = (int) (m_coord.X() - i * Math.cos(angle));
						}
						if (m_model.m_room.isBlocked(checkX, checkY)) {
							return false;
						}
						i += 40;
					}
					return true;

				}
			}
		}
		return false;

	}

	@Override
	public boolean cell(Direction dir, Category cat) {
		boolean b = super.cell(dir, cat);
		if (b) {
			switch (dir.toString()) {
			case Direction.Hs:
				if (cat == Category.P) {
					collidingWith = m_model.getPlayer();
					return true;
				}
			}
		}
		return b;


	}
}
