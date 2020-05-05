package underworld;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import automaton.Entity;
import environnement.Element;
import game.Coord;
import game.Model;
import underworld.PlayerSoul;

public class Ghost extends Entity {

	public static final int SPEED = 1;

	public static final int SIZE = (int) (Element.SIZE);

	public static final Direction[] dirs = { Direction.N, Direction.E, Direction.W, Direction.S };

	int SPEED_WALK_TICK = 4;

	Coord m_coord;
	Model m_model;
	Direction m_direction;
	Image m_images[];
	long m_imageElapsed;
	int m_width = SIZE, m_height = SIZE;
	boolean leftOrientation, movingUp, movingDown, move;
	int m_image_index;
	boolean isAttacking = false, isFollowing = false, isLure;
	int m_range = 200;
	Rectangle m_hitbox;
	private long m_moveElapsed = SPEED_WALK_TICK;

	public Ghost(Direction dir, Coord coord, Automaton automaton, Model model, Image[] images) {
		super(automaton);
		m_model = model;
		m_coord = coord;
		m_direction = dir;
		m_hitbox = new Rectangle(m_coord.X(), m_coord.Y(), SIZE, SIZE);
		m_images = images;
	}


	@Override
	public boolean turn(Direction dir) {
		boolean flag = false;
		Direction randDir = dirs[(int) (Math.random() * 3)];
		switch (m_direction.toString()) {
		case Direction.Ws:
			if (randDir == Direction.W) {
				m_direction = Direction.E;
			} else {
				m_direction = randDir;
			}
			flag = true;
			break;
		case Direction.Ns:
			if (randDir == Direction.N) {
				m_direction = Direction.S;
			} else {
				m_direction = randDir;
			}
			flag = true;
			break;
		case Direction.Es:
			if (randDir == Direction.E || randDir == Direction.W) {
				m_direction = Direction.W;
				leftOrientation = true;
			} else {
				m_direction = randDir;
			}
			flag = true;
			break;
		case Direction.Ss:
			if (randDir == Direction.S) {
				m_direction = Direction.N;
			} else {
				m_direction = randDir;
			}
			flag = true;
			break;
		}
		return flag;
	}

	@Override
	public boolean pop(Direction dir) {
		Coord playerCoord = getPlayer().getCoord();
		return PopOrWizz(dir, playerCoord);
	}

	@Override
	public boolean wizz(Direction dir) {
		Coord lureCoord = getPlayer().lure.getCoord();
		return PopOrWizz(dir, lureCoord);
	}

	@Override
	public boolean move(Direction dir) {
		boolean flag = false;
		switch (m_direction.toString()) {
		case Direction.Ws:
			m_coord.translateX(-SPEED);
			leftOrientation = true;
			flag = true;
			break;
		case Direction.Ns:
			m_coord.translateY(-SPEED);
			flag = true;
			break;
		case Direction.Es:
			m_coord.translateX(SPEED);
			leftOrientation = false;
			flag = true;
			break;
		case Direction.Ss:
			m_coord.translateY(SPEED);
			flag = true;
			break;
		}
		if (flag)
			m_hitbox.setLocation(m_coord.X(), m_coord.Y());
		return flag;
	}

	@Override
	public boolean closest(Category cat, Direction dir) {
		quitAttackMode();
		int d = 0;
		if (cat == Category.A || cat == Category.C) {
			Coord playerCoord = null;
			if (cat == Category.A) {
				playerCoord = getPlayer().getCoord();
				isLure = false;
			} else if ((getPlayer()).lure != null && !(getPlayer()).lure.disapered) {
				playerCoord = (getPlayer()).lure.getCoord();
				isLure = true;
			} else
				return false;
			switch (dir.toString()) {
			case Direction.Ns:
				d = m_coord.Y() - playerCoord.Y();
				return (playerCoord.X() == m_coord.X()) && (d > 0 && d <= m_range);
			case Direction.Ss:
				d = playerCoord.Y() - m_coord.Y();
				return (playerCoord.X() == m_coord.X()) && (d > 0 && d <= m_range);
			case Direction.Es:
				d = playerCoord.X() - m_coord.X();
				return (playerCoord.Y() == m_coord.Y()) && (d > 0 && d <= m_range);
			case Direction.Ws:
				d = m_coord.X() - playerCoord.X();
				return (playerCoord.Y() == m_coord.Y()) && (d > 0 && d <= m_range);
			case Direction.NEs:
				d = (int) Math.sqrt((playerCoord.X() - m_coord.X()) * (playerCoord.X() - m_coord.X())
						+ (playerCoord.Y() - m_coord.Y()) * (playerCoord.Y() - m_coord.Y()));
				return (playerCoord.Y() < m_coord.Y()) && (playerCoord.X() > m_coord.X()) && (d <= m_range);
			case Direction.SEs:
				d = (int) Math.sqrt((playerCoord.X() - m_coord.X()) * (playerCoord.X() - m_coord.X())
						+ (playerCoord.Y() - m_coord.Y()) * (playerCoord.Y() - m_coord.Y()));
				return (playerCoord.Y() > m_coord.Y()) && (playerCoord.X() > m_coord.X()) && (d <= m_range);
			case Direction.SWs:
				d = (int) Math.sqrt((playerCoord.X() - m_coord.X()) * (playerCoord.X() - m_coord.X())
						+ (playerCoord.Y() - m_coord.Y()) * (playerCoord.Y() - m_coord.Y()));
				return (playerCoord.Y() > m_coord.Y()) && (playerCoord.X() < m_coord.X()) && (d <= m_range);
			case Direction.NWs:
				d = (int) Math.sqrt((playerCoord.X() - m_coord.X()) * (playerCoord.X() - m_coord.X())
						+ (playerCoord.Y() - m_coord.Y()) * (playerCoord.Y() - m_coord.Y()));
				return (playerCoord.Y() < m_coord.Y()) && (playerCoord.X() < m_coord.X()) && (d <= m_range);
			}
		}
		return false;
	}

