package opponent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import game.Coord;
import game.ImageLoader;
import game.Model;
import projectile.Projectile.proj;

public class FlyingOpponent extends Opponent {

	int m_imageElapsed;

	public FlyingOpponent(Automaton automaton, Coord C, Direction dir, Model model) throws Exception {

		super(automaton, C, dir, model, 100, 100, 1000, 100, 5);

		m_imageElapsed = 0;
		shooting = false;

		m_height = SIZE;
		m_width = (int) (m_height * ratio);
		
		int w = (int) (m_width / 1.7);
		int h = (int) (m_height / 1.3);

		hitBox = new Rectangle(m_coord.X() - w / 2, m_coord.Y() - h - 10, w, h);
		setMoney(200);
		m_moveElapsed = 0;

		X_MOVE = 2;

	}

	public boolean explode() {
		return true;
	}

	@Override
	public boolean egg(Direction dir) {
		if (gotpower() && !shooting) {

			currentAction = Action.SHOT;
			resetAnim();

			shooting = true;

			Coord playerCoord = getM_model().getPlayer().getCoord();
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
		BufferedImage img;

		img = bImages[indiceAction.get(currentAction)[m_imageIndex]];
		
		if (m_direction == Direction.E) {
			g.drawImage(img, m_coord.X() - (m_width / 2), m_coord.Y() - m_height, m_width, m_height, null);
		} else {
			g.drawImage(img, m_coord.X() + (m_width / 2), m_coord.Y() - m_height, -m_width, m_height, null);
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
			m_imageIndex ++;
			if (!gotpower()) {
				if (m_imageIndex == indiceAction.get(currentAction).length) {
					getM_model().getOpponent().remove(this);
					dropKey();
					getM_model().addCoin(new Coin(getM_model().coinDropAutomaton, m_coord.X(), m_coord.Y(), m_money, getM_model()));

				}
			}
			if (shooting) {
				if (m_imageIndex == indiceAction.get(currentAction).length) {
					Coord playerCoord = getM_model().getPlayer().getCoord();
					super.shoot(playerCoord.X(), playerCoord.Y() - getM_model().getPlayer().getHeight() / 2,
							proj.MAGIC_PROJECTILE);
				}
			}
			if (m_imageIndex >= indiceAction.get(currentAction).length) {
				m_imageIndex = 0;
			}
		}

		for (int i = 0; i < m_projectiles.size(); i ++) {
			m_projectiles.get(i).tick(elapsed);
		}
	}

	@Override
	public boolean closest(Category cat, Direction dir) {
		if (getM_model().actualMode == Model.mode.ROOM) {
			boolean d = getM_model().getPlayer().gotpower();
			if (d) {

				Coord playerCoord = getM_model().getPlayer().getCoord();
				int player_x = playerCoord.X();
				int player_y = playerCoord.Y() - getM_model().getPlayer().getHeight() / 2;
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
						if (getM_model().m_room.isBlocked(checkX, checkY)) {
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
