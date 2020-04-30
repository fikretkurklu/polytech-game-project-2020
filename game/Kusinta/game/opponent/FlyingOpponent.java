package opponent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import game.Coord;
import game.Model;
import projectile.MagicProjectile;
import projectile.Projectile;

public class FlyingOpponent extends Opponent {

	public static final int SPEED_FLY = 1;

	protected boolean shooting;

	long m_shot_time;

	Image[] death;
	Image[] flight;
	Image[] attack;
	int m_image_index, m_imageElapsed;

	public FlyingOpponent(Automaton automaton, int x, int y, Direction dir, Model model, int maxLife, int life,
			int attackSpeed, int resistance, int strength) throws Exception {

		super(automaton, x, y, dir, model, maxLife, life, attackSpeed, resistance, strength);

		m_shot_time = System.currentTimeMillis();
		m_imageElapsed = 0;

		shooting = false;
		moving = false;
		dead = false;

		imageProjectiles = new Image[13];
		for (int i = 0; i < 13; i++) {
			imageProjectiles[i] = loadImage("resources/oppenent/jin/Magic_Attack" + (i + 1) + ".png");
		}
		death = new Image[6];
		for (int i = 0; i < 6; i++) {
			death[i] = loadImage("resources/oppenent/jin/Death" + (i + 1) + ".png");
		}

		flight = new Image[4];
		for (int i = 0; i < 4; i++) {
			flight[i] = loadImage("resources/oppenent/jin/Flight" + (i + 1) + ".png");
		}

		attack = new Image[4];
		for (int i = 0; i < 4; i++) {
			attack[i] = loadImage("resources/oppenent/jin/Attack" + (i + 1) + ".png");
		}

		m_width = flight[0].getWidth(null);
		m_height = flight[0].getHeight(null);

		int w = (int) (m_width / 1.7);
		int h = (int) (m_height / 1.3);

		hitBox = new Rectangle(m_coord.X() - w / 2, m_coord.Y() - h - 10, w, h);

		m_image_index = 0;

	}

	public boolean move(Direction dir) {
		if (!dead) {
			int m_x = m_coord.X();

			if (!moving) {
				m_image_index = 0;
			}
			moving = true;

			if (m_direction.toString().equals("E")) {
				m_x += SPEED_FLY;
				hitBox.translate(m_x - m_coord.X(), 0);
				m_coord.setX(m_x);
			} else {
				m_x -= SPEED_FLY;
				hitBox.translate(-(m_coord.X() - m_x), 0);
				m_coord.setX(m_x);
			}
		}
		return true;
	}

	public boolean explode() {
		dead = true;
		return true;
	}

	@Override
	public boolean egg(Direction dir) {
		if (!dead) {
			long now = System.currentTimeMillis();

			if (now - m_shot_time > m_currentStatMap.get(CurrentStat.Attackspeed)) {

				m_image_index = 0;

				shooting = true;

				Coord playerCoord = m_model.getPlayer().getCoord();
				int player_x = playerCoord.X();

				if (player_x > m_coord.X()) {
					turn(new Direction("E"));
				} else {
					turn(new Direction("W"));
				}

				shoot();

				m_shot_time = now;

				return true;
			}
		}
		return false;
	}

	@Override
	public void paint(Graphics g) {
//		g.setColor(Color.blue);
//		g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
		Image image;
		if (dead) {
			image = death[m_image_index];
		} else if (shooting) {
			image = attack[m_image_index];
		} else {
			image = flight[m_image_index];
		}
		if (m_direction.toString().equals("E")) {
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

		for (int i = 0; i < m_projectiles.size(); i++) {
			((MagicProjectile) m_projectiles.get(i)).paint(g);
		}
	}

	@Override
	public void tick(long elapsed) {
		m_automaton.step(this);

		m_imageElapsed += elapsed;
		if (m_imageElapsed > 200) {
			m_imageElapsed = 0;

			if (dead) {
				if (m_image_index == 5) {
					m_model.getOpponent().remove(this);
				}
				m_image_index = (m_image_index + 1) % 6;
			} else {
				m_image_index = (m_image_index + 1) % 4;
			}
			if (shooting) {
				if (m_image_index == 3) {
					shooting = false;
				}
			}
		}

		for (int i = 0; i < m_projectiles.size(); i++) {
			(m_projectiles.get(i)).tick(elapsed);
		}
	}

	@Override
	public boolean closest(Category cat, Direction dir) {
		boolean d = m_model.getPlayer().isDead();
		if (!d) {

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
		return false;
	}

	@Override
	public boolean cell(Direction dir, Category cat) {

		if (m_direction.toString().equals(dir.toString())) {
			if (dir.toString().equals("E")) {

				int x = hitBox.x + hitBox.width + 1;
				if (m_model.m_room.isBlocked(x, m_coord.Y() - m_height / 2)
						|| m_model.m_room.isBlocked(x, m_coord.Y() - 1)
						|| m_model.m_room.isBlocked(x, m_coord.Y() - m_height + 1)) {
					return true;
				} else {
					return false;
				}
			} else if (dir.toString().equals("W")) {
				int x = hitBox.x;
				if (m_model.m_room.isBlocked(x, m_coord.Y() - m_height / 2)
						|| m_model.m_room.isBlocked(x, m_coord.Y() - 1)
						|| m_model.m_room.isBlocked(x, m_coord.Y() - m_height + 1)) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	public void shoot() {
		if (shooting) {
			int m_x = m_coord.X();
			int m_y = m_coord.Y() - m_height / 2;

			Direction direc;
			float angle;
			double r;
			Coord playerCoord = m_model.getPlayer().getCoord();
			int player_x = playerCoord.X();
			int player_y = playerCoord.Y();

			int x = player_x - m_x;
			int y = m_y - player_y;

			if (player_x > m_x) {
				direc = new Direction("E");
			} else {
				direc = new Direction("W");
			}

			r = (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
			angle = (float) Math.asin(Math.abs(y) / r);

			if (player_y > m_y) {
				angle = -angle;
			}
			try {
				addProjectile(m_x, m_y, angle, this, direc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void addProjectile(int x, int y, double angle, FlyingOpponent opponent, Direction direction)
			throws Exception {
		m_projectiles.add(new MagicProjectile(m_model.arrowAutomaton, x, y, angle, opponent, direction));
	}

	public void removeProjectile(Projectile projectile) {
		m_projectiles.remove(projectile);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
	}

}
