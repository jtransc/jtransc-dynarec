package com.jtransc.dynarec.compiler;

import com.jtransc.dynarec.*;

import java.util.HashMap;

public class InterpreterCompiler extends FunctionCompiler {
	@Override
	public AnyInvoke compile(final Function function) {
		return new AnyInvoke() {
			final Impl interpreter = new Impl();

			@Override
			public Object invoke(Object... params) {
				return interpreter.interpret(function);
			}
		};
	}

	static private class Impl {
		Object result;
		boolean completed = false;

		private HashMap<Local, Object> locals = new HashMap<Local, Object>();

		public Object interpret(Function function) {
			interpret(function.stm);
			return result;
		}

		private void interpret(Stm stm) {
			if (stm instanceof Stm.Return) {
				result = interpret(((Stm.Return) stm).expr);
				completed = true;
			} else if (stm instanceof Stm.Stms) {
				Stm.Stms stms = (Stm.Stms) stm;
				for (Stm item : stms.items) {
					interpret(item);
					if (completed) return;
				}
			} else if (stm instanceof Stm.IfElse) {
				Stm.IfElse stm2 = (Stm.IfElse) stm;
				boolean cond = (Boolean) interpret(stm2.cond);
				if (cond) {
					interpret(stm2.strue);
				} else {
					interpret(stm2.sfalse);
				}
			} else if (stm instanceof Stm.SetLocal) {
				Stm.SetLocal setlocal = (Stm.SetLocal) stm;
				locals.put(setlocal.local, interpret(setlocal.expr));
			} else {
				throw new RuntimeException("Unknown stm " + stm);
			}
		}

		private Object interpret(Expr expr) {
			if (expr instanceof Expr.Literal) {
				return ((Expr.Literal) expr).getValue();
			} else if (expr instanceof Expr.Local) {
				return locals.get(((Expr.Local) expr).local);
			} else if (expr instanceof Expr.Binop) {
				final Expr.Binop binop = (Expr.Binop) expr;
				Object left = interpret(binop.left);
				Object right = interpret(binop.right);
				switch (binop.op) {
					case IADD:
						return ((Integer) left) + ((Integer) right);
					case ISUB:
						return ((Integer) left) - ((Integer) right);
					default:
						throw new RuntimeException("Not implemented binary operator " + binop.op);
				}
			} else {
				throw new RuntimeException("Unknown expr " + expr);
			}
		}
	}

}
