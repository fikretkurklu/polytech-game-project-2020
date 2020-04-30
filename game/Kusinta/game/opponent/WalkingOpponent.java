package opponent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import automaton.Category;
import automaton.Automaton;
import automaton.Direction;
import environnement.Element;
import game.Model;

public class WalkingOpponent extends Opponent {

	public static final int SIZE = (int) (1.5 * Element.SIZE);
	public static final int WALKING_SPEED = 1;

	protected enum CurrentState {
		isAttacking, isMoving, isDead
	};

	CurrentState m_state;

	int AttackStrength;

	long m_shotTime;

	Image[] deathSprite;
	Image[] walkingSprite;
	Image[] attackingSprite;
	int m_image_index, m_imageElapsed;

	Direction m_dir;

	public WalkingOpponent(Automaton automaton, int x, int y, Direction dir, Model model, int maxLife, int life,
			int attackSpeed, int resistance, int strength) throws Exception {

		super(automaton, x, y, dir, model, maxLife, life, attackSpeed, resistance, strength);

		int yCor = m_model.m_room.blockTop(x, y);
		m_coord.setY(y);

		m_imageElapsed = 0;
		m_state = CurrentState.isMoving;

		deathSprite = new Image[6];
		for (int i = 0; i < 6; i++) {
			deathSprite[i] = loadImage("resources/oppenent/demon/Death" + (i + 1) + ".png");
		}

		walkingSprite = new Image[6];
		for (int i = 0; i < 6; i++) {
			walkingSprite[i] = loadImage("resources/oppenent/demon/Walk" + (i + 1) + ".png");
		}

		attackingSprite = new Image[4];
		for (int i = 0; i < 4; i++) {
			attackingSprite[i] = loadImage("resources/oppenent/demon/Attack" + (i + 1) + ".png");
		}

		m_width = walkingSprite[0].getWidth(null);
		m_height = walkingSprite[0].getHeight(null);

		int w = (int) (m_width);
		int h = (int) (m_height);

		hitBox = new Rectangle(m_coord.X() - w / 2, m_coord.Y() - h - 10, w, h);

		m_image_index = 0;

	}

	@Override
	public void paint(Graphics gp) {
		// Draw hitbox
		gp.setColor(Color.blue);
		gp.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);

		Image image = null;
		switch (m_state) {
		case isDead:
			image = deathSprite[m_image_index];
			break;
		case isAttacking:
			image = attackingSprite[m_image_index];
			break;
		case isMoving:
			image = walkingSprite[m_image_index];
		}

