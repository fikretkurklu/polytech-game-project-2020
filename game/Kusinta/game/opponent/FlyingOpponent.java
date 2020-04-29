package opponent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;

import javax.imageio.ImageIO;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import environnement.Element;
import game.Coord;
import game.Model;
import projectile.MagicProjectile;
import projectile.Projectile;

public class FlyingOpponent extends Opponent {

	public static final int SIZE = (int) (1.5 * Element.SIZE);
	public static final int SPEED_FLY = 1;

	protected boolean shooting, moving, dead;

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

		hitBox = new Rectangle(m_coord.X() - m_width / 2, m_coord.Y() - m_height, m_width, m_height);

		m_image_index = 0;

	}

	public boolean move(Direction dir) {

//		System.out.println("moving");

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
//		}
		return true;
	}

	public boolean explode() {
		dead = true;
		return true;
	}

	@Override
	public boolean egg(Direction dir) {

		long now = System.currentTimeMillis();

		if (now - m_shot_time > m_attackSpeed && !shooting) {

			shooting = true;

			shoot();

			m_shot_time = now;

			return true;
		}

		return false;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.blue);
		g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
		Image image;
		if (shooting) {
			image = attack[m_image_index];
		} else if (dead) {
			image = death[m_image_index];
		} else {
			image = flight[m_image_index];
		}
		if (m_direction.toString().equals("E")) {
			g.drawImage(image, m_coord.X() - (m_width / 2), m_coord.Y() - m_height, m_width, m_height, null);
		} else {
			g.drawImage(image, m_coord.X() + (m_width / 2), m_coord.Y() - m_height, -m_width, m_height, null);
		}
	}

	@Override
	public void tick(long elapsed) {
		m_automaton.step(this);
		System.out.println(m_direction.toString());

		m_imageElapsed += elapsed;
		if (m_imageElapsed > 200) {
			m_imageElapsed = 0;

			if (dead) {
				m_image_index = (m_image_index + 1) % 6;
			} else {
				m_image_index = (m_image_index + 1) % 4;
			}
		}
	}

	@Override
	public boolean closest(Category cat, Direction dir) {
		System.out.println("closest");
		return false;
	}

	@Override
	public boolean cell(Direction dir, Category cat) {

		if (m_direction.toString().equals(dir.toString())) {
			System.out.println("dir = "+dir.toString());
			if (dir.toString().equals("E")) {

				int x = hitBox.x + hitBox.width + 1;
				System.out.println(x);
				if (m_model.m_room.isBlocked(x, m_coord.Y() - m_height / 2) || 
						m_model.m_room.isBlocked(x, m_coord.Y() - 1) ||
						m_model.m_room.isBlocked(x, m_coord.Y() - m_height +1) ) {
					return true;
				} else {
					return false;
				}
			} else if (dir.toString().equals("W")) {
				int x = hitBox.x;
				if (m_model.m_room.isBlocked(x, m_coord.Y() - m_height / 2) || 
						m_model.m_room.isBlocked(x, m_coord.Y() - 1) ||
						m_model.m_room.isBlocked(x, m_coord.Y() - m_height +1)) {
					return true;
				} else {
					return false;
				}
			}
		}

//		if (cat.toString().equals("A")) {
//			Coord playerCoord = m_model.getPlayer().getCoord();
//			int player_x = playerCoord.X();
//			int player_y = playerCoord.Y();
//			int x = player_x - m_coord.X();
//			int y = m_coord.Y() - m_height - player_y;
//
//			int distance = (int) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
//
//			if (distance <= 400) {
//
//				double r = (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
//				double angle = (float) Math.asin(Math.abs(y) / r);
//				int i = 0;
//
//				while (i < distance) {
//					int checkX;
//					if (player_x > m_coord.X()) {
//						checkX = (int) (m_coord.X() + i * Math.cos(angle));
//					} else {
//						checkX = (int) (m_coord.X() - i * Math.cos(angle));
//					}
//					if (m_model.m_room.isBlocked(checkX, (int) (m_coord.Y() - m_height - i * Math.sin(angle)))) {
//						return false;
//					}
//					i += 40;
//				}
//				return true;
//
//			}
//			return false;
//		}
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

			shooting = false;

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

}
