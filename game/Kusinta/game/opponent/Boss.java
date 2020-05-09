package opponent;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.HashMap;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import game.Coord;
import game.Model;
import projectile.Projectile.proj;

public class Boss extends Opponent {

	int m_imageElapsed;
	int hitBoxPadding;

	public Boss(Automaton automaton, Coord C, Direction dir, Model model, Image[] bImages,
			HashMap<Action, int[]> indiceAction) throws Exception {

		super(automaton, C, dir, model, 10000, 10000, 4000, 200, 300, bImages, indiceAction);

		m_imageElapsed = 0;
		shooting = false;

		X_MOVE = 8;

		m_height = SIZE * 2;
		hitBoxPadding = m_height / 10;
		m_width = (int) (m_height * ratio);
		hitBox = new Rectangle(C.X() - m_width / 2 + hitBoxPadding, C.Y() - m_height + hitBoxPadding,
				m_width - 2 * hitBoxPadding, m_height - 2 * hitBoxPadding);

		currentAction = Action.MOVE;
		resetAnim();

	}

	public boolean explode() {
		currentAction = Action.DEATH;
		resetAnim();
		return true;
	}

	@Override
	public boolean egg(Direction dir) {
		if (gotpower() && !shooting) {
			currentAction = Action.SHOT;
			resetAnim();
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
		Image img;

		img = getImage();

		if (m_direction == Direction.E) {
			g.drawImage(img, m_coord.X() - (m_width / 2), m_coord.Y() - m_height, m_width, m_height, null);
		} else {
			g.drawImage(img, m_coord.X() + (m_width / 2), m_coord.Y() - m_height, -m_width, m_height, null);
		}

		super.paint(g);
		g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
		for (int i = 0; i < m_projectiles.size(); i++) {
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
			m_imageIndex++;
			if (!gotpower()) {
				if (m_imageIndex >= currentIndex.length) {
					m_model.getOpponent().remove(this);
				}
			}
			if (shooting) {
				if (m_imageIndex >= currentIndex.length) {
					Coord playerCoord = m_model.getPlayer().getCoord();
					super.shoot(playerCoord.X(), playerCoord.Y() - m_model.getPlayer().getHeight() / 4,
							proj.METEOR);
				}
			}
			if (m_imageIndex >= currentIndex.length) {
				m_imageIndex = 0;
			}
		}
		for (int i = 0; i < m_projectiles.size(); i++) {
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

				if (distance <= 800) {

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
