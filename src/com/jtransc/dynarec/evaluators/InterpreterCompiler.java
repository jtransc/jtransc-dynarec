package com.jtransc.dynarec.evaluators;

import com.jtransc.dynarec.*;

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
