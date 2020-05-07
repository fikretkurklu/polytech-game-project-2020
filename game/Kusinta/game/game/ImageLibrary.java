package game;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import automata.ast.AST;
import automata.parser.AutomataParser;
import automaton.Automaton;
import automaton.Entity.Action;;

public class ImageLibrary {

	public static String PATH = "resources/ani/";
	String Avatars[] = { "arrow", "demon", "jin", "magicProjectile", "player"}; // Nom des fichiers

	HashMap<String, Image[]> sprites;
	HashMap<String, HashMap<Action, int[]>> actionsIndex;

	public ImageLibrary() {
		sprites = new HashMap<String, Image[]>();
		actionsIndex = new HashMap<String, HashMap<Action, int[]>>();
		try {
			for (String avatar : Avatars) {
				File file = new File(PATH + avatar + ".ani");
				AnimationParser(file, avatar);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void AnimationParser(File file, String avatar) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader(file));
		String fichierAni = f.readLine().split(" = ")[1];
		String[] dim = f.readLine().split(";");
		Image[] img = loadImageSprite(fichierAni, Integer.valueOf(dim[0]), Integer.valueOf(dim[1]),
				Integer.valueOf(dim[2]), Integer.valueOf(dim[3]));
		sprites.put(avatar, img);
		String line = f.readLine();
		String[] splitedLines;
		String[] splitedIndexAnimations;
		int[] indexAnimations;
		HashMap<Action, int[]> hmActions = new HashMap<Action, int[]>();
		while (line != null) {
			splitedLines = line.split(" = ");
			switch (splitedLines[0]) {
			case "MOVE":
				splitedIndexAnimations = splitedLines[1].split(";");
				indexAnimations = new int[splitedIndexAnimations.length];
				for (int i = 0; i < splitedIndexAnimations.length; i++) {
					indexAnimations[i] = Integer.valueOf(splitedIndexAnimations[i]);
				}
				hmActions.put(Action.MOVE, indexAnimations);
				break;
			case "JUMP":
				splitedIndexAnimations = splitedLines[1].split(";");
				indexAnimations = new int[splitedIndexAnimations.length];
				for (int i = 0; i < splitedIndexAnimations.length; i++) {
					indexAnimations[i] = Integer.valueOf(splitedIndexAnimations[i]);
				}
				hmActions.put(Action.JUMP, indexAnimations);
				break;
			case "SHOT":
				splitedIndexAnimations = splitedLines[1].split(";");
				indexAnimations = new int[splitedIndexAnimations.length];
				for (int i = 0; i < splitedIndexAnimations.length; i++) {
					indexAnimations[i] = Integer.valueOf(splitedIndexAnimations[i]);
				}
				hmActions.put(Action.SHOT, indexAnimations);
				break;
			case "DEATH":
				splitedIndexAnimations = splitedLines[1].split(";");
				indexAnimations = new int[splitedIndexAnimations.length];
				for (int i = 0; i < splitedIndexAnimations.length; i++) {
					indexAnimations[i] = Integer.valueOf(splitedIndexAnimations[i]);
				}
				hmActions.put(Action.DEATH, indexAnimations);
				break;
			case "SHOTMOVE":
				splitedIndexAnimations = splitedLines[1].split(";");
				indexAnimations = new int[splitedIndexAnimations.length];
				for (int i = 0; i < splitedIndexAnimations.length; i++) {
					indexAnimations[i] = Integer.valueOf(splitedIndexAnimations[i]);
				}
				hmActions.put(Action.SHOTMOVE, indexAnimations);
				break;
			case "DEFAULT":
				splitedIndexAnimations = splitedLines[1].split(";");
				indexAnimations = new int[splitedIndexAnimations.length];
				for (int i = 0; i < splitedIndexAnimations.length; i++) {
					indexAnimations[i] = Integer.valueOf(splitedIndexAnimations[i]);
				}
				hmActions.put(Action.DEFAULT, indexAnimations);
				break;
			}
			line = f.readLine();
		}
		actionsIndex.put(avatar, hmActions);
		f.close();
	}

	public static Image[] loadImageSprite(String filename, int nrows, int ncols, int finalWidth, int finalHeight)
			throws IOException {
		File imageFile = new File(filename);
		if (imageFile.exists()) {
			BufferedImage image = ImageIO.read(imageFile);
			int width = image.getWidth(null) / ncols;
			int height = image.getHeight(null) / nrows;
			Image[] images = new Image[nrows * ncols];
			for (int i = 0; i < nrows; i++) {
				for (int j = 0; j < ncols; j++) {
					int x = j * width;
					int y = i * height;
					images[(i * ncols) + j] = image.getSubimage(x, y, width, height);
					images[(i * ncols) + j] = images[(i * ncols) + j].getScaledInstance(finalWidth, finalHeight, 0);
				}
			}
			return images;
		}
		return null;
	}

}
