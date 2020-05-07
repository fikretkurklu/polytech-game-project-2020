package entityFactory;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import automaton.Automaton;
import automaton.AutomatonLibrary;
import automaton.Direction;
import automaton.Entity;
import game.Coord;
import game.Model;
import opponent.BossKey;
import opponent.Coin;
import opponent.FlyingOpponent;
import opponent.NormalKey;
import opponent.WalkingOpponent;
import player.Player;
import playerActions.Action;
import projectile.Arrow;
import projectile.MagicProjectile;
import underworld.Fragment;
import underworld.Ghost;
import underworld.Lure;
import underworld.PlayerSoul;

public class Factory {

	AutomatonLibrary m_AL;
//	ImageLibrary m_IL;

	enum Type {
		Player, PlayerSoul, Lure, NormalKey, BossKey, Coin, Fragment, WalkingOpponent, FlyingOpponent, Boss, Arrow,
		MagicProjectile, Meteor, Ghost
	};

	HashMap<Type, Automaton> automatons;
	HashMap<Type, BufferedImage> images;
	HashMap<String, HashMap<Action, int[]>> actions;

	public Factory() throws Exception {
		m_AL = new AutomatonLibrary();
		fillAutomaton();
		fillImages();
		fillActions();
	}

	private void fillActions() { }

	private void fillImages() {	}

	private void fillAutomaton() throws Exception {
		automatons.put(Type.Player, m_AL.getAutomaton("Player_donjon"));
		automatons.put(Type.PlayerSoul, m_AL.getAutomaton("PlayerSoul"));
		automatons.put(Type.NormalKey, m_AL.getAutomaton("KeyDrop"));
		automatons.put(Type.BossKey, m_AL.getAutomaton("keyDrop"));
		automatons.put(Type.WalkingOpponent, m_AL.getAutomaton("WalkingOpponents"));
		automatons.put(Type.FlyingOpponent, m_AL.getAutomaton("flyingOpponentAutomaton"));
		automatons.put(Type.Arrow, m_AL.getAutomaton("arrowAutomaton"));
		automatons.put(Type.MagicProjectile, m_AL.getAutomaton("MagicProj"));
		automatons.put(Type.Ghost, m_AL.getAutomaton("Ghost"));
		automatons.put(Type.Coin, m_AL.getAutomaton("CoinDrop"));
		automatons.put(Type.Lure, m_AL.getAutomaton("Lure"));
		automatons.put(Type.Boss, m_AL.getAutomaton("Boss"));
		automatons.put(Type.Meteor, m_AL.getAutomaton("Meteor"));
		automatons.put(Type.Fragment, m_AL.getAutomaton("Fragment"));
	}

	public Entity newEntity(Type type, Direction dir, Coord coord, Model model, float angle, player.Character shooter)
			throws Exception {
		switch (type) {
		case Player:
			return new Player(automatons.get(Type.Player), coord, dir, model);
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
			return new Arrow(automatons.get(Type.Arrow), coord, angle, shooter, dir);
		case MagicProjectile:
			return new MagicProjectile(automatons.get(Type.MagicProjectile), coord, angle, shooter, dir);
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
//		images.put(type, "temporaire"/*m_IL.getBImage(string)*/);
	}

}
