package opponent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import automaton.Category;
import automaton.Automaton;
import automaton.Direction;
import environnement.Element;
import game.Coord;
import game.ImageLoader;
import game.Model;
import player.Character;

public class WalkingOpponent extends Opponent {

	public static final int SIZE = (int) (1.5 * Element.SIZE);

	protected enum CurrentState {
		isAttacking, isMoving, isDead
	};

	CurrentState m_state;

	int AttackStrength;

	Image[] deathSprite;
	Image[] walkingSprite;
	Image[] attackingSprite;
	int m_image_index, m_imageElapsed;

	boolean alreadyMove;

	public WalkingOpponent(Automaton automaton, Coord C, Direction dir, Model model) throws Exception {

		super(automaton, C, dir, model, 100, 100, 1000, 100, 5);
		while (m_model.m_room.isBlocked(m_coord)) {
			int c = m_model.m_room.getWitdh();
			int d = m_model.m_room.getWitdh();
			m_coord.setCoord((int) (Math.random() * (c + 1)), (int) (Math.random() * (d + 1)));
		}
		while (!m_model.m_room.isBlocked(m_coord)) {
			m_coord.translateY(40);
		}
		m_coord.setY(m_model.m_room.blockTop(m_coord));

		X_MOVE = 2;

		m_imageElapsed = 0;
		m_state = CurrentState.isMoving;

		AttackStrength = m_currentStatMap.get(CurrentStat.Strength) * 2;

		deathSprite = new Image[6];
		for (int i = 0; i < 6; i++) {
			deathSprite[i] = ImageLoader.loadImage("resources/oppenent/demon/Death" + (i + 1) + ".png", SIZE);
		}

		walkingSprite = new Image[6];
		for (int i = 0; i < 6; i++) {
			walkingSprite[i] = ImageLoader.loadImage("resources/oppenent/demon/Walk" + (i + 1) + ".png", SIZE);
		}

		attackingSprite = new Image[4];
		for (int i = 0; i < 4; i++) {
			attackingSprite[i] = ImageLoader.loadImage("resources/oppenent/demon/Attack" + (i + 1) + ".png", SIZE);
		}

		m_width = walkingSprite[0].getWidth(null) * 2;
		m_height = walkingSprite[0].getHeight(null) * 2;

		int w = (int) (m_width / 1.5) - 75;
		int h = (int) (m_height / 1.5) - 70;

		hitBox = new Rectangle(m_coord.X() - w / 2, m_coord.Y() - h, w, h);

		m_image_index = 0;

		m_money = 100;

		m_moveElapsed = 0;

	}

	@Override
	public void paint(Graphics gp) {
		// Draw hitbox
//		gp.setColor(Color.blue);
//		gp.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);

		Image image = null;
		switch (m_state) {
		case isDead:
			image = deathSprite[m_image_index];
			basicHitBox();
			break;
		case isAttacking:
			image = attackingSprite[m_image_index];
			attackHitBox();
			break;
		case isMoving:
			image = walkingSprite[m_image_index];
			basicHitBox();
			break;
		default:
			image = walkingSprite[m_image_index];
			basicHitBox();
			break;
		}

		if (m_direction == Direction.E) {
			gp.drawImage(image, hitBox.x - image.getWidth(null) / 2, m_coord.Y() - m_height + 70, m_width, m_height,
					null);
		} else {
			gp.drawImage(image, hitBox.x + hitBox.width + image.getWidth(null) / 2, m_coord.Y() - m_height + 70,
					-m_width, m_height, null);
		}
		gp.setColor(Color.DARK_GRAY);
		gp.fillRect(hitBox.x, hitBox.y - 10, hitBox.width, 10);
		if ((m_currentStatMap.get(CurrentStat.Life)) > 50) {
			gp.setColor(Color.GREEN);
		} else if ((m_currentStatMap.get(CurrentStat.Life)) > 25) {
			gp.setColor(Color.ORANGE);
		} else {
			gp.setColor(Color.RED);
		}

		float w = hitBox.width * ((float) (m_currentStatMap.get(CurrentStat.Life)) / 100);
		gp.fillRect(hitBox.x, hitBox.y - 10, (int) w, 10);
		gp.setColor(Color.LIGHT_GRAY);
		gp.drawRect(hitBox.x, hitBox.y - 10, hitBox.width, 10);

	}

