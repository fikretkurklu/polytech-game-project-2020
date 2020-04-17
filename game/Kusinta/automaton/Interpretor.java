package automaton;

import java.util.List;

import info3.game.automata.ast.AST;
import info3.game.automata.ast.Action;
import info3.game.automata.ast.Behaviour;
import info3.game.automata.ast.BinaryOp;
import info3.game.automata.ast.Category;
import info3.game.automata.ast.Condition;
import info3.game.automata.ast.Direction;
import info3.game.automata.ast.FunCall;
import info3.game.automata.ast.IVisitor;
import info3.game.automata.ast.Key;
import info3.game.automata.ast.Mode;
import info3.game.automata.ast.Transition;
import info3.game.automata.ast.UnaryOp;
import info3.game.automata.ast.Underscore;
import info3.game.automata.ast.Value;

public class Interpretor implements IVisitor {

	String fun_name;

	@Override
	public Object visit(Category cat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Direction dir) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Key key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Value v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Underscore u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enter(FunCall funcall) {
		fun_name = funcall.name;
	}

	@Override
	public Object exit(FunCall funcall, List<Object> parameters) {
/*		if (fun_name.compareTo("Hit")) {
		ActionHit ac = new ActionHit();
		} else if (fun_name.compareTo("Egg")) {
			ActionEgg ac = new ActionEgg();
		} else if (fun_name.compareTo("Pop")) {

		} else if (fun_name.compareTo("Wiz")) {

		} else if (fun_name.compareTo("Jump")) {

		} else if (fun_name.compareTo("Move")) {

		} else if (fun_name.compareTo("Pick")) {

		} else if (fun_name.compareTo("Power")) {

		} else if (fun_name.compareTo("Turn")) {

		} else if (fun_name.compareTo("Wizz")) {

		}*/
		return null;
	}

	@Override
	public Object visit(BinaryOp operator, Object left, Object right) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(UnaryOp operator, Object expression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(info3.game.automata.ast.State state) {
		return new State(state.name);
	}

	@Override
	public void enter(Mode mode) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object exit(Mode mode, Object source_state, Object behaviour) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Behaviour behaviour, List<Object> transitions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enter(Condition condition) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object exit(Condition condition, Object expression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enter(Action acton) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object exit(Action action, List<Object> funcalls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Transition transition, Object condition, Object action, Object target_state) {
		// TODO Auto-generated method stub
		return null;
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
	public void enter(info3.game.automata.ast.Automaton automaton) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object exit(info3.game.automata.ast.Automaton automaton, Object initial_state, List<Object> modes) {
		// TODO Auto-generated method stub
		return null;
	}

}
