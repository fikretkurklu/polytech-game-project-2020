package entityFactory;

import java.awt.Image;
import java.util.HashMap;

import automaton.Automaton;
import automaton.AutomatonLibrary;
import automaton.Direction;
import automaton.Entity;
import game.Coord;
import game.Model;
import opponent.Boss;
import opponent.BossKey;
import opponent.Coin;
import opponent.Jin;
import opponent.Medusa;
import opponent.MiniDragon;
import opponent.NormalKey;
import opponent.Demon;
import player.Player;
import projectile.Arrow;
import projectile.MagicProjectile;
import projectile.Meteor;
import underworld.Cloud;
import underworld.Fragment;
import underworld.Gate;
import underworld.Ghost;
import underworld.Lure;
import underworld.PlayerSoul;
import automaton.Entity.Action;

public class Factory {

	public AutomatonLibrary m_AL;
	ImageLibrary m_IL;

	public static enum Type {
		Arrow, Boss, BossKey, Cloud, Coin, Demon, Fragment, FireAttack, Gate, Ghost, Jin, Lure, MagicProjectile, Medusa,
		NormalKey, Player, PlayerSoul, SmallDragon
	};

	public static String Avatars[] = { "Arrow", "Demon", "Jin", "MagicProjectile", "Player_Donjon", "PlayerSoul",
			"Ghost", "Lure", "KeyBoss", "Coin", "KeyNormal", "Fragment", "Gate", "Cloud", "Medusa", "Boss",
			"FireAttack", "SmallDragon" }; // Nom des
	// fichiers

	HashMap<Type, Automaton> automatons;
	HashMap<Type, Image[]> images;
	HashMap<Type, HashMap<Action, int[]>> actions;

	public Factory() {
		m_AL = new AutomatonLibrary();
		m_IL = new ImageLibrary();
		automatons = new HashMap<Factory.Type, Automaton>();
		images = new HashMap<Factory.Type, Image[]>();
		actions = new HashMap<Factory.Type, HashMap<Action, int[]>>();
		fillAutomaton();
		fillImages();
		fillActions();
	}

	private void fillActions() {
		actions.put(Type.Arrow, m_IL.getActions(Avatars[0]));
		actions.put(Type.Demon, m_IL.getActions(Avatars[1]));
		actions.put(Type.Jin, m_IL.getActions(Avatars[2]));
		actions.put(Type.MagicProjectile, m_IL.getActions(Avatars[3]));
		actions.put(Type.Player, m_IL.getActions(Avatars[4]));
		actions.put(Type.PlayerSoul, m_IL.getActions(Avatars[5]));
		actions.put(Type.Ghost, m_IL.getActions(Avatars[6]));
		actions.put(Type.Lure, m_IL.getActions(Avatars[7]));
		actions.put(Type.BossKey, m_IL.getActions(Avatars[8]));
		actions.put(Type.Coin, m_IL.getActions(Avatars[9]));
		actions.put(Type.NormalKey, m_IL.getActions(Avatars[10]));
		actions.put(Type.Fragment, m_IL.getActions(Avatars[11]));
		actions.put(Type.Gate, m_IL.getActions(Avatars[12]));
		actions.put(Type.Cloud, m_IL.getActions(Avatars[13]));
		actions.put(Type.Medusa, m_IL.getActions(Avatars[14]));
		actions.put(Type.Boss, m_IL.getActions(Avatars[15]));
		actions.put(Type.FireAttack, m_IL.getActions(Avatars[16]));
		actions.put(Type.SmallDragon, m_IL.getActions(Avatars[17]));
	}

	private void fillImages() {
		images.put(Type.Arrow, m_IL.getImages(Avatars[0]));
		images.put(Type.Demon, m_IL.getImages(Avatars[1]));
		images.put(Type.Jin, m_IL.getImages(Avatars[2]));
		images.put(Type.MagicProjectile, m_IL.getImages(Avatars[3]));
		images.put(Type.Player, m_IL.getImages(Avatars[4]));
		images.put(Type.PlayerSoul, m_IL.getImages(Avatars[5]));
		images.put(Type.Ghost, m_IL.getImages(Avatars[6]));
		images.put(Type.Lure, m_IL.getImages(Avatars[7]));
		images.put(Type.Coin, m_IL.getImages(Avatars[9]));
		images.put(Type.NormalKey, m_IL.getImages(Avatars[10]));
		images.put(Type.Fragment, m_IL.getImages(Avatars[11]));
		images.put(Type.Gate, m_IL.getImages(Avatars[12]));
		images.put(Type.Cloud, m_IL.getImages(Avatars[13]));
		images.put(Type.Medusa, m_IL.getImages(Avatars[14]));
		images.put(Type.Boss, m_IL.getImages(Avatars[15]));
		images.put(Type.FireAttack, m_IL.getImages(Avatars[16]));
		images.put(Type.SmallDragon, m_IL.getImages(Avatars[17]));
	}

