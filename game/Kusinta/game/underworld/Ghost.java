package underworld;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
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
import player.Character;

public class Ghost extends Entity {

	public static final int SIZE = (int) (1.5 * Element.SIZE);

	Coord m_coord;
	Model m_model;
	Direction m_direction;

	Image m_images[];
	long m_imageElapsed;
	int m_width, m_height = SIZE;

	int xHitbox[];
	int yHitbox[];
	boolean leftOrientation, move;
	int m_image_index;
	boolean isAttacking = false, isFollowing = false;
	int m_range = 300;
	Character player;

	public Ghost(Direction dir, Coord coord, Automaton automaton, Model model) {
		super(automaton);
		m_model = model;
		m_coord = coord;
		loadImage();
		m_direction = dir;
		xHitbox = new int[4];
		yHitbox = new int[4];
		calculateHitbox();
		player = model.getPlayer();
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

	private void calculateHitbox() {

		int x = getCoord().X();
		int y = getCoord().Y();

		xHitbox[0] = x - m_width + (m_width / 7);
		xHitbox[1] = x - (m_width / 7);
		xHitbox[2] = x - (m_width / 7);
		xHitbox[3] = x - m_width + (m_width / 7);

		yHitbox[0] = y + (m_width / 7);
		yHitbox[1] = y + (m_width / 7);
		yHitbox[2] = y + m_height - (m_width / 7);
		yHitbox[3] = y + m_height - (m_width / 7);

	}

	private Coord getCoord() {
		return m_coord;
	}

	public static final int SPEED = 1;

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
			m_coord.translate(-SPEED, 0);
			leftOrientation = true;
			flag = true;
		} else if (m_direction.toString().equals("N")) {
			m_coord.translate(0, -SPEED);
			flag = true;
		} else if (m_direction.toString().equals("E")) {
			m_coord.translate(SPEED, 0);
			leftOrientation = false;
			flag = true;
		} else if (m_direction.toString().equals("S")) {
			m_coord.translate(0, SPEED);
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean closest(Category cat, Direction dir) {
		quitAttackMode();
		int d = 0;
		String catString = cat.toString();
		if (((PlayerSoul) m_model.getPlayer()).hidden) {
			return false;
		}
		if (catString.equals("A") || catString.equals("C")) {
			Coord playerCoord = null;
			if (catString.equals("A"))
				playerCoord = m_model.getPlayer().getCoord();
			else if (((PlayerSoul) m_model.getPlayer()).lure != null
					&& !((PlayerSoul) m_model.getPlayer()).lure.disapered)
				playerCoord = ((PlayerSoul) m_model.getPlayer()).lure.getCoord();
			else
				return false;
			if (dir.toString().equals("N")) {
				d = m_coord.Y() - playerCoord.Y();
				return (playerCoord.X() == m_coord.X()) && (0 < d && d <= m_range);
			} else if (dir.toString().equals("S")) {
				d = playerCoord.Y() - m_coord.Y();
				return (playerCoord.X() == m_coord.X()) && (0 < d && d <= m_range);
			} else if (dir.toString().equals("E")) {
				d = playerCoord.X() - m_coord.X();
				return (playerCoord.Y() == m_coord.Y()) && (0 < d && d <= m_range);
			} else if (dir.toString().equals("W")) {
				d = m_coord.X() - playerCoord.X();
				return (playerCoord.Y() == m_coord.Y()) && (0 < d && d <= m_range);
			} else if (dir.toString().equals("N")) {
				d = playerCoord.X() - m_coord.X();
				return (playerCoord.Y() == m_coord.Y()) && (0 < d && d <= m_range);
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
		if (cat.toString().equals("O")) {
			return m_model.m_underworld.isBlocked(m_coord.X(), m_coord.Y());
		} else if (cat.toString().equals("A")) {
			if (((PlayerSoul) m_model.getPlayer()).hidden) {
				return false;
			}
			return m_model.getPlayer().getCoord().isEqual(m_coord);
		} else if (cat.toString().equals("C")) {
			if (((PlayerSoul) m_model.getPlayer()).lure != null && !((PlayerSoul) m_model.getPlayer()).lure.disapered) {
				 return ((PlayerSoul) m_model.getPlayer()).lure.getCoord().isEqual(m_coord);
			}
			
			return false;
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
		return flag;
	}

	public boolean hit(Direction dir) {
		isAttacking = true;
		// m_range = m_range*2;
		return true;
	}

	public void quitAttackMode() {
		if (isAttacking) {
			isAttacking = false;
		}
	}

	public void paint(Graphics g) {
		if (m_images != null) {
			calculateHitbox();
			if (leftOrientation)
				g.drawImage(m_images[m_image_index], m_coord.X(), m_coord.Y(), -SIZE, SIZE, null);
			else
				g.drawImage(m_images[m_image_index], m_coord.X() - SIZE, m_coord.Y(), SIZE, SIZE, null);
			g.setColor(Color.blue);
			g.drawPolygon(xHitbox, yHitbox, 4);
		}
	}

	public void tick(long elapsed) {
		m_imageElapsed += elapsed;
		if (m_imageElapsed > 200) {
			m_imageElapsed = 0;
			m_image_index++;
			if (m_image_index >= 3) {
				m_image_index = 0;
			}
		}
		m_automaton.step(this);

	}

}
