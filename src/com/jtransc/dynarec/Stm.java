package com.jtransc.dynarec;

public interface Stm {
	class Return implements Stm {
		public final Expr expr;

		public Return(Expr expr) {
			this.expr = expr;
		}
	}
}
