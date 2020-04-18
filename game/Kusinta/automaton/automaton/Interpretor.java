package automaton;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import playerActions.*;
import playerCond.*;
import game.*;

import automata.ast.AST;
import automata.ast.Action;
import automata.ast.Behaviour;
import automata.ast.BinaryOp;
import automata.ast.Category;
import automata.ast.Condition;
import automata.ast.Direction;
import automata.ast.FunCall;
import automata.ast.IVisitor;
import automata.ast.Key;
import automata.ast.Mode;
import automata.ast.Transition;
import automata.ast.UnaryOp;
import automata.ast.Underscore;
import automata.ast.Value;

public class Interpretor implements IVisitor {

	LinkedList<Automaton> bots;
	State initialState;
	HashMap<State, Transition[]> mapTransitions;
	
	public Interpretor() {
		bots = new LinkedList();
		mapTransitions = new HashMap();
	}

	@Override
	public Object visit(Category cat) {
		System.out.println(cat.toString());
		return null;
	}

	@Override
	public Object visit(Direction dir) {
		return new game.Direction(dir.toString());
	}

	@Override
	public Object visit(Key key) {
		System.out.println(key.toString());
		return null;
	}

	@Override
	public Object visit(Value v) {
		return v.value;
	}

	@Override
	public Object visit(Underscore u) {
		System.out.println(u.toString());
		return null;
	}

	@Override
	public void enter(FunCall funcall) {
	}

	@Override
	public Object exit(FunCall funcall, List<Object> parameters) {
		String name = funcall.name;
		ListIterator<Object> parameter = parameters.listIterator();
		switch(name) {
		
		// ACTIONS
		
		case "Hit":
			return new ActionHit();
		case "Egg" :
			return new ActionEgg();
		case "Pop" :
			if (parameter.hasNext())
				return new ActionPop((game.Direction) parameter.next());
			return null;
		case "Wiz" :
		case "Jump" :
			return new ActionJump();
		case "Move" :
			if (parameter.hasNext())
				return new ActionMove((game.Direction) parameter.next());
			return null;
		case "Pick" :
			return new ActionPick();
		case "Power" :
			return new ActionPower();
		case "Turn" :
			if (parameter.hasNext())
				return new ActionTurn((game.Direction) parameter.next());
			return null;
		case "Wizz" :
			return new ActionWizz();
			
		// CONDITIONS
			
		case "True":
			return new CondTrue();
		case "Cell" :
			if (parameter.hasNext())
				return new CondCell((game.Entity) parameter.next());
			return null;
		case "Closest" :
			if (parameter.hasNext())
				return new CondClosest((game.Entity) parameter.next());
			return null;
		case "GotPower" :
			return new CondGotPower();
		case "GotStuff" :
			return new CondGotStuff();
		case "Key" :
			if (parameter.hasNext())
				return new CondKey((int) parameter.next());
			return null;
		case "MyDir" :
			if (parameter.hasNext())
				return new CondMyDir((game.Direction) parameter.next());
			return null;
			
			
		default :
			return null;
		}
	}

	@Override
	public Object visit(BinaryOp operator, Object left, Object right) {
		System.out.println(left.toString() + " " + operator.toString() + " " + right.toString());
		return null;
	}

	@Override
	public Object visit(UnaryOp operator, Object expression) {
		System.out.println(operator.toString() + " " + expression.toString());
		return null;
	}

	@Override
	public Object visit(automata.ast.State state) {
		return new State(state.name);
	}

	@Override
	public void enter(Mode mode) {
	}

	@Override
	public Object exit(Mode mode, Object source_state, Object behaviour) {
		ListIterator<Transition> transition = ((Behaviour)behaviour).transitions.listIterator();
		Transition tr;
		Action action;
		Condition condition;
		State target;
		while (transition.hasNext()) {
			tr = transition.next();
			action = (Action) tr.action.accept(this);
			//Action action = transition.
		}
		return null;
	}

	@Override
	public Object visit(Behaviour behaviour, List<Object> transitions) {
		return transitions;
	}

	@Override
	public void enter(Condition condition) {
	}

	@Override
	public Object exit(Condition condition, Object expression) {
		return expression;
	}
		

	@Override
	public void enter(Action acton) {
	}

	@Override
	public Object exit(Action action, List<Object> funcalls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Transition transition, Object condition, Object action, Object target_state) {
		return new automaton.Transition((ICondition) condition, (automaton.State) target_state, (IAction) action);
	}

//	@Override
	public void enter(Automaton automaton) {
		
	}

//	@Override
	public Object exit(Automaton automaton, Object initial_state, List<Object> modes) {
		return null;
		
	}

	@Override
	public Object visit(AST bot, List<Object> automata) {
		return automata;
	}

	@Override
	public void enter(automata.ast.Automaton automaton) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object exit(automata.ast.Automaton automaton, Object initial_state, List<Object> modes) {
		// TODO Auto-generated method stub
		return null;
	}

}