		if (m_direction.toString().equals("E")) {
			gp.drawImage(image, m_coord.X() - (m_width / 2), m_coord.Y() - m_height, m_width, m_height, null);
		} else {
			gp.drawImage(image, m_coord.X() + (m_width / 2), m_coord.Y() - m_height, -m_width, m_height, null);
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
		m_automaton.step(this);

		m_imageElapsed += elapsed;
		if (m_imageElapsed > 200) {
			m_imageElapsed = 0;

			switch (m_state) {
			case isDead:
				if (m_image_index == 5) {
					m_model.getOpponent().remove(this);
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

		if (dir.toString().equals("E")) {
			if (cat.toString().equals("O")) {
				if (m_model.m_room.isBlocked(m_coord.X()+hitBox.x/2, m_coord.Y()+1)) {
					System.out.println("Oh Yeah");
					return false;
				} else {
					return true;
				}
				
//				if ((m_model.m_room.isBlocked(m_coord.X() + hitBox.x / 2, m_coord.Y() - m_height / 2)
//						|| m_model.m_room.isBlocked(m_coord.X() + hitBox.x / 2, m_coord.Y() - 1)
//						|| m_model.m_room.isBlocked(m_coord.X() + hitBox.x / 2, m_coord.Y() - m_height + 1))) {
//						//|| !m_model.m_room.isBlocked(m_coord.X() + hitBox.x / 2, m_coord.Y() + 1)) {
//					return true;
//				}

			} else if (cat.toString().equals("A")) {
				if (m_model.getPlayer().getHitBox().contains(m_coord.X() + hitBox.x, m_coord.Y() - hitBox.y / 2)) {
					return true;
				}
			}
		} else if (dir.toString().equals("W")) {
			if (cat.toString().equals("O")) {
				if (m_model.m_room.isBlocked(m_coord.X()-hitBox.x/2, m_coord.Y()+1)) {
					System.out.println("Yeah");
					return false;
				} else {
					return true;
				}
				
//				if ((m_model.m_room.isBlocked(m_coord.X() - hitBox.x / 2, m_coord.Y() - m_height / 2)
//						|| m_model.m_room.isBlocked(m_coord.X() - hitBox.x / 2, m_coord.Y() - 1)
//						|| m_model.m_room.isBlocked(m_coord.X() - hitBox.x / 2, m_coord.Y() - m_height + 1))) {
//						//|| !m_model.m_room.isBlocked(m_coord.X() - hitBox.x / 2, m_coord.Y() + 1)) {
//					return true;
//				}
			} else if (cat.toString().equals("A")) {
				if (m_model.getPlayer().getHitBox().contains(m_coord.X() - hitBox.x, m_coord.Y() - hitBox.y / 2)) {
					return true;
				}
			}
		} else if (dir.toString().equals("H")) {
			if (cat.toString().equals("A")) {
				return false;
//				int xHB = m_model.getPlayer().getHitBox().x;
//				int yHB = m_model.getPlayer().getHitBox().y;
//				int widthHB = m_model.getPlayer().getHitBox().width;
//				int heightHB = m_model.getPlayer().getHitBox().height;
//				if (hitBox.contains(xHB, yHB) || hitBox.contains(xHB + widthHB / 2, yHB)
//						|| hitBox.contains(xHB + widthHB, yHB) || hitBox.contains(xHB + widthHB, yHB + heightHB / 2)
//						|| hitBox.contains(xHB + widthHB, yHB + heightHB)
//						|| hitBox.contains(xHB + widthHB / 2, yHB + heightHB) || hitBox.contains(xHB, yHB + heightHB)
//						|| hitBox.contains(xHB, yHB + heightHB / 2)
//						|| hitBox.contains(xHB + widthHB / 2, yHB + widthHB / 2)) {
//					collidingWith = m_model.getPlayer();
//					return true;
//				}
			}
		}
		return false;

	}

	@Override
	public boolean closest(Category cat, Direction dir) {
		int xPlayer = m_model.getPlayer().getCoord().X();
		int yPlayer = m_model.getPlayer().getCoord().Y();
		if (yPlayer > hitBox.y - 1 && yPlayer < hitBox.y + hitBox.height) {
			if (dir.toString().equals("E")) {
				if (xPlayer > hitBox.x + hitBox.width && xPlayer < hitBox.x + 3 * hitBox.width) {
					int intervalle = Math.abs((xPlayer - m_coord.X()) / 10);
					for (int i = 0; i < 10; i++) {
						if (!m_model.m_room.isBlocked(m_coord.X() + i * intervalle, m_coord.Y() + 1)) {
							return false;
						}
					}
					return true;
				}
			} else if (dir.toString().equals("W")) {
				if (xPlayer > hitBox.x - 3 * hitBox.width && xPlayer < hitBox.x + 1) {
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
		return false;
	}

	@Override
	public boolean move(Direction dir) {
		if (!(m_state.equals(CurrentState.isDead))) {
			int m_x = m_coord.X();

			if (!(m_state.equals(CurrentState.isMoving))) {
				m_image_index = 0;
			}
			m_state = CurrentState.isMoving;

			if (m_direction.toString().equals("E")) {
				m_x += WALKING_SPEED;
				hitBox.translate(m_x - m_coord.X(), 0);
				m_coord.setX(m_x);
			} else {
				m_x -= WALKING_SPEED;
				hitBox.translate(-(m_coord.X() - m_x), 0);
				m_coord.setX(m_x);
			}
		}
		return true;
	}

	@Override
	public boolean pop(Direction dir) {
		if (!(m_state.equals(CurrentState.isDead))) {
			if (dir.toString().equals("E")) {
				hitBox.translate(2 * WALKING_SPEED, 0);
				m_coord.setX(m_coord.X() + 2 * WALKING_SPEED);
			} else if (dir.toString().equals("W")) {
				hitBox.translate(-2 * WALKING_SPEED, 0);
				m_coord.setX(m_coord.X() - 2 * WALKING_SPEED);
			}
		}
		return true;
	}

	@Override
	public boolean wizz(Direction dir) {
		m_state = CurrentState.isAttacking;
		return false;
	}

	@Override
	public boolean explode() {
		m_state = CurrentState.isDead;
		return true;
	}

	@Override
	public boolean power() {
		if (collidingWith != null) {
			if (m_state.equals(CurrentState.isAttacking)) {
				//collidingWith.loseLife(m_currentStatMap.get(AttackStrength));
			} else {
				//collidingWith.loseLife(m_currentStatMap.get(CurrentStat.Strength));
			}
		}
		return true;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
