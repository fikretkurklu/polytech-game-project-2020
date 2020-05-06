package opponent;

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

public class Boss extends Opponent {

	private static String SPRITES = "resources/oppenent/boss/animation.png";
	private static String PATH_SHOT = "resources/oppenent/boss/attack/";
	public static int MAX_LIFE = 5000;
	public static int AS = 4000;
	public static int RESISTANCE = 50;
	public static int STRENGH = 40;

	int min_image_index, max_image_index;

	long m_imageElapsed = 0;
	long m_imageElapsedTick = 200;
	long m_moveElapsed;
	int SPEED_WALK_TICK = 8;

	public Boss(Automaton automaton, Coord C, Direction dir, Model model) throws Exception {
		super(automaton, C, dir, model, MAX_LIFE, MAX_LIFE, AS, RESISTANCE, STRENGH);
		bI = ImageLoader.loadBufferedSprite(SPRITES, 5, 27);

		float ratio = (float) ((float) bI[0].getWidth()) / (float) (bI[0].getHeight());

		m_height = SIZE;
		m_width = (int) (m_height * ratio);
		hitBox = new Rectangle(C.X() - m_width / 2, C.Y() - m_height, m_width, m_height);
		m_image_index = 45;

		setImageIndex(44, 53);
		X_MOVE = 2;
		imageProjectiles = new Image[56];
		for (int i = 0; i < 56; i++) {
			imageProjectiles[i] = ImageLoader.loadImage(PATH_SHOT + (i + 1) + ".png", SIZE);
		}
	}

	@Override
	public void paint(Graphics gp) {
		if (m_direction == Direction.E) {
			gp.drawImage(bI[m_image_index], m_coord.X() - m_width / 2, m_coord.Y() - m_height, m_width, m_height, null);
		} else {
			gp.drawImage(bI[m_image_index], m_coord.X() + m_width / 2, m_coord.Y() - m_height, -m_width, m_height,
					null);
		}
		for (int i = 0; i < m_projectiles.size(); i ++) {
			m_projectiles.get(i).paint(gp);
		}

	}

	@Override
	public void tick(long elapsed) {
		super.tick(elapsed);
		m_imageElapsed += elapsed;
		long attackspeed = m_imageElapsedTick;
		if (shooting) {
			attackspeed = m_currentStatMap.get(CurrentStat.Attackspeed);
			attackspeed = m_imageElapsedTick / (attackspeed / 1000);
		}
		if (m_imageElapsed > attackspeed) {
			m_imageElapsed = 0;
			m_image_index++;
			if (m_image_index > max_image_index || m_image_index < min_image_index)
				m_image_index = min_image_index;
			if (shooting && m_image_index == max_image_index) {
				Coord playerCoord = m_model.getPlayer().getCoord();
				super.shoot(playerCoord.X(), playerCoord.Y() - m_model.getPlayer().getHeight() / 2,
						proj.METEOR);
			}
		}
		for (int i = 0; i < m_projectiles.size(); i ++) {
			m_projectiles.get(i).tick(elapsed);;
		}
	}

	public void setImageIndex(int min, int max) {
		if (min_image_index != min || max_image_index != max) {
			m_imageElapsed = 200;
		}
		min_image_index = min;
		max_image_index = max;
	}

	public boolean move(Direction dir) {
		if (gotpower()) {
			int m_x = m_coord.X();

			setImageIndex(44, 53);

			if (!shooting)
				turn(dir);

			super.move(dir);

			if (collidedWith != null) {
				collidedWith.getCoord().translate(m_coord.X() - m_x, 0);
			}
		}
		return true;
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

	public boolean hit(Direction dir) {
		if (gotpower() && !shooting) {

			setImageIndex(0, 8);

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
}
