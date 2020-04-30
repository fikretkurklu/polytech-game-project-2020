package underworld;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import automaton.Entity;
import environnement.Element;
import game.Coord;
import game.Model;


public class Ghost extends Entity {

	public static final int SPEED = 1;

	public static final int SIZE = (int) (1.5*Element.SIZE);

	Coord m_coord;
	Model m_model;
	Direction m_direction;

	Image m_images[];
	long m_imageElapsed;
	int m_width, m_height = SIZE;

	int xHitbox[];
	int yHitbox[];
	boolean leftOrientation, movingUp, movingDown, move;
	int m_image_index;
	boolean isAttacking = false, isFollowing = false;
	int m_range = 300;
	Rectangle m_hitbox;

	public Ghost(Direction dir, Coord coord, Automaton automaton, Model model) {
		super(automaton);
		m_model = model;
		m_coord = coord;
		m_direction = dir;
		m_hitbox = new Rectangle(m_coord.X(), m_coord.Y(), SIZE, SIZE);
		loadImage();
	}

	private void loadImage() {
		m_width = SIZE;
		m_height = SIZE;
		int len = UnderworldParam.ghostImage.length;
		m_images = new Image[len];
		File imageFile;
		Image image;
		m_image_index = 0;
		m_imageElapsed = 0;
		leftOrientation = false;
		try {
			for (int i = 0; i < len; i++) {
				imageFile = new File(UnderworldParam.ghostImage[i]);
				image = ImageIO.read(imageFile);
				m_images[i] = image.getScaledInstance(SIZE, SIZE, 0);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	@Override
	public boolean turn(Direction dir) {
		return changeOrientation();
	}

	@Override
	public boolean pop(Direction dir) {
		Coord playerCoord = m_model.getPlayer().getCoord();
		return PopOrWizz(dir, playerCoord);
	}

	@Override
	public boolean wizz(Direction dir) {
		Coord lureCoord = ((PlayerSoul) m_model.getPlayer()).lure.getCoord();
		return PopOrWizz(dir, lureCoord);
	}

	@Override
	public boolean move(Direction dir) {
		move = false;
		boolean flag = false;
		if (m_direction.toString().equals("W")) {
			m_coord.translateX(-SPEED);
			leftOrientation = true;
			flag = true;
		} else if (m_direction.toString().equals("N")) {
			m_coord.translateY(-SPEED);
			flag = true;
		} else if (m_direction.toString().equals("E")) {
			m_coord.translateX(SPEED);
			leftOrientation = false;
			flag = true;
		} else if (m_direction.toString().equals("S")) {
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
		String catString = cat.toString();
//		if (((PlayerSoul) m_model.getPlayer()).hidden) {
//			return false;
//		}
		if (catString.equals("A") || catString.equals("C")) {
			Coord playerCoord = null;
			if (catString.equals("A"))
				playerCoord = getPlayer().getCoord();
			else if (((PlayerSoul) getPlayer()).lure != null
					&& !((PlayerSoul) getPlayer()).lure.disapered)
				playerCoord = ((PlayerSoul) getPlayer()).lure.getCoord();
			else
				return false;
			if (dir.toString().equals("N")) {
				d = m_coord.Y() - playerCoord.Y();
				return (playerCoord.X() == m_coord.X()) && (0 <= d && d <= m_range);
			} else if (dir.toString().equals("S")) {
				d = playerCoord.Y() - m_coord.Y();
				return (playerCoord.X() == m_coord.X()) && (0 <= d && d <= m_range);
			} else if (dir.toString().equals("E")) {
				d = playerCoord.X() - m_coord.X();
				return (playerCoord.Y() == m_coord.Y()) && (0 <= d && d <= m_range);
			} else if (dir.toString().equals("W")) {
				d = m_coord.X() - playerCoord.X();
				return (playerCoord.Y() == m_coord.Y()) && (0 <= d && d <= m_range);
			} else if (dir.toString().equals("N")) {
				d = playerCoord.X() - m_coord.X();
				return (playerCoord.Y() == m_coord.Y()) && (0 <= d && d <= m_range);
			} else if (dir.toString().equals("NE")) {
				d = (int) Math.sqrt((playerCoord.X() - m_coord.X()) * (playerCoord.X() - m_coord.X())
						+ (playerCoord.Y() - m_coord.Y()) * (playerCoord.Y() - m_coord.Y()));
				return (playerCoord.Y() < m_coord.Y()) && (playerCoord.X() > m_coord.X()) && (d <= m_range);
			} else if (dir.toString().equals("SE")) {
				d = (int) Math.sqrt((playerCoord.X() - m_coord.X()) * (playerCoord.X() - m_coord.X())
						+ (playerCoord.Y() - m_coord.Y()) * (playerCoord.Y() - m_coord.Y()));
				return (playerCoord.Y() > m_coord.Y()) && (playerCoord.X() > m_coord.X()) && (d <= m_range);
			} else if (dir.toString().equals("SW")) {
				d = (int) Math.sqrt((playerCoord.X() - m_coord.X()) * (playerCoord.X() - m_coord.X())
						+ (playerCoord.Y() - m_coord.Y()) * (playerCoord.Y() - m_coord.Y()));
				return (playerCoord.Y() > m_coord.Y()) && (playerCoord.X() < m_coord.X()) && (d <= m_range);
			} else if (dir.toString().equals("NW")) {
				d = (int) Math.sqrt((playerCoord.X() - m_coord.X()) * (playerCoord.X() - m_coord.X())
						+ (playerCoord.Y() - m_coord.Y()) * (playerCoord.Y() - m_coord.Y()));
				return (playerCoord.Y() < m_coord.Y()) && (playerCoord.X() < m_coord.X()) && (d <= m_range);
			}
		}
		return false;
	}

	private boolean changeOrientation() {
		boolean flag = false;
		if (m_direction.toString().equals("W")) {
			m_direction = new Direction("E");
			leftOrientation = false;
			flag = true;
		} else if (m_direction.toString().equals("N")) {
			m_direction = new Direction("S");
			flag = true;
		} else if (m_direction.toString().equals("E")) {
			m_direction = new Direction("W");
			leftOrientation = true;
			flag = true;
		} else if (m_direction.toString().equals("S")) {
			m_direction = new Direction("N");
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean cell(Direction dir, Category cat) {
		Coord block, playerBlock;
		Coord coord = null;
		if (cat.toString().equals("O")) {
			if (dir.toString().equals("F")) {
				if (m_direction.toString().equals("N")) {
					block = getBlockCoord(m_coord.X(), m_coord.Y() - SIZE);
					return m_model.m_underworld.isBlocked(block.X(), block.Y());
				} else if (m_direction.toString().equals("S")) {
					block = getBlockCoord(m_coord.X(), m_coord.Y() + SIZE);
					return m_model.m_underworld.isBlocked(block.X(), block.Y());
				} else if (m_direction.toString().equals("W")) {
					 block = getBlockCoord(m_coord.X() - SIZE, m_coord.Y());
					return m_model.m_underworld.isBlocked(block.X(), block.Y());
				} else if (m_direction.toString().equals("E")) {
					 block = getBlockCoord(m_coord.X() + SIZE, m_coord.Y());
					return m_model.m_underworld.isBlocked(block.X(), block.Y());
				}
			}
			// return m_model.m_underworld.isBlocked(m_coord.X(), m_coord.Y());
		} else if (cat.toString().equals("A")) {
//			if (((PlayerSoul) m_model.getPlayer()).hidden) {
//				return false;
//			}
			coord = getPlayer().getCoord();
			// return m_model.getPlayer().getCoord().isEqual(m_coord);
		} else if (cat.toString().equals("C")) {
			if (((PlayerSoul) getPlayer()).lure != null && !((PlayerSoul) getPlayer()).lure.disapered) {
				coord = ((PlayerSoul) getPlayer()).lure.getCoord();
				// return ((PlayerSoul) m_model.getPlayer()).lure.getCoord().isEqual(m_coord);
			}else
				return false;
		}
		// Modifier egalités de coord pour garder une distance

		if (dir.toString().equals("N")) {
			 block = getBlockCoord(m_coord.X(), m_coord.Y() - SIZE);
			 playerBlock = getBlockCoord(coord.X(), coord.Y());
			return block.isEqual(playerBlock) && Math.abs(coord.Y()-m_coord.Y()) <= 25;
		} else if (dir.toString().equals("S")) {
			 block = getBlockCoord(m_coord.X(), m_coord.Y() + SIZE);
			 playerBlock = getBlockCoord(coord.X(), coord.Y());
			return block.isEqual(playerBlock) && Math.abs(coord.Y()-m_coord.Y()) <= 25;
		} else if (dir.toString().equals("E")) {
			 block = getBlockCoord(m_coord.X() + SIZE, m_coord.Y());
			 playerBlock = getBlockCoord(coord.X(), coord.Y());
			return block.isEqual(playerBlock) && Math.abs(coord.X()-m_coord.X()) <= 25;
		} else if (dir.toString().equals("W")) {
			 block = getBlockCoord(m_coord.X() - SIZE, m_coord.Y());
			 playerBlock = getBlockCoord(coord.X(), coord.Y());
			return block.isEqual(playerBlock) && Math.abs(coord.X()-m_coord.X()) <= 25;
		} else if (dir.toString().equals("NE")) {
			 block = getBlockCoord(m_coord.X() + SIZE, m_coord.Y() - SIZE);
			 playerBlock = getBlockCoord(coord.X(), coord.Y());
			return block.isEqual(playerBlock) && Math.abs(coord.X()-m_coord.X()) <= 25 && Math.abs(coord.Y()-m_coord.Y()) <= 25;
		} else if (dir.toString().equals("NW")) {
			 block = getBlockCoord(m_coord.X() - SIZE, m_coord.Y() - SIZE);
			 playerBlock = getBlockCoord(coord.X(), coord.Y());
			return block.isEqual(playerBlock) && Math.abs(coord.X()-m_coord.X()) <= 25 && Math.abs(coord.Y()-m_coord.Y()) <= 25;
		} else if (dir.toString().equals("SE")) {
			 block = getBlockCoord(m_coord.X() + SIZE, m_coord.Y() + SIZE);
			 playerBlock = getBlockCoord(coord.X(), coord.Y());
			return block.isEqual(playerBlock) && Math.abs(coord.X()-m_coord.X()) <= 25 && Math.abs(coord.Y()-m_coord.Y()) <= 25;
		} else if (dir.toString().equals("SW")) {
			 block = getBlockCoord(m_coord.X() - SIZE, m_coord.Y() + SIZE);
			 playerBlock = getBlockCoord(coord.X(), coord.Y());
			return block.isEqual(playerBlock) && Math.abs(coord.X()-m_coord.X()) <= 25 && Math.abs(coord.Y()-m_coord.Y()) <= 25;
		} else if (dir.toString().equals("H")) {
			return Math.abs(coord.X()-m_coord.X()) <= 50 && Math.abs(coord.Y()-m_coord.Y()) <= 50;
			//return coord.isEqual(m_coord);
		}
		return false;
	}

	public boolean PopOrWizz(Direction dir, Coord coord) {
//		int d = (int) Math.sqrt((coord.X() - m_coord.X()) * (coord.X() - m_coord.X())
//				+ (coord.Y() - m_coord.Y()) * (coord.Y() - m_coord.Y()));
		isFollowing = true;
		boolean flag = false;
		if (dir.toString().equals("W")) {
			m_direction = dir;
			m_coord.translateX(-SPEED);
			leftOrientation = true;
			flag = true;
		} else if (dir.toString().equals("E")) {
			m_direction = dir;
			m_coord.translateX(SPEED);
			leftOrientation = false;
			flag = true;
		} else if (dir.toString().equals("N")) {
			m_direction = dir;
			m_coord.translateY(-SPEED);
			flag = true;
		} else if (dir.toString().equals("S")) {
			m_direction = dir;
			m_coord.translateY(SPEED);
			flag = true;
		} else if (dir.toString().equals("NE")) {
			m_direction = new Direction("N");
			m_coord.translate(SPEED, -SPEED);
			leftOrientation = false;
			flag = true;
		} else if (dir.toString().equals("NW")) {
			m_direction = new Direction("N");
			leftOrientation = true;
			m_coord.translate(-SPEED, -SPEED);
			flag = true;
		} else if (dir.toString().equals("SW")) {
			m_direction = new Direction("S");
			leftOrientation = true;
			m_coord.translate(-SPEED, SPEED);
			flag = true;
		} else if (dir.toString().equals("SE")) {
			m_direction = new Direction("S");
			leftOrientation = false;
			m_coord.translate(SPEED, SPEED);
			flag = true;
		}
		if (flag)
			m_hitbox.setLocation(m_coord.X(), m_coord.Y());
		return flag;
	}

	public boolean hit(Direction dir) {
		isAttacking = true;
		if (dir.toString().equals("W")) {
			leftOrientation = true;
			movingUp = false;
			movingDown = false;
		} else if (dir.toString().equals("E")) {
			leftOrientation = false;
			movingUp = false;
			movingDown = false;
		} else if (dir.toString().equals("N")) {
			movingUp = true;
			movingDown = false;
		} else if (dir.toString().equals("S")) {
			movingUp = false;
			movingDown = true;
		} else if (dir.toString().equals("NE")) {
			leftOrientation = false;
			movingUp = true;
			movingDown = false;
		} else if (dir.toString().equals("NW")) {
			leftOrientation = true;
			movingUp = true;
			movingDown = false;
		} else if (dir.toString().equals("SW")) {
			leftOrientation = true;
			movingUp = false;
			movingDown = true;
		} else if (dir.toString().equals("SE")) {
			leftOrientation = false;
			movingUp = false;
			movingDown = true;
			;
		} else if (dir.toString().equals("H")) {

		}
		return true;
	}

	public void quitAttackMode() {
		if (isAttacking) {
			isAttacking = false;
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
					m_image_index = 3;
				}
			} else {
				m_imageElapsed = 0;
				if (m_image_index >= 3) {
					m_image_index = 0;
				}
			}
		}
		m_automaton.step(this);

	}

	player.Character getPlayer() {
		return m_model.getPlayer();
	}
	
	Coord getBlockCoord(int x, int y) {
		return m_model.m_underworld.blockCoord(x,y);
	}
}
