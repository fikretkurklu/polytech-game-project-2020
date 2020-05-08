package underworld;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Iterator;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import automaton.Entity;
import automaton.Entity.Action;
import environnement.Element;
import game.Coord;
import game.Model;
import projectile.Projectile;
import underworld.PlayerSoul;

public class Ghost extends Entity {

	public static final int SPEED = 1;
	public static final int MAX_RANGE = 450;

	public static int SIZE = (int) (Element.SIZE);

	public static final Direction[] dirs = { Direction.N, Direction.E, Direction.W, Direction.S };

	int SPEED_WALK_TICK = 4;

	Coord m_coord;
	Model m_model;
	Direction m_direction;

	int m_width = SIZE, m_height = SIZE;
	boolean leftOrientation, movingUp, movingDown, move;
	boolean isAttacking = false, isFollowing = false, isLure, isBuffed;
	int m_range = 200, m_size = SIZE;;
	Rectangle m_hitbox;

	public Ghost(Direction dir, Coord coord, Automaton automaton, Model model, Image[] images, HashMap<Action, int[]> hmAction) {
		super(automaton, images, hmAction);
		currentAction = Action.DEFAULT;
		m_model = model;
		m_coord = new Coord(coord);
		m_direction = dir;
		m_hitbox = new Rectangle(m_coord.X(), m_coord.Y(), SIZE, SIZE);
		resetAnim();
	}

	public void buff() {
		if (m_range < MAX_RANGE)
			m_range = m_range + 50;
		if (!isBuffed) {
			m_size = (int) (1.5 * m_size);
			m_hitbox.setSize(m_size, m_size);
			m_width = m_size;
			m_height = m_size;
		}
		isBuffed = true;
	}

