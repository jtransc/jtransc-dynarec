package com.jtransc.dynarec;

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
			IADD, ISUB;
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

	public class Local implements Expr {
		public final com.jtransc.dynarec.Local local;

		public Local(com.jtransc.dynarec.Local local) {
			this.local = local;
		}
	}
}
