package playerCond;

import game.Entity;

public class CondBinaryOp implements ICondition {
	
	String operator;
	ICondition leftCondition;
	ICondition rightCondition;
	
	public CondBinaryOp(String operator, ICondition left, ICondition right) {
		this.operator = operator;
		this.leftCondition = left;
		this.rightCondition = right;
	}

	@Override
	public boolean eval(Entity e) {
		switch(operator) {
		case "&" :
			return leftCondition.eval(e) && rightCondition.eval(e);
		case "/" :
			return leftCondition.eval(e) || rightCondition.eval(e); 
		}
		return false;
	}

}
