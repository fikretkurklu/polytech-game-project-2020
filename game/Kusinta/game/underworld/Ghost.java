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
	boolean leftOrientation;
	int m_image_index;
	boolean isAttacking = false;
	int m_range = 300;
	
	public Ghost(Direction dir, Coord coord, Automaton automaton, Model model) {
		super(automaton);
		m_model = model;
		m_coord = coord;
		loadImage();
		m_direction = dir;
		xHitbox = new int[4];
		yHitbox = new int[4];
		calculateHitbox();
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

	public static final int DISTANCE = 1;

	@Override
	public boolean turn(Direction dir) {
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
	
// Ajouter une possibilité de changer la direction au lieu de creer un nouvel object
	
	@Override
	public boolean pop(Direction dir) {
		Coord playerCoord = m_model.getPlayer().getCoord();
		int d = (int) Math.sqrt((playerCoord.X() - m_coord.X()) * (playerCoord.X() - m_coord.X())
				+ (playerCoord.Y() - m_coord.Y()) * (playerCoord.Y() - m_coord.Y()));
		boolean flag = false;
			if (dir.toString().equals("W")) {
				m_direction = new Direction("W");
				m_coord.translateX(-d/80);
				leftOrientation = true;
				flag = true;
			}else if (dir.toString().equals("E")) {
				m_direction = dir;
				m_coord.translateX(d/80);
				leftOrientation = false;
				flag = true;
			}else if (dir.toString().equals("N")) {
				m_direction = new Direction("N");
				m_coord.translateY(-d/80);
				flag = true;
			}else if (dir.toString().equals("S")) {
				m_direction = new Direction("S");
				m_coord.translateY(d/80);
				flag = true;
			}else if (dir.toString().equals("NE")) {
				m_direction = new Direction("N");
				m_coord.translate(d/80, -d/80);
				flag = true;
			}else if (dir.toString().equals("NW")) {
				m_direction = new Direction("N");
				m_coord.translate(-d/80, -d/80);
				flag = true;
			}else if (dir.toString().equals("SW")) {
				m_direction = new Direction("S");
				m_coord.translate(-d/80, d/80);
				flag = true;
			}else if (dir.toString().equals("SE")) {
				m_direction = new Direction("S");
				m_coord.translate(d/80, d/80);
				flag = true;
			}
		return flag;
	}
/*		case "S":
			if (dir.toString().equals("W")) {
				m_direction = new Direction("W");
				getCoord().translateX(-FOLLOW_DISTANCE);
				leftOrientation = true;
				flag = true;
			}else if (dir.toString().equals("E")) {
				m_direction = new Direction("E");
				getCoord().translateX(FOLLOW_DISTANCE);
				leftOrientation = false;
				flag = true;
			}else if (dir.toString().equals("N")) {
				m_direction = new Direction("N");
				getCoord().translateY(-FOLLOW_DISTANCE);
				flag = true;
			}else if (dir.toString().equals("S")) {
				m_direction = new Direction("S");
				getCoord().translateY(FOLLOW_DISTANCE);
				flag = true;
			}
			return flag;
		case "E":
			if (dir.toString().equals("W"))
				getCoord().translate(DISTANCE, -DISTANCE);
			else if (dir.toString().equals("E"))
				getCoord().translate(DISTANCE, DISTANCE);
			else
				getCoord().translate(DISTANCE, 0);
			return true;
		case "W":
			if (dir.toString().equals("W"))
				getCoord().translate(-DISTANCE, -DISTANCE);
			else if (dir.toString().equals("E"))
				getCoord().translate(-DISTANCE, DISTANCE);
			else
				getCoord().translate(-DISTANCE, 0);
			return true;
		default:
			return false;
		}*/
	

	@Override
	public boolean move(Direction dir) {
		boolean flag = false;
		if (m_direction.toString().equals("W")) {
			m_coord.translate(-DISTANCE, 0);
			flag = true;
		} else if (m_direction.toString().equals("N")) {
			m_coord.translate(0, -DISTANCE);
			flag = true;
		} else if (m_direction.toString().equals("E")) {
			m_coord.translate(DISTANCE, 0);
			flag = true;
		} else if (m_direction.toString().equals("S")) {
			m_coord.translate(0, DISTANCE);
			flag = true;
		}
		return flag;
	}


	@Override
	public boolean closest(Category cat, Direction dir) {
		quitAttackMode();
		int d = 0;
		if (cat.toString().equals("@")) {
			Coord playerCoord = m_model.getPlayer().getCoord();
			if (dir.toString().equals("N")) {
				d = m_coord.Y() - playerCoord.Y();
				return (playerCoord.X() == m_coord.X()) && (0 < d && d <= m_range);
			}else if (dir.toString().equals("S")){
				d = playerCoord.Y() - m_coord.Y();
				return (playerCoord.X() == m_coord.X()) && (0 < d && d <= m_range);
			}else if (dir.toString().equals("E")) {
				d = playerCoord.X() - m_coord.X();
				return (playerCoord.Y() == m_coord.Y()) && (0 < d && d <= m_range);
			}else if (dir.toString().equals("W")) {
				d = m_coord.X() - playerCoord.X();
				return (playerCoord.Y() == m_coord.Y()) && (0 < d && d <= m_range);
			}else if (dir.toString().equals("N")) {
				d = playerCoord.X() - m_coord.X();
				return (playerCoord.Y() == m_coord.Y()) && (0 < d && d <= m_range);
			}else if (dir.toString().equals("NE")) {
				d = (int) Math.sqrt((playerCoord.X() - m_coord.X()) * (playerCoord.X() - m_coord.X())
						+ (playerCoord.Y() - m_coord.Y()) * (playerCoord.Y() - m_coord.Y()));
				return (playerCoord.Y() < m_coord.Y()) && (playerCoord.X() > m_coord.X()) && (d <= m_range);
			}else if (dir.toString().equals("SE")) {
				d = (int) Math.sqrt((playerCoord.X() - m_coord.X()) * (playerCoord.X() - m_coord.X())
						+ (playerCoord.Y() - m_coord.Y()) * (playerCoord.Y() - m_coord.Y()));
				return (playerCoord.Y() > m_coord.Y()) && (playerCoord.X() > m_coord.X())  && (d <= m_range);
			}else if (dir.toString().equals("SW")) {
				d = (int) Math.sqrt((playerCoord.X() - m_coord.X()) * (playerCoord.X() - m_coord.X())
						+ (playerCoord.Y() - m_coord.Y()) * (playerCoord.Y() - m_coord.Y()));
				return (playerCoord.Y() > m_coord.Y()) && (playerCoord.X() < m_coord.X())  && (d <= m_range);
			}else if (dir.toString().equals("NW")) {
				d = (int) Math.sqrt((playerCoord.X() - m_coord.X()) * (playerCoord.X() - m_coord.X())
						+ (playerCoord.Y() - m_coord.Y()) * (playerCoord.Y() - m_coord.Y()));
				return (playerCoord.Y() < m_coord.Y()) && (playerCoord.X() < m_coord.X())  && (d <= m_range);
			}
		}
		return false;
	}


	@Override
	public boolean cell(Direction dir, Category cat) {
		if (cat.toString().equals("O")) {
			return m_model.m_underworld.isBlocked(m_coord.X(), m_coord.Y());
		}else if (cat.toString().equals("@")) {
			boolean res =  m_model.getPlayer().getCoord().isEqual(m_coord);
			if (res) {
				attackMode();
			}
			return res;
		}
		return false;
	}
	
	public void attackMode() {
		isAttacking = true;
	//	m_range = m_range * 2;
	}
	
	public void quitAttackMode() {
		if (isAttacking) {
			isAttacking = false;
			m_range = m_range / 2;
		}
	}
	
	public void paint(Graphics g) {
		if (m_images != null) {
			calculateHitbox();
			if (m_image_index >= UnderworldParam.ghostImage.length) {
				m_image_index = 0;
			}
			if (leftOrientation)
				g.drawImage(m_images[m_image_index], m_coord.X(), m_coord.Y(), -SIZE, SIZE, null);
			else
				g.drawImage(m_images[m_image_index], m_coord.X() - SIZE , m_coord.Y(), SIZE, SIZE, null);
			g.setColor(Color.blue);
			g.drawPolygon(xHitbox, yHitbox, 4);
		}
	}
	
	public void tick(long elapsed) {
		m_imageElapsed += elapsed;
		if (m_imageElapsed > 200) {
			m_imageElapsed = 0;
			m_image_index++;
		}
	}
	

}