	private void fillAutomaton() {
		automatons.put(Type.Arrow, m_AL.getAutomaton(Avatars[0]));
		automatons.put(Type.Demon, m_AL.getAutomaton(Avatars[1]));
		automatons.put(Type.Jin, m_AL.getAutomaton(Avatars[2]));
		automatons.put(Type.MagicProjectile, m_AL.getAutomaton(Avatars[3]));
		automatons.put(Type.Player, m_AL.getAutomaton(Avatars[4]));
		automatons.put(Type.PlayerSoul, m_AL.getAutomaton(Avatars[5]));
		automatons.put(Type.Ghost, m_AL.getAutomaton(Avatars[6]));
		automatons.put(Type.Lure, m_AL.getAutomaton(Avatars[7]));
		automatons.put(Type.BossKey, m_AL.getAutomaton(Avatars[8]));
		automatons.put(Type.Coin, m_AL.getAutomaton(Avatars[9]));
		automatons.put(Type.NormalKey, m_AL.getAutomaton(Avatars[10]));
		automatons.put(Type.Fragment, m_AL.getAutomaton(Avatars[11]));
		automatons.put(Type.Gate, m_AL.getAutomaton(Avatars[12]));
		automatons.put(Type.Cloud, m_AL.getAutomaton(Avatars[13]));
		automatons.put(Type.Medusa, m_AL.getAutomaton(Avatars[14]));
		automatons.put(Type.Boss, m_AL.getAutomaton(Avatars[15]));
		automatons.put(Type.FireAttack, m_AL.getAutomaton(Avatars[16]));
		automatons.put(Type.SmallDragon, m_AL.getAutomaton(Avatars[17]));
	}

	public Entity newEntity(Type type, Direction dir, Coord coord, Model model, double angle,
			player.Character shooter) {
		try {
			switch (type) {
			case Player:
				return new Player(automatons.get(Type.Player), coord, dir, model, images.get(Type.Player),
						actions.get(Type.Player));
			case PlayerSoul:
				return new PlayerSoul(automatons.get(Type.PlayerSoul), coord, dir, images.get(Type.PlayerSoul), model,
						actions.get(Type.PlayerSoul));
			case NormalKey:
				return new NormalKey(automatons.get(Type.NormalKey), coord, model, images.get(Type.NormalKey),
						actions.get(Type.NormalKey));
			case BossKey:
				return new BossKey(automatons.get(Type.BossKey), coord, model, images.get(Type.BossKey),
						actions.get(Type.BossKey));
			case Demon:
				return new Demon(automatons.get(Type.Demon), coord, dir, model, images.get(Type.Demon),
						actions.get(Type.Demon));
			case Jin:
				return new Jin(automatons.get(Type.Jin), coord, dir, model, images.get(Type.Jin),
						actions.get(Type.Jin));
			case Arrow:
				return new Arrow(automatons.get(Type.Arrow), coord, angle, shooter, dir, images.get(Type.Arrow),
						actions.get(Type.Arrow));
			case MagicProjectile:
				return new MagicProjectile(automatons.get(Type.MagicProjectile), coord, angle, shooter, dir,
						images.get(Type.MagicProjectile), actions.get(Type.MagicProjectile));
			case Ghost:
				return new Ghost(dir, coord, automatons.get(Type.Ghost), model, images.get(Type.Ghost),
						actions.get(Type.Ghost));
			case Coin:
				return new Coin(automatons.get(Type.Coin), coord, model, images.get(Type.Coin), actions.get(Type.Coin));
			case Lure:
				return new Lure(automatons.get(Type.Lure), coord, shooter, dir, images.get(Type.Lure), model,
						actions.get(Type.Lure));
			case Fragment:
				return new Fragment(automatons.get(Type.Fragment), coord, model, images.get(Type.Fragment),
						actions.get(Type.Fragment));
			case Gate:
				return new Gate(automatons.get(Type.Gate), coord, model, images.get(Type.Gate), actions.get(Type.Gate));
			case Cloud:
				return new Cloud(automatons.get(Type.Cloud), coord, model, images.get(Type.Cloud),
						actions.get(Type.Cloud));
			case Medusa:
				return new Medusa(automatons.get(Type.Medusa), coord, dir, model, images.get(Type.Medusa),
						actions.get(Type.Medusa));
			case Boss:
				return new Boss(automatons.get(Type.Boss), coord, dir, model, images.get(Type.Boss),
						actions.get(Type.Boss));
			case FireAttack:
				return new Meteor(automatons.get(Type.FireAttack), coord, angle, shooter, dir,
						images.get(Type.FireAttack), actions.get(Type.FireAttack));
			case SmallDragon:
				return new MiniDragon(automatons.get(Type.SmallDragon), coord, dir, model, images.get(Type.SmallDragon),
						actions.get(Type.SmallDragon));
			default:
				break;
			}
		} catch (Exception e) {
			System.out.println("Error while creating : " + type.toString() + e.getMessage());
		}
		return null;

	}

	public void changeAutomaton(Type type, String string) throws Exception {
		automatons.put(type, m_AL.getAutomaton(string));
	}

	public void changeAnimation(Type type, String string) {
		images.put(type, m_IL.getImages(string));
		actions.put(type, m_IL.getActions(string));
	}
	
	public Image[] getImage(Type t) {
		return images.get(t);
	}

}
