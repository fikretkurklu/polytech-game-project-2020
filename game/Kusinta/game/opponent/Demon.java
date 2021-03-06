package opponent;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.HashMap;

import automaton.Category;
import automaton.Automaton;
import automaton.Direction;
import entityFactory.Factory.Type;
import environnement.Element;
import game.Coord;
import game.Game;
import game.Model;

public class Demon extends Opponent {

	public static final int SIZE = (int) (1.5 * Element.SIZE);
	int AttackStrength;

	int hHitBox, wHitBox;

	boolean isDead;

	public Demon(Automaton automaton, Coord C, Direction dir, Model model, Image[] bImages,
			HashMap<Action, int[]> indiceAction) throws Exception {

		super(automaton, C, dir, model,
				(int) (50 * Model.difficultyLevel * model.m_player.getGoldGenerationMultiplier()),
				(int) (50 * Model.difficultyLevel * model.m_player.getGoldGenerationMultiplier()), 1000,
				(int) (10 * Model.difficultyLevel),
				(int) (8 * Model.difficultyLevel * model.m_player.getGoldGenerationMultiplier()), bImages,
				indiceAction);

		while (!m_model.m_room.isBlocked(m_coord)) {
			m_coord.translateY(40);
		}
		m_coord.setY(m_model.m_room.blockTop(m_coord.X(), m_coord.Y()));

		setSpeed(2);
		m_height = SIZE;
		m_width = (int) (m_height * ratio);

		shooting = false;

		isDead = false;

		AttackStrength = m_currentStatMap.get(CurrentStat.Strength) * 2;

		wHitBox = (int) (m_width * 0.8);
		hHitBox = (int) (m_height * 0.9);

		hitBox = new Rectangle(m_coord.X() - wHitBox / 2, m_coord.Y() - hHitBox, wHitBox, hHitBox - 1);
		int money = (int) (100 / ((float) (m_model.m_player.getGoldGenerationMultiplier() / Model.difficultyLevel)));
		setMoney(money);
		m_moveElapsed = 0;
		currentAction = Action.MOVE;
		resetAnim();

	}

	@Override
	public void paint(Graphics gp) {
		Image image = getImage();
		int w = m_width;
		int h = m_height;

		if (m_direction == Direction.E) {
			gp.drawImage(image, m_coord.X() - w / 2, m_coord.Y() - h, w, h, null);
		} else {
			gp.drawImage(image, m_coord.X() + w / 2, m_coord.Y() - h, -w, h, null);
		}

		super.paint(gp);

	}

	@Override
	public void tick(long elapsed) {
		super.tick(elapsed);
		m_imageElapsed += elapsed;
		if (m_imageElapsed > m_imageTick) {
			m_imageElapsed = 0;
			m_imageIndex++;
			if (!gotpower()) {
				if (m_imageIndex >= currentIndex.length) {
					dropKey();
					Coin c = (Coin) Game.m_factory.newEntity(Type.Coin, null, m_coord, m_model, 0, null);
					c.setMoney(m_money);
					m_model.addCoin(c);
					m_model.getM_opponentsToDelete().add(this);
				}
			}
			if (m_imageIndex >= currentIndex.length) {
				if (shooting) {
					shooting = !shooting;
					currentAction = Action.MOVE;
					resetAnim();
				}
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
					if (!m_model.m_room.isBlocked(hitBox.x + hitBox.width + 1, hitBox.y + hitBox.height + 1)) {
						return true;
					}
				}
				break;
			case Direction.Ws:
				if (cat == Category.O) {
					if (!m_model.m_room.isBlocked(hitBox.x, hitBox.y + hitBox.height + 1)) {
						return true;
					}
				}
				break;
			}
		}

		return c;
	}

	@Override
	public boolean closest(Category cat, Direction dir) {
		if (m_model.getPlayer().gotpower()) {
			int xPlayer = m_model.getPlayer().getCoord().X();
			int yPlayer = m_model.getPlayer().getCoord().Y();
			if (yPlayer >= hitBox.y && yPlayer - m_model.getPlayer().getHeight() / 2 <= hitBox.y + hitBox.height) {
				if (dir == Direction.E) {
					if (xPlayer > hitBox.x + hitBox.width && xPlayer < hitBox.x + hitBox.width / 2 + 500) {
						int intervalle = Math.abs((xPlayer - m_coord.X()) / 10);
						for (int i = 0; i < 10; i++) {
							if (!m_model.m_room.isBlocked(m_coord.X() + i * intervalle, m_coord.Y() + 1)) {
								return false;
							}

						}
						return true;
					}
				} else if (dir == Direction.W) {
					if (xPlayer > hitBox.x + hitBox.width / 2 - 500 && xPlayer < hitBox.x + 1) {
						int intervalle = Math.abs((xPlayer - m_coord.X()) / 10);
						for (int i = 0; i < 10; i++) {
							if (!m_model.m_room.isBlocked(m_coord.X() - i * intervalle, m_coord.Y() + 1)) {
								return false;
							}

						}
						return true;
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
		if (currentAction != Action.SHOT) {
			currentAction = Action.SHOT;
			resetAnim();
			m_model.getPlayer().loseLife(AttackStrength);
		}
		shooting = true;
		return true;
	}

	@Override
	public boolean explode() {
		if (!isDead) {
			currentAction = Action.DEATH;
			resetAnim();
			isDead = true;
		}
		setLife(0);
		return true;
	}

	@Override
	public boolean power() {
		m_model.getPlayer().loseLife(m_currentStatMap.get(CurrentStat.Strength));
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
