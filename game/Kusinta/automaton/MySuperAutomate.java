package automaton;



import java.util.HashMap;

import automaton.playerActions.*;
import automaton.playerCond.*;

public class MySuperAutomate {

	public static void main(String[] args) {
		State init = new State("Init");
		State etat2 = new State("etat2");
		State etat3 = new State("etat3");
		State etat4 = new State("etat4");
		State etat5 = new State("etat5");
		
		CondTrue cond1 = new CondTrue();
		CondKey cond2 = new CondKey(80);
		CondMyDir cond3 = new CondMyDir(null);
		CondClosest cond4 = new CondClosest(null);
		CondGotPower cond5 = new CondGotPower();
		
		ActionJump action_jump = new ActionJump();
		ActionMove action_move = new ActionMove(null);
		ActionPop action_pop = new ActionPop(null);
		ActionPower action_power = new ActionPower();
		ActionTurn action_turn = new ActionTurn(null);
		ActionPick action_pick = new ActionPick();
		
		Transition[] trans1 = {new Transition(cond1, init, etat2, action_jump)};
		Transition[] trans2 = {new Transition(cond1, etat2, etat5, action_move)};
		Transition trans_1 = new Transition(cond2, etat3, etat2, action_pop);
		Transition trans_2 = new Transition(cond3, etat3, etat4, action_power);
		Transition trans_3 = new Transition(cond4, etat3, etat5, action_turn);
		Transition[] trans3 = {trans_1, trans_2, trans_3 };
		Transition[] trans4 = {new Transition(cond5, etat5, etat2, action_pick)};
		
		HashMap<State,Transition[]> mapTransitions = new HashMap<State, Transition[]>();
		mapTransitions.put(init, trans1);
		mapTransitions.put(etat2, trans2);
		mapTransitions.put(etat3, trans3);
		mapTransitions.put(etat5, trans4);
		
		Automaton aut = new Automaton(init, mapTransitions);
		Block b = new Block(aut);
		
		for (int i = 0; i < 6; i++) {
			aut.step(b);
		}

	}

}
