package com.jtransc.dynarec;

public interface Expr {
	class IntLiteral implements Expr {
		public final int value;

		public IntLiteral(int value) {
			this.value = value;
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
}
