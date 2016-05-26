package com.jtransc.dynarec;

public interface Expr {
	class IntLiteral implements Expr {
		public final int value;

		public IntLiteral(int value) {
			this.value = value;
		}
	}
}
