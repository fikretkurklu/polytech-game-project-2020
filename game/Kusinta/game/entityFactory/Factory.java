package entityFactory;

import java.awt.Image;
import java.util.HashMap;

import automaton.Automaton;
import automaton.AutomatonLibrary;
import automaton.Direction;
import automaton.Entity;
import game.Coord;
import game.ImageLibrary;
import game.Model;
import opponent.BossKey;
import opponent.Coin;
import opponent.FlyingOpponent;
import opponent.NormalKey;
import opponent.WalkingOpponent;
import player.Player;
import projectile.Arrow;
import projectile.MagicProjectile;
import underworld.Fragment;
import underworld.Ghost;
import underworld.Lure;
import underworld.PlayerSoul;
import automaton.Entity.Action;

public class Factory {

	AutomatonLibrary m_AL;
	ImageLibrary m_IL;

	enum Type {
		Player, PlayerSoul, Lure, NormalKey, BossKey, Coin, Fragment, WalkingOpponent, FlyingOpponent, Boss, Arrow,
		MagicProjectile, Meteor, Ghost
	};

	String Avatars[] = { "arrow", "demon", "jin", "magicProjectile", "player", "playerSoul", "ghost", "lure", "boss",
			"bossKey", "coin", "normalKey" }; // Nom des fichiers

	HashMap<Type, Automaton> automatons;
	HashMap<Type, Image[]> images;
	HashMap<Type, HashMap<Action, int[]>> actions;

	public Factory() throws Exception {
		m_AL = new AutomatonLibrary();
		fillAutomaton();
		fillImages();
		fillActions();
	}

	private void fillActions() {
		actions.put(Type.Player, m_IL.getActions(Avatars[4]));
		actions.put(Type.PlayerSoul, m_IL.getActions(Avatars[5]));
		actions.put(Type.NormalKey, m_IL.getActions(Avatars[11]));
		actions.put(Type.BossKey, m_IL.getActions(Avatars[9]));
		actions.put(Type.WalkingOpponent, m_IL.getActions(Avatars[1]));
		actions.put(Type.FlyingOpponent, m_IL.getActions(Avatars[2]));
		actions.put(Type.Arrow, m_IL.getActions(Avatars[0]));
		actions.put(Type.MagicProjectile, m_IL.getActions(Avatars[3]));
		actions.put(Type.Ghost, m_IL.getActions(Avatars[6]));
		actions.put(Type.Coin, m_IL.getActions(Avatars[10]));
		actions.put(Type.Lure, m_IL.getActions(Avatars[7]));
		actions.put(Type.Boss, m_IL.getActions(Avatars[8]));
//		actions.put(Type.Meteor, m_IL.getActions("Meteor"));
//		actions.put(Type.Fragment, m_IL.getActions("Fragment"));
	}

	private void fillImages() {
		images.put(Type.Player, m_IL.getImages(Avatars[4]));
		images.put(Type.PlayerSoul, m_IL.getImages(Avatars[5]));
		images.put(Type.NormalKey, m_IL.getImages(Avatars[11]));
		images.put(Type.WalkingOpponent, m_IL.getImages(Avatars[1]));
		images.put(Type.FlyingOpponent, m_IL.getImages(Avatars[2]));
		images.put(Type.Arrow, m_IL.getImages(Avatars[0]));
		images.put(Type.MagicProjectile, m_IL.getImages(Avatars[3]));
		images.put(Type.Ghost, m_IL.getImages(Avatars[6]));
		images.put(Type.Coin, m_IL.getImages(Avatars[10]));
		images.put(Type.Lure, m_IL.getImages(Avatars[7]));
		images.put(Type.Boss, m_IL.getImages(Avatars[8]));
//		images.put(Type.Meteor, m_IL.getImages("Meteor"));
//		images.put(Type.Fragment, m_IL.getImages("Fragment"));
	}

	private void fillAutomaton() throws Exception {
		automatons.put(Type.Player, m_AL.getAutomaton(Avatars[4]));
		automatons.put(Type.PlayerSoul, m_AL.getAutomaton(Avatars[5]));
		automatons.put(Type.NormalKey, m_AL.getAutomaton(Avatars[11]));
		automatons.put(Type.BossKey, m_AL.getAutomaton(Avatars[9]));
		automatons.put(Type.WalkingOpponent, m_AL.getAutomaton(Avatars[1]));
		automatons.put(Type.FlyingOpponent, m_AL.getAutomaton(Avatars[2]));
		automatons.put(Type.Arrow, m_AL.getAutomaton(Avatars[0]));
		automatons.put(Type.MagicProjectile, m_AL.getAutomaton(Avatars[3]));
		automatons.put(Type.Ghost, m_AL.getAutomaton(Avatars[6]));
		automatons.put(Type.Coin, m_AL.getAutomaton(Avatars[10]));
		automatons.put(Type.Lure, m_AL.getAutomaton(Avatars[7]));
		automatons.put(Type.Boss, m_AL.getAutomaton(Avatars[8]));
		automatons.put(Type.Meteor, m_AL.getAutomaton("Meteor"));
		automatons.put(Type.Fragment, m_AL.getAutomaton("Fragment"));
	}

	public Entity newEntity(Type type, Direction dir, Coord coord, Model model, float angle, player.Character shooter)
			throws Exception {
		switch (type) {
		case Player:
			return new Player(automatons.get(Type.Player), coord, dir, model, images.get(Type.Player),
					actions.get(Type.Player));
		case PlayerSoul:
			return new PlayerSoul(automatons.get(Type.PlayerSoul), coord, dir, null, model);
		case NormalKey:
			return new NormalKey(automatons.get(Type.NormalKey), coord.X(), coord.Y(), model);
		case BossKey:
			return new BossKey(automatons.get(Type.BossKey), coord.X(), coord.Y(), model);
		case WalkingOpponent:
			return new WalkingOpponent(automatons.get(Type.WalkingOpponent), coord, dir, model);
		case FlyingOpponent:
			return new FlyingOpponent(automatons.get(Type.FlyingOpponent), coord, dir, model);
		case Arrow:
			return new Arrow(automatons.get(Type.Arrow), coord, angle, shooter, dir, images.get(Type.Arrow),
					actions.get(Type.Arrow));
		case MagicProjectile:
			return new MagicProjectile(automatons.get(Type.MagicProjectile), coord, angle, shooter, dir,
					images.get(Type.MagicProjectile), actions.get(Type.MagicProjectile));
		case Ghost:
			return new Ghost(dir, coord, automatons.get(Type.Ghost), model, null);
		case Coin:
			return new Coin(automatons.get(Type.Coin), coord.X(), coord.Y(), 10, model);
		case Lure:
			return new Lure(automatons.get(Type.Lure), coord, shooter, dir, null, model);
		case Boss:
			System.out.println("temporaire");
			return null;
		case Meteor:
			System.out.println("temporaire");
			return null;
		case Fragment:
			return new Fragment(automatons.get(Type.Fragment), coord, model, null);
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

}
