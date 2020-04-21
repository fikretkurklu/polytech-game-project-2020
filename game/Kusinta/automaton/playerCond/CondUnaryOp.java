package playerCond;

import automaton.Entity;

public class CondUnaryOp implements ICondition {
	
	String operator;
	ICondition condition;
	
	public CondUnaryOp(String operator, ICondition condition) {
		this.operator = operator;
		this.condition = condition;
	}

	@Override
	public boolean eval(Entity e) {
		return !condition.eval(e); // Le seul opérateur unaire que l'on possède est la négation
	}

}
