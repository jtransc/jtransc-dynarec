package com.jtransc.dynarec;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public interface Expr {
	interface Literal extends Expr {
		Object getValue();
	}

	class BoolLiteral implements Literal {
		public final boolean value;

		public BoolLiteral(boolean value) {
			this.value = value;
		}

		public Object getValue() {
			return value;
		}
	}

	class IntLiteral implements Literal {
		public final int value;

		public IntLiteral(int value) {
			this.value = value;
		}

		public Object getValue() {
			return value;
		}
	}

	class Binop implements Expr {
		public enum Operator {
			IADD, // +
			ISUB, // -
			NE    // !=
			;
		}

		public final Expr left;
		public final Operator op;
		public final Expr right;

		public Binop(Expr left, Operator op, Expr right) {
			this.left = left;
			this.op = op;
			this.right = right;
		}
	}

	class Local implements Expr {
		public final com.jtransc.dynarec.Local local;

		public Local(com.jtransc.dynarec.Local local) {
			this.local = local;
		}
	}

	class GetArray implements Expr {
		public final Expr array;
		public final Expr index;

		public GetArray(Expr array, Expr index) {
			this.array = array;
			this.index = index;
		}
	}

	class NewArray implements Expr {
		public final Class<?> type;
		public final Expr size;

		public NewArray(Class<?> type, Expr size) {
			this.type = type;
			this.size = size;
		}
	}

	class InvokeStatic implements Expr {
		public final Method method;
		public final Expr[] args;

		public InvokeStatic(Method method, Expr[] args) {
			this.method = method;
			this.args = args;
		}
	}
}