	@Override
	public boolean turn(Direction dir) {
		boolean flag = false;
		Direction randDir = dirs[(int) (Math.random() * 4)];
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
		Coord lureCoord, tmpCoord;
		int d;
		lureCoord = getPlayer().getProjectiles().get(0).getCoord();
		int min = distance(m_coord, lureCoord);
		Iterator<Projectile> it = getPlayer().getProjectiles().iterator();
		while (it.hasNext()) {
			tmpCoord = it.next().getCoord();
			d = distance(m_coord, tmpCoord);
			if (d <= min) {
				min = d;
				lureCoord = tmpCoord;
			}
		}
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
		Coord tmpCoord;
		quitAttackMode();
		int d = 0;
		if (cat == Category.A || cat == Category.C) {
			Coord playerCoord = null;
			if (cat == Category.A) {
				playerCoord = getPlayer().getCoord();
				isLure = false;
			} else if (getPlayer().lureActive()) {
				playerCoord = getPlayer().getProjectiles().get(0).getCoord();
				int min = distance(m_coord, playerCoord);
				Iterator<Projectile> it = getPlayer().getProjectiles().iterator();
				while (it.hasNext()) {
					tmpCoord = it.next().getCoord();
					d = distance(m_coord, tmpCoord);
					if (d <= min) {
						min = d;
						playerCoord = tmpCoord;
					}
				}
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
				d = distance(playerCoord, m_coord); 
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
		Coord block, playerBlock, tmpCoord;
		Coord coord = null;
		if (cat == Category.O) {
			isFollowing = false;
			if (dir == Direction.F) {
				switch (m_direction.toString()) {
				case Direction.Ns:
//					block = getBlockCoord(m_coord.X(), m_coord.Y() - SIZE);
					return m_model.m_underworld.isBlocked(m_coord.X(), m_coord.Y());
				case Direction.Ss:
//					block = getBlockCoord(m_coord.X(), m_coord.Y() + SIZE);
					return m_model.m_underworld.isBlocked(m_coord.X(), m_coord.Y() + m_size);
				case Direction.Ws:
//					block = getBlockCoord(m_coord.X() - SIZE, m_coord.Y());
					return m_model.m_underworld.isBlocked(m_coord.X(), m_coord.Y());
				case Direction.Es:
//					block = getBlockCoord(m_coord.X() + SIZE, m_coord.Y());
					return m_model.m_underworld.isBlocked(m_coord.X() + m_size, m_coord.Y());
				default:
					return false;
				}

			}
		} else if (cat == Category.A) {
			coord = getPlayer().getCoord();
			isLure = false;
		} else if (cat == Category.C) {
			if (getPlayer().lureActive()) {
				isLure = true;
				coord = getPlayer().getProjectiles().get(0).getCoord();
				int min = distance(m_coord, coord);
				Iterator<Projectile> it = getPlayer().getProjectiles().iterator();
				int d;
				while (it.hasNext()) {
					tmpCoord = it.next().getCoord();
					d = distance(m_coord, tmpCoord);
					if (d <= min) {
						min = d;
						coord = tmpCoord;
					}
				}
			} else
				return false;
		}
		// Modifier egalités de coord pour garder une distance
//		switch (dir.toString()) {
//		case Direction.Ns:
//			block = getBlockCoord(m_coord.X(), m_coord.Y() - m_size);
//			playerBlock = getBlockCoord(coord.X(), coord.Y());
//			return block.isEqual(playerBlock) && Math.abs(coord.Y() - m_coord.Y()) <= 50;
//		case Direction.Ss:
//			block = getBlockCoord(m_coord.X(), m_coord.Y() + m_size);
//			playerBlock = getBlockCoord(coord.X(), coord.Y());
//			return block.isEqual(playerBlock) && Math.abs(coord.Y() - m_coord.Y()) <= 50;
//		case Direction.Es:
//			block = getBlockCoord(m_coord.X() + m_size, m_coord.Y());
//			playerBlock = getBlockCoord(coord.X(), coord.Y());
//			return block.isEqual(playerBlock) && Math.abs(coord.X() - m_coord.X()) <= 50;
//		case Direction.Ws:
//			block = getBlockCoord(m_coord.X() - m_size, m_coord.Y());
//			playerBlock = getBlockCoord(coord.X(), coord.Y());
//			return block.isEqual(playerBlock) && Math.abs(coord.X() - m_coord.X()) <= 50;
//		case Direction.NEs:
//			block = getBlockCoord(m_coord.X() + m_size, m_coord.Y() - m_size);
//			playerBlock = getBlockCoord(coord.X(), coord.Y());
//			return block.isEqual(playerBlock) && Math.abs(coord.X() - m_coord.X()) <= 50
//					&& Math.abs(coord.Y() - m_coord.Y()) <= 50;
//		case Direction.NWs:
//			block = getBlockCoord(m_coord.X() - m_size, m_coord.Y() - m_size);
//			playerBlock = getBlockCoord(coord.X(), coord.Y());
//			return block.isEqual(playerBlock) && Math.abs(coord.X() - m_coord.X()) <= 50
//					&& Math.abs(coord.Y() - m_coord.Y()) <= 50;
//		case Direction.SEs:
//			block = getBlockCoord(m_coord.X() + m_size, m_coord.Y() + m_size);
//			playerBlock = getBlockCoord(coord.X(), coord.Y());
//			return block.isEqual(playerBlock) && Math.abs(coord.X() - m_coord.X()) <= 50
//					&& Math.abs(coord.Y() - m_coord.Y()) <= 50;
//		case Direction.SWs:
//			block = getBlockCoord(m_coord.X() - m_size, m_coord.Y() + m_size);
//			playerBlock = getBlockCoord(coord.X(), coord.Y());
//			return block.isEqual(playerBlock) && Math.abs(coord.X() - m_coord.X()) <= 50
//					&& Math.abs(coord.Y() - m_coord.Y()) <= 50;
//		case Direction.Hs:
//			return Math.abs(m_coord.X() - coord.X()) <= 5 && Math.abs(m_coord.Y() - coord.Y()) <= 5;
//		// return coord.isEqual(m_coord);
//		}
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
		if (!isLure)
			getPlayer().getDamage();
		boolean flag = false;
		isAttacking = true;
//		isFollowing = false;
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
		if (isAttacking || isFollowing) {
			isFollowing = false;
			isAttacking = false;
			resetAnim();
			m_direction = dirs[(int) (Math.random() * 4)];
		}
	}

	public void paint(Graphics g) {
		Image img;
		img = getImage();
		if (leftOrientation)
			g.drawImage(img, m_coord.X() + m_size, m_coord.Y(), -m_size, m_size, null);
		else
			g.drawImage(img, m_coord.X(), m_coord.Y(), m_size, m_size, null);
		g.setColor(Color.blue);
		g.drawRect(m_hitbox.x, m_hitbox.y, m_width, m_height);

	}

	public void tick(long elapsed) {
		m_imageElapsed += elapsed;
		if (m_imageElapsed > m_imageTick) {
			m_imageElapsed = 0;
			m_imageIndex++;
			if (isAttacking) {
				currentAction = Action.SHOT;

			} else {
				currentAction = Action.DEFAULT;
			}
			if (m_imageIndex >= currentIndex.length) {
				resetAnim();
			}
		}
		m_stepElapsed += elapsed;
		if (m_stepElapsed > m_stepTick) {
			m_stepElapsed -= m_stepTick;
			if (!isAttacking)
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
		boolean res = m_model.m_underworld.playerCreated && !getPlayer().escapedOrDead && !getPlayer().hidden;
		if (!res) {
			quitAttackMode();
		}
		return res;
	}

	public int distance(Coord x, Coord y) {
		return (int) Math.sqrt((x.X() - y.X()) * (x.X() - y.X()) + (x.Y() - y.Y()) * (x.Y() - y.Y()));
	}
}
