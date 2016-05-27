package com.jtransc.dynarec.compiler;

import com.jtransc.dynarec.*;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
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
			} else if (stm instanceof Stm.SetArray) {
				Object array = interpret(((Stm.SetArray) stm).array);
				int index = (Integer) interpret(((Stm.SetArray) stm).index);
				Object value = interpret(((Stm.SetArray) stm).value);
				Array.set(array, index, value);
			} else if (stm instanceof Stm.While) {
				Expr cond = ((Stm.While) stm).cond;
				Stm body = ((Stm.While) stm).body;
				while ((Boolean) interpret(cond)) {
					interpret(body);
					if (completed) return;
				}
			} else if (stm instanceof Stm.StmExpr) {
				interpret(((Stm.StmExpr) stm).expr);
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
					case NE:
						return ((Comparable)left).compareTo(right) != 0;
					default:
						throw new RuntimeException("Not implemented binary operator " + binop.op);
				}
			} else if (expr instanceof Expr.NewArray) {
				Class<?> type = ((Expr.NewArray) expr).type;
				int size = (Integer) interpret(((Expr.NewArray) expr).size);
				return Array.newInstance(type, size);
			} else if (expr instanceof Expr.GetArray) {
				Object array = interpret(((Expr.GetArray) expr).array);
				int index = (Integer) interpret(((Expr.GetArray) expr).index);
				return Array.get(array, index);
			} else if (expr instanceof Expr.InvokeStatic) {
				Method method = ((Expr.InvokeStatic) expr).method;
				Expr[] argsExpr = ((Expr.InvokeStatic) expr).args;
				Object[] args = new Object[argsExpr.length];
				for (int n = 0; n < args.length; n++) {
					args[n] = interpret(argsExpr[n]);
				}
				try {
					return method.invoke(null, args);
				} catch (Throwable t) {
					throw new RuntimeException(t);
				}
			} else {
				throw new RuntimeException("Unknown expr " + expr);
			}
		}
	}

}
