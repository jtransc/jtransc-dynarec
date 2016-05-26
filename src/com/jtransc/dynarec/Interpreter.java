package com.jtransc.dynarec;

public class Interpreter {
	Object result;
	boolean completed = false;

	public Object interpret(Function function) {
		interpret(function.stm);
		return result;
	}

	private void interpret(Stm stm) {
		if (stm instanceof Stm.Return) {
			result = interpret(((Stm.Return) stm).expr);
			completed = true;
		} else {
			throw new RuntimeException("Unknown stm " + stm);
		}
	}

	private Object interpret(Expr expr) {
		if (expr instanceof Expr.IntLiteral) {
			return ((Expr.IntLiteral) expr).value;
		} else {
			throw new RuntimeException("Unknown expr " + expr);
		}
	}
}
