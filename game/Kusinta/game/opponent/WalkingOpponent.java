package opponent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.HashMap;

import automaton.Category;
import automaton.Automaton;
import automaton.Direction;
import automaton.Entity.Action;
import environnement.Element;
import game.Coord;
import game.ImageLoader;
import game.Model;
import player.Character;

public class WalkingOpponent extends Opponent {

	public static final int SIZE = (int) (1.5 * Element.SIZE);
	int AttackStrength;

	int hHitBox, wHitBox;

	public WalkingOpponent(Automaton automaton, Coord C, Direction dir, Model model, Image[] bImages,
			HashMap<Action, int[]> indiceAction) throws Exception {

		super(automaton, C, dir, model, 100, 100, 1000, 100, 5, bImages, indiceAction);

		while (!m_model.m_room.isBlocked(m_coord)) {
			m_coord.translateY(40);
		}
		m_coord.setY(getM_model().m_room.blockTop(m_coord.X(), m_coord.Y()));

		X_MOVE = 2;

		m_imageElapsed = 0;
		shooting = false;

		AttackStrength = m_currentStatMap.get(CurrentStat.Strength) * 2;


		wHitBox = (int) (m_width * 0.7);
		hHitBox = (int) (m_height * 0.8);

		hitBox = new Rectangle(m_coord.X() - wHitBox / 2, m_coord.Y() - hHitBox, wHitBox, hHitBox);
		setMoney(100);
		m_moveElapsed = 0;

	}

	@Override
	public void paint(Graphics gp) {

		Image image = null;
		image = bImages[indiceAction.get(currentAction)[m_imageIndex]];

		double agr = 2;
		int w = (int) (m_width * agr);
		int h = (int) (m_height * agr);
		int decalage = (int) (((float) 3 / (float) 4) * h);

		if (m_direction == Direction.E) {
			gp.drawImage(image, m_coord.X() - (2 * w) / 5, m_coord.Y() - (decalage), w, h, null);
		} else {
			gp.drawImage(image, m_coord.X() + (2 * w) / 5, m_coord.Y() - (decalage), -w, h, null);
		}
		gp.setColor(Color.DARK_GRAY);
		gp.fillRect(hitBox.x, hitBox.y - 10, wHitBox, 10);
		if ((m_currentStatMap.get(CurrentStat.Life)) > 50) {
			gp.setColor(Color.GREEN);
		} else if ((m_currentStatMap.get(CurrentStat.Life)) > 25) {
			gp.setColor(Color.ORANGE);
		} else {
			gp.setColor(Color.RED);
		}

		float wi = wHitBox * ((float) (m_currentStatMap.get(CurrentStat.Life)) / 100);
		gp.fillRect(hitBox.x, hitBox.y - 10, (int) wi, 10);
		gp.setColor(Color.LIGHT_GRAY);
		gp.drawRect(hitBox.x, hitBox.y - 10, wHitBox, 10);

	}

	@Override
	public void tick(long elapsed) {
		super.tick(elapsed);

		m_imageElapsed += elapsed;
		if (m_imageElapsed > 200) {
			m_imageElapsed = 0;
			m_imageIndex ++;
			if (!gotpower()) {
				if (m_imageIndex == indiceAction.get(currentAction).length) {
					getM_model().getOpponent().remove(this);
					dropKey();
					try {
						getM_model().addCoin(new Coin(getM_model().coinDropAutomaton, m_coord.X(), m_coord.Y() - 5,
								m_money, getM_model()));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
			}
			if (m_imageIndex >= indiceAction.get(currentAction).length) {
				m_imageIndex = 0;
			}
		}
	}

	@Override
	public boolean cell(Direction dir, Category cat) {
		boolean c = super.cell(dir, cat);
		if (!c) {
			switch (dir.toString()) {
			case Direction.Es:
				if (cat == Category.O) {
					if (!getM_model().m_room.isBlocked(hitBox.x + hitBox.width + 1, hitBox.y + hitBox.height + 1)) {
						return true;
					}
				}
				break;
			case Direction.Ws:
				if (cat == Category.O) {
					if (!getM_model().m_room.isBlocked(hitBox.x, hitBox.y + hitBox.height + 1)) {
						return true;
					}
				}
				break;
			}
			if (shooting) {
				shooting = !shooting;
				currentAction = Action.MOVE;
				resetAnim();
			}
		} else {
			if (dir == Direction.H) {
				collidingWith = getM_model().getPlayer();
			}

		}

		return c;
	}

	@Override
	public boolean closest(Category cat, Direction dir) {
		if (getM_model().actualMode == Model.mode.ROOM) {
			if (getM_model().getPlayer().gotpower()) {
				int xPlayer = getM_model().getPlayer().getCoord().X();
				int yPlayer = getM_model().getPlayer().getCoord().Y();
				if (yPlayer >= hitBox.y
						&& yPlayer - getM_model().getPlayer().getHeight() / 2 <= hitBox.y + hitBox.height) {
					if (dir == Direction.E) {
						if (xPlayer > hitBox.x + hitBox.width && xPlayer < hitBox.x + hitBox.width / 2 + 500) {
							int intervalle = Math.abs((xPlayer - m_coord.X()) / 10);
							for (int i = 0; i < 10; i++) {
								if (!getM_model().m_room.isBlocked(m_coord.X() + i * intervalle, m_coord.Y() + 1)) {
									return false;
								}
							}
							return true;
						}
					} else if (dir == Direction.W) {
						if (xPlayer > hitBox.x + hitBox.width / 2 - 500 && xPlayer < hitBox.x + 1) {
							int intervalle = Math.abs((xPlayer - m_coord.X()) / 10);
							for (int i = 0; i < 10; i++) {
								if (!getM_model().m_room.isBlocked(m_coord.X() - i * intervalle, m_coord.Y() + 1)) {
									return false;
								}
							}
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean pop(Direction dir) {
		if (gotpower()) {
			X_MOVE *= 2;
			move(dir);
			X_MOVE /= 2;
		}
		return true;
	}

	@Override
	public boolean wizz(Direction dir) {
		if (!shooting) {
			resetAnim();
		}
		currentAction = Action.SHOT;
		shooting = true;
		return true;
	}

	@Override
	public boolean explode() {
		currentAction = Action.DEATH;
		setLife(0);
		return true;
	}

	@Override
	public boolean power() {
		if (collidingWith != null) {
			if (shooting) {
				collidingWith.loseLife(AttackStrength);
			} else {
				collidingWith.loseLife(m_currentStatMap.get(CurrentStat.Strength));
			}
		}
		return true;

	}

	public void attackHitBox() {
		if (m_direction == Direction.E) {
			hitBox.setBounds(hitBox.x, hitBox.y, (int) (1.3 * wHitBox), hHitBox);
		} else {
			int newW = (int) (1.3 * wHitBox);
			int newX = hitBox.x + hitBox.width - newW;
			hitBox.setBounds(newX, hitBox.y, newW, hHitBox);
		}
		hitBox.height -= 1;
	}

	public void basicHitBox() {
		if (m_direction == Direction.W) {
			int newX = hitBox.x + hitBox.width - wHitBox;
			hitBox.setBounds(newX, hitBox.y, wHitBox, hHitBox);
		} else {
			hitBox.setBounds(hitBox.x, hitBox.y, wHitBox, hHitBox);
		}
		hitBox.height -= 1;
	}

}
