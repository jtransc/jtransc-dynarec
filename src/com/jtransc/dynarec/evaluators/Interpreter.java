package com.jtransc.dynarec.evaluators;

import com.jtransc.dynarec.AnyInvoke;
import com.jtransc.dynarec.Expr;
import com.jtransc.dynarec.Function;
import com.jtransc.dynarec.Stm;

public class Interpreter {
	Object result;
	boolean completed = false;

	static public AnyInvoke compile(final Function function) {
		return new AnyInvoke() {
			final Interpreter interpreter = new Interpreter();

			@Override
			public Object invoke(Object... params) {
				return interpreter.interpret(function);
			}
		};
	}

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
