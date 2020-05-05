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
import game.ImageLoader;
import game.Model;
import underworld.PlayerSoul;

public class Ghost extends Entity {

	public static final int SPEED = 2;

	public static final int SIZE = (int) (Element.SIZE);

	public static final Direction[] dirs = { Direction.N, Direction.E, Direction.W, Direction.S };

	int SPEED_WALK_TICK = 4;

	Coord m_coord;
	Model m_model;
	Direction m_direction;

	Image m_images[];
	long m_imageElapsed;
	int m_width, m_height = SIZE;
	boolean leftOrientation, movingUp, movingDown, move;
	int m_image_index;
	boolean isAttacking = false, isFollowing = false, isLure;
	int m_range = 200;
	Rectangle m_hitbox;
	private long m_moveElapsed;

	public Ghost(Direction dir, Coord coord, Automaton automaton, Model model) {
		super(automaton);
		m_model = model;
		m_coord = coord;
		m_direction = dir;
		m_hitbox = new Rectangle(m_coord.X(), m_coord.Y(), SIZE, SIZE);
		ImageLoader.loadImageGhost(SIZE, m_width, m_height, m_image_index, m_imageElapsed, leftOrientation);
	}

	@Override
	public boolean turn(Direction dir) {
		boolean flag = false;
		Direction randDir = dirs[(int) (Math.random()*3)];
		if (m_direction ==  Direction.W) {
			if (randDir == Direction.W) {
				m_direction = Direction.E;
			}else {
				m_direction = randDir;
			}
			flag = true;
		} else if (m_direction ==  Direction.N) {
			if (randDir == Direction.N) {
				m_direction = Direction.S;
			}else {
				m_direction = randDir;
			}
			flag = true;
		} else if (m_direction ==  Direction.E) {
			if (randDir == Direction.E || randDir == Direction.W) {
				m_direction = Direction.W;
				leftOrientation = true;
			}else {
				m_direction = randDir;
			}
			flag = true;
		} else if (m_direction ==  Direction.S) {
			if (randDir == Direction.S) {
				m_direction = Direction.N;
			}else {
				m_direction = randDir;
			}
			flag = true;
		}
		return flag;	}

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
		if (m_direction ==  Direction.W) {
			m_coord.translateX(-SPEED);
			leftOrientation = true;
			flag = true;
		} else if (m_direction ==  Direction.N) {
			m_coord.translateY(-SPEED);
			flag = true;
		} else if (m_direction ==  Direction.E) {
			m_coord.translateX(SPEED);
			leftOrientation = false;
			flag = true;
		} else if (m_direction ==  Direction.S) {
			m_coord.translateY(SPEED);
			flag = true;
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
			}else if (( getPlayer()).lure != null && !( getPlayer()).lure.disapered) {
				playerCoord = ( getPlayer()).lure.getCoord();
				isLure = true;
			}else
				return false;
			if (dir ==  Direction.N) {
				d = m_coord.Y() - playerCoord.Y();
				return (playerCoord.X() == m_coord.X()) && (d > 0 && d <= m_range);
			} else if (dir ==  Direction.S) {
				d = playerCoord.Y() - m_coord.Y();
				return (playerCoord.X() == m_coord.X()) && (d > 0 && d <= m_range);
			} else if (dir ==  Direction.E) {
				d = playerCoord.X() - m_coord.X();
				return (playerCoord.Y() == m_coord.Y()) && (d > 0 && d <= m_range);
			} else if (dir ==  Direction.W) {
				d = m_coord.X() - playerCoord.X();
				return (playerCoord.Y() == m_coord.Y()) && (d > 0 && d <= m_range);
			} else if (dir ==  Direction.NE) {
				d = (int) Math.sqrt((playerCoord.X() - m_coord.X()) * (playerCoord.X() - m_coord.X())
						+ (playerCoord.Y() - m_coord.Y()) * (playerCoord.Y() - m_coord.Y()));
				return (playerCoord.Y() < m_coord.Y()) && (playerCoord.X() > m_coord.X()) && (d <= m_range);
			} else if (dir ==  Direction.SE) {
				d = (int) Math.sqrt((playerCoord.X() - m_coord.X()) * (playerCoord.X() - m_coord.X())
						+ (playerCoord.Y() - m_coord.Y()) * (playerCoord.Y() - m_coord.Y()));
				return (playerCoord.Y() > m_coord.Y()) && (playerCoord.X() > m_coord.X()) && (d <= m_range);
			} else if (dir ==  Direction.SW) {
				d = (int) Math.sqrt((playerCoord.X() - m_coord.X()) * (playerCoord.X() - m_coord.X())
						+ (playerCoord.Y() - m_coord.Y()) * (playerCoord.Y() - m_coord.Y()));
				return (playerCoord.Y() > m_coord.Y()) && (playerCoord.X() < m_coord.X()) && (d <= m_range);
			} else if (dir ==  Direction.NW) {
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
				if (m_direction ==  Direction.N) {
					block = getBlockCoord(m_coord.X(), m_coord.Y() - SIZE);
					return m_model.m_underworld.isBlocked(block.X(), block.Y());
				} else if (m_direction ==  Direction.S) {
					block = getBlockCoord(m_coord.X(), m_coord.Y() + SIZE);
					return m_model.m_underworld.isBlocked(block.X(), block.Y());
				} else if (m_direction ==  Direction.W) {
					block = getBlockCoord(m_coord.X() - SIZE, m_coord.Y());
					return m_model.m_underworld.isBlocked(block.X(), block.Y());
				} else if (m_direction ==  Direction.E) {
					block = getBlockCoord(m_coord.X() + SIZE, m_coord.Y());
					return m_model.m_underworld.isBlocked(block.X(), block.Y());
				}
				return false;
			}
		} else if (cat == Category.A) {
			coord = getPlayer().getCoord();
			isLure = false;
		} else if (cat == Category.C) {
			if (( getPlayer()).lure != null && !( getPlayer()).lure.disapered) {
				coord = ( getPlayer()).lure.getCoord();
				isLure = true;
			} else
				return false;
		}
		// Modifier egalités de coord pour garder une distance

		if (dir ==  Direction.N) {
			block = getBlockCoord(m_coord.X(), m_coord.Y() - SIZE);
			playerBlock = getBlockCoord(coord.X(), coord.Y());
			return block.isEqual(playerBlock) && Math.abs(coord.Y() - m_coord.Y()) <= 50;
		} else if (dir ==  Direction.S) {
			block = getBlockCoord(m_coord.X(), m_coord.Y() + SIZE);
			playerBlock = getBlockCoord(coord.X(), coord.Y());
			return block.isEqual(playerBlock) && Math.abs(coord.Y() - m_coord.Y()) <= 50;
		} else if (dir ==  Direction.E) {
			block = getBlockCoord(m_coord.X() + SIZE, m_coord.Y());
			playerBlock = getBlockCoord(coord.X(), coord.Y());
			return block.isEqual(playerBlock) && Math.abs(coord.X() - m_coord.X()) <= 50;
		} else if (dir ==  Direction.W) {
			block = getBlockCoord(m_coord.X() - SIZE, m_coord.Y());
			playerBlock = getBlockCoord(coord.X(), coord.Y());
			return block.isEqual(playerBlock) && Math.abs(coord.X() - m_coord.X()) <= 50;
		} else if (dir ==  Direction.NE) {
			block = getBlockCoord(m_coord.X() + SIZE, m_coord.Y() - SIZE);
			playerBlock = getBlockCoord(coord.X(), coord.Y());
			return block.isEqual(playerBlock) && Math.abs(coord.X() - m_coord.X()) <= 50
					&& Math.abs(coord.Y() - m_coord.Y()) <= 50;
		} else if (dir ==  Direction.NW) {
			block = getBlockCoord(m_coord.X() - SIZE, m_coord.Y() - SIZE);
			playerBlock = getBlockCoord(coord.X(), coord.Y());
			return block.isEqual(playerBlock) && Math.abs(coord.X() - m_coord.X()) <= 50
					&& Math.abs(coord.Y() - m_coord.Y()) <= 50;
		} else if (dir ==  Direction.SE) {
			block = getBlockCoord(m_coord.X() + SIZE, m_coord.Y() + SIZE);
			playerBlock = getBlockCoord(coord.X(), coord.Y());
			return block.isEqual(playerBlock) && Math.abs(coord.X() - m_coord.X()) <= 50
					&& Math.abs(coord.Y() - m_coord.Y()) <= 50;
		} else if (dir ==  Direction.SW) {
			block = getBlockCoord(m_coord.X() - SIZE, m_coord.Y() + SIZE);
			playerBlock = getBlockCoord(coord.X(), coord.Y());
			return block.isEqual(playerBlock) && Math.abs(coord.X() - m_coord.X()) <= 50
					&& Math.abs(coord.Y() - m_coord.Y()) <= 50;
		} else if (dir ==  Direction.H) {
			return Math.abs(m_coord.X()-coord.X()) <= 5 && Math.abs(m_coord.Y()-coord.Y()) <= 5;
			// return coord.isEqual(m_coord);
		}
		return false;
	}

	public boolean PopOrWizz(Direction dir, Coord coord) {
//		int d = (int) Math.sqrt((coord.X() - m_coord.X()) * (coord.X() - m_coord.X())
//				+ (coord.Y() - m_coord.Y()) * (coord.Y() - m_coord.Y()));
		isFollowing = true;
		boolean flag = false;
		if (dir ==  Direction.W) {
			m_direction = dir;
			m_coord.translateX(-SPEED);
			leftOrientation = true;
			flag = true;
		} else if (dir ==  Direction.E) {
			m_direction = dir;
			m_coord.translateX(SPEED);
			leftOrientation = false;
			flag = true;
		} else if (dir ==  Direction.N) {
			m_direction = dir;
			m_coord.translateY(-SPEED);
			flag = true;
		} else if (dir ==  Direction.S) {
			m_direction = dir;
			m_coord.translateY(SPEED);
			flag = true;
		} else if (dir ==  Direction.NE) {
			m_direction = Direction.N;
			m_coord.translate((int)(SPEED/Math.sqrt(2)), (int)(-SPEED/Math.sqrt(2)));
			leftOrientation = false;
			flag = true;
		} else if (dir ==  Direction.NW) {
			m_direction = Direction.N;
			leftOrientation = true;
			m_coord.translate((int)(-SPEED/Math.sqrt(2)), (int)(-SPEED/Math.sqrt(2)));
			flag = true;
		} else if (dir ==  Direction.SW) {
			m_direction = Direction.S;
			leftOrientation = true;
			m_coord.translate((int)(-SPEED/Math.sqrt(2)), (int)(SPEED/Math.sqrt(2)));
			flag = true;
		} else if (dir ==  Direction.SE) {
			m_direction = Direction.S;
			leftOrientation = false;
			m_coord.translate((int)(SPEED/Math.sqrt(2)), (int)(SPEED/Math.sqrt(2)));
			flag = true;
		}
		if (flag)
			m_hitbox.setLocation(m_coord.X(), m_coord.Y());
		return flag;
	}

	public boolean hit(Direction dir) {
		isAttacking = true;
		if (dir ==  Direction.W) {
			leftOrientation = true;
			movingUp = false;
			movingDown = false;
		} else if (dir ==  Direction.E) {
			leftOrientation = false;
			movingUp = false;
			movingDown = false;
		} else if (dir ==  Direction.N) {
			movingUp = true;
			movingDown = false;
		} else if (dir ==  Direction.S) {
			movingUp = false;
			movingDown = true;
		} else if (dir ==  Direction.NE) {
			leftOrientation = false;
			movingUp = true;
			movingDown = false;
		} else if (dir ==  Direction.NW) {
			leftOrientation = true;
			movingUp = true;
			movingDown = false;
		} else if (dir ==  Direction.SW) {
			leftOrientation = true;
			movingUp = false;
			movingDown = true;
		} else if (dir ==  Direction.SE) {
			leftOrientation = false;
			movingUp = false;
			movingDown = true;
		} else if (dir ==  Direction.H) {

		}
		return true;
	}

	public void quitAttackMode() {
		if (isAttacking) {
			isAttacking = false;
			m_image_index = 0;
			m_direction = dirs[(int) (Math.random()*3)];
		}
	}

	public void paint(Graphics g) {
		if (m_images != null) {
//			calculateHitbox();
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
				if (m_image_index > 8) {
					if (!isLure)
						getPlayer().getDamage();
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
	
	public boolean gotstuff(){
		return m_model.m_underworld.playerCreated;
	}
}