	@Override
	public boolean cell(Direction dir, Category cat) {
		Coord block, playerBlock;
		Coord coord = null;
		if (cat == Category.O) {
			if (dir == Direction.F) {
				if (m_direction == Direction.N) {
					block = getBlockCoord(m_coord.X(), m_coord.Y() - SIZE);
					return m_model.m_underworld.isBlocked(block.X(), block.Y());
				} else if (m_direction == Direction.S) {
					block = getBlockCoord(m_coord.X(), m_coord.Y() + SIZE);
					return m_model.m_underworld.isBlocked(block.X(), block.Y());
				} else if (m_direction == Direction.W) {
					block = getBlockCoord(m_coord.X(), m_coord.Y());
					return m_model.m_underworld.isBlocked(block.X(), block.Y());
				} else if (m_direction == Direction.E) {
					block = getBlockCoord(m_coord.X() + SIZE, m_coord.Y());
					return m_model.m_underworld.isBlocked(block.X(), block.Y());
				}
				return false;
			}
		} else if (cat == Category.A) {
			coord = getPlayer().getCoord();
			isLure = false;
		} else if (cat == Category.C) {
			if ((getPlayer()).lure != null && !(getPlayer()).lure.disapered) {
				coord = (getPlayer()).lure.getCoord();
				isLure = true;
			} else
				return false;
		}
		// Modifier egalités de coord pour garder une distance
		switch (dir.toString()) {
		case Direction.Ns:
			block = getBlockCoord(m_coord.X(), m_coord.Y() - SIZE);
			playerBlock = getBlockCoord(coord.X(), coord.Y());
			return block.isEqual(playerBlock) && Math.abs(coord.Y() - m_coord.Y()) <= 50;
		case Direction.Ss:
			block = getBlockCoord(m_coord.X(), m_coord.Y() + SIZE);
			playerBlock = getBlockCoord(coord.X(), coord.Y());
			return block.isEqual(playerBlock) && Math.abs(coord.Y() - m_coord.Y()) <= 50;
		case Direction.Es:
			block = getBlockCoord(m_coord.X() + SIZE, m_coord.Y());
			playerBlock = getBlockCoord(coord.X(), coord.Y());
			return block.isEqual(playerBlock) && Math.abs(coord.X() - m_coord.X()) <= 50;
		case Direction.Ws:
			block = getBlockCoord(m_coord.X() - SIZE, m_coord.Y());
			playerBlock = getBlockCoord(coord.X(), coord.Y());
			return block.isEqual(playerBlock) && Math.abs(coord.X() - m_coord.X()) <= 50;
		case Direction.NEs:
			block = getBlockCoord(m_coord.X() + SIZE, m_coord.Y() - SIZE);
			playerBlock = getBlockCoord(coord.X(), coord.Y());
			return block.isEqual(playerBlock) && Math.abs(coord.X() - m_coord.X()) <= 50
					&& Math.abs(coord.Y() - m_coord.Y()) <= 50;
		case Direction.NWs:
			block = getBlockCoord(m_coord.X() - SIZE, m_coord.Y() - SIZE);
			playerBlock = getBlockCoord(coord.X(), coord.Y());
			return block.isEqual(playerBlock) && Math.abs(coord.X() - m_coord.X()) <= 50
					&& Math.abs(coord.Y() - m_coord.Y()) <= 50;
		case Direction.SEs:
			block = getBlockCoord(m_coord.X() + SIZE, m_coord.Y() + SIZE);
			playerBlock = getBlockCoord(coord.X(), coord.Y());
			return block.isEqual(playerBlock) && Math.abs(coord.X() - m_coord.X()) <= 50
					&& Math.abs(coord.Y() - m_coord.Y()) <= 50;
		case Direction.SWs:
			block = getBlockCoord(m_coord.X() - SIZE, m_coord.Y() + SIZE);
			playerBlock = getBlockCoord(coord.X(), coord.Y());
			return block.isEqual(playerBlock) && Math.abs(coord.X() - m_coord.X()) <= 50
					&& Math.abs(coord.Y() - m_coord.Y()) <= 50;
		case Direction.Hs:
			return Math.abs(m_coord.X() - coord.X()) <= 5 && Math.abs(m_coord.Y() - coord.Y()) <= 5;
		// return coord.isEqual(m_coord);
		}
		return false;
	}

