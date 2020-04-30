package opponent;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import automaton.Automaton;
import automaton.Direction;
import environnement.Element;
import game.Model;
import player.Character;

public abstract class Opponent extends Character {
	
	public static final int SIZE = (int) (1.5 * Element.SIZE);

	
	int m_width, m_height;
	
	Rectangle hotBox;

	public Opponent(Automaton automaton, int x, int y, Direction dir, Model model, int maxLife, int life,
			int attackSpeed, int resistance, int strength) throws IOException {
		super(automaton, x, y, dir, model, maxLife, life, attackSpeed, resistance, strength);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void tick(long elapsed) {
		m_automaton.step(this);
		
	}
	

	@Override
	public void setPressed(int keyChar, boolean b) {
		// TODO Auto-generated method stub	
	}
	
	public Image loadImage(String path) throws Exception {
		File imageFile = new File(path);
		Image image;
		if (imageFile.exists()) {
			image = ImageIO.read(imageFile);
			image = image.getScaledInstance(SIZE, SIZE, 0);
			return image;
		} else {
			throw new Exception("Error while loading image: path = " + path);
		}
	}

}