	@Override
	public void tick(long elapsed) {
		super.tick(elapsed);

		m_imageElapsed += elapsed;
		if (m_imageElapsed > 200) {
			m_imageElapsed = 0;

			switch (m_state) {
			case isDead:
				if (m_image_index == 5) {
					m_model.getOpponent().remove(this);
					dropKey();
					try {
						m_model.addCoin(
								new Coin(m_model.coinDropAutomaton, m_coord.X(), m_coord.Y() - 5, m_money, m_model));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				m_image_index = (m_image_index + 1) % 6;
				break;
			case isAttacking:
				m_image_index = (m_image_index + 1) % 4;
				break;
			case isMoving:
				m_image_index = (m_image_index + 1) % 6;
				break;
			}

		}
	}

	@Override
	public boolean cell(Direction dir, Category cat) {
		boolean c = super.cell(dir, cat);
		if (!c) {
			switch (dir.toString()){
			case Direction.Es : 
				if (cat == Category.O) {
					if (!m_model.m_room.isBlocked(hitBox.x + hitBox.width + 1, hitBox.y + hitBox.height + 1)) {
						return true;
					}
				} else if (cat == Category.P && m_model.actualMode == Model.mode.ROOM) {
					if (m_model.getPlayer().gotpower()) {
						if (m_model.getPlayer().getHitBox().contains(hitBox.width + hitBox.x + 5,
								hitBox.y + hitBox.height / 2)) {
							return true;
						}
					}
				}
				break;
			case Direction.Ws : 
				if (cat == Category.O) {
					if (!m_model.m_room.isBlocked(hitBox.x, hitBox.y + hitBox.height + 1)) {
						return true;
					}
				} else if (cat == Category.P && m_model.actualMode == Model.mode.ROOM) {
					if (m_model.getPlayer().gotpower()) {
						if (m_model.getPlayer().getHitBox().contains(hitBox.x - 5, hitBox.y + hitBox.height / 2)) {
							return true;
						}
					}
				}
				break;
			}
		} else {
			if (dir == Direction.H) {
				collidingWith = m_model.getPlayer();
			}
		}

		return c;
	}

	@Override
	public boolean closest(Category cat, Direction dir) {
		if (m_model.actualMode == Model.mode.ROOM) {
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
		}
		return false;
	}

	@Override
	public boolean move(Direction dir) {
		if (!alreadyMove) {
			if (!(m_state.equals(CurrentState.isDead))) {

				if (dir != m_direction) {
					turn(dir);
				}

				int m_x = m_coord.X();

				if (!(m_state.equals(CurrentState.isMoving))) {
					m_image_index = 0;
				}
				m_state = CurrentState.isMoving;

				super.move(dir);

				if (collidedWith != null) {
					collidedWith.getCoord().translate(m_coord.X() - m_x, 0);
				}
			}
		}

		alreadyMove = !alreadyMove;
		return true;
	}

	@Override
	public boolean pop(Direction dir) {
		if (!(m_state.equals(CurrentState.isDead))) {
			X_MOVE *= 2;
			move(dir);
			X_MOVE /= 2;
		}
		return true;
	}

	@Override
	public boolean wizz(Direction dir) {
		if (!m_state.equals(CurrentState.isAttacking)) {
			m_image_index = 0;
		}
		m_state = CurrentState.isAttacking;
		return false;
	}

	@Override
	public boolean explode() {
		if (!m_state.equals(CurrentState.isDead)) {
			m_image_index = 0;
		}
		m_state = CurrentState.isDead;
		return true;
	}

	@Override
	public boolean power() {
		if (collidingWith != null) {
			int tmpPlayerResistance = m_model.m_player.m_currentStatMap.get(Character.CurrentStat.Resistance);
			if (m_state.equals(CurrentState.isAttacking)) {
				collidingWith.loseLife(AttackStrength - tmpPlayerResistance);
			} else {
				collidingWith.loseLife(m_currentStatMap.get(CurrentStat.Strength) - tmpPlayerResistance);
			}
		}
		return true;
	}

	public void attackHitBox() {
		int w = (int) (m_width / 1.5) - 75;
		int h = (int) (m_height / 1.5) - 70;
		if (m_direction == Direction.E) {
			hitBox = new Rectangle(m_coord.X() - w / 2, m_coord.Y() - h, w + 40, h);
		} else {
			hitBox = new Rectangle(m_coord.X() - w / 2 - 40, m_coord.Y() - h, w + 40, h);
		}
	}

	public void basicHitBox() {
		int w = (int) (m_width / 1.5) - 75;
		int h = (int) (m_height / 1.5) - 70;

		hitBox = new Rectangle(m_coord.X() - w / 2, m_coord.Y() - h, w, h);
	}

}