	public boolean PopOrWizz(Direction dir, Coord coord) {
//		int d = (int) Math.sqrt((coord.X() - m_coord.X()) * (coord.X() - m_coord.X())
//				+ (coord.Y() - m_coord.Y()) * (coord.Y() - m_coord.Y()));
		isFollowing = true;
		boolean flag = false;
		switch (dir.toString()) {
		case Direction.Ws:
			m_direction = dir;
			m_coord.translateX(-SPEED);
			leftOrientation = true;
			flag = true;
			break;
		case Direction.Es:
			m_direction = dir;
			m_coord.translateX(SPEED);
			leftOrientation = false;
			flag = true;
			break;
		case Direction.Ns:
			m_direction = dir;
			m_coord.translateY(-SPEED);
			flag = true;
			break;
		case Direction.Ss:
			m_direction = dir;
			m_coord.translateY(SPEED);
			flag = true;
			break;
		case Direction.NEs:
			m_direction = Direction.N;
			m_coord.translate(SPEED, -SPEED);
			leftOrientation = false;
			flag = true;
			break;
		case Direction.NWs:
			m_direction = Direction.N;
			leftOrientation = true;
			m_coord.translate(-SPEED, -SPEED);
			flag = true;
			break;
		case Direction.SWs:
			m_direction = Direction.S;
			leftOrientation = true;
			m_coord.translate(-SPEED, SPEED);
			flag = true;
			break;
		case Direction.SEs:
			m_direction = Direction.S;
			leftOrientation = false;
			m_coord.translate(SPEED, SPEED);
			flag = true;
			break;
		}
		if (flag)
			m_hitbox.setLocation(m_coord.X(), m_coord.Y());
		return flag;
	}

	public boolean hit(Direction dir) {
		boolean flag = false;
		isAttacking = true;
		switch (dir.toString()) {
		case Direction.Ws:
			leftOrientation = true;
			movingUp = false;
			movingDown = false;
			flag = true;
			break;
		case Direction.Es:
			leftOrientation = false;
			movingUp = false;
			movingDown = false;
			flag = true;
			break;
		case Direction.Ns:
			movingUp = true;
			movingDown = false;
			flag = true;
			break;
		case Direction.Ss:
			movingUp = false;
			movingDown = true;
			flag = true;
			break;
		case Direction.NEs:
			leftOrientation = false;
			movingUp = true;
			movingDown = false;
			flag = true;
			break;
		case Direction.NWs:
			leftOrientation = true;
			movingUp = true;
			movingDown = false;
			flag = true;
			break;
		case Direction.SWs:
			leftOrientation = true;
			movingUp = false;
			movingDown = true;
			flag = true;
			break;
		case Direction.SEs:
			leftOrientation = false;
			movingUp = false;
			movingDown = true;
			flag = true;
			break;
		case Direction.Hs:
			flag = true;
			break;
		}
		return flag;
	}

	public void quitAttackMode() {
		if (isAttacking) {
			isAttacking = false;
			m_image_index = 0;
			m_direction = dirs[(int) (Math.random() * 3)];
		}
	}

	public void paint(Graphics g) {
		if (m_images != null) {
			if (leftOrientation)
				g.drawImage(m_images[m_image_index], m_coord.X() + SIZE, m_coord.Y(), -SIZE, SIZE, null);
			else
				g.drawImage(m_images[m_image_index], m_coord.X(), m_coord.Y(), SIZE, SIZE, null);
			g.setColor(Color.blue);
			g.drawRect(m_hitbox.x, m_hitbox.y, m_width, m_height);
		}
	}

	public void tick(long elapsed) {
		m_imageElapsed += elapsed;
		if (m_imageElapsed > 200) {
			m_imageElapsed = 0;
			m_image_index++;
			if (isAttacking) {
				if (m_image_index == 4)
					if (!isLure)
						getPlayer().getDamage();
				if (m_image_index > 8) {
					m_image_index = 3;
				}
			} else {
				if (m_image_index >= 3) {
					m_image_index = 0;
				}
			}
		}
		m_moveElapsed += elapsed;
		if (m_moveElapsed > SPEED_WALK_TICK) {
			m_moveElapsed -= SPEED_WALK_TICK;
			m_automaton.step(this);
		}
	}

	PlayerSoul getPlayer() {
		return m_model.m_underworld.m_player;
	}

	Coord getBlockCoord(int x, int y) {
		return m_model.m_underworld.blockCoord(x, y);
	}

	public boolean gotstuff() {
		boolean res = m_model.m_underworld.playerCreated && !getPlayer().escapedOrDead;
		if (!res) {
			isAttacking = false;
		}
		return res;
	}
}
