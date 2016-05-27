package com.jtransc.dynarec;

public interface Stm {
	class If implements Stm {
		public final Expr cond;
		public final Stm body;

		public If(Expr cond, Stm body) {
			this.cond = cond;
			this.body = body;
		}
	}

	class IfElse implements Stm {
		public final Expr cond;
		public final Stm strue;
		public final Stm sfalse;

		public IfElse(Expr cond, Stm strue, Stm sfalse) {
			this.cond = cond;
			this.strue = strue;
			this.sfalse = sfalse;
		}
	}

	class While implements Stm {
		public final Expr cond;
		public final Stm body;

		public While(Expr cond, Stm body) {
			this.cond = cond;
			this.body = body;
		}
	}

	class Return implements Stm {
		public final Expr expr;

		public Return(Expr expr) {
			this.expr = expr;
		}
	}

	class StmExpr implements Stm {
		public final Expr expr;

		public StmExpr(Expr expr) {
			this.expr = expr;
		}
	}

	class Stms implements Stm {
		public final Stm[] items;

		public Stms(Stm[] items) {
			this.items = items;
		}
	}

	class SetLocal implements Stm {
		public final Local local;
		public final Expr expr;

		public SetLocal(Local local, Expr expr) {
			this.local = local;
			this.expr = expr;
		}
	}

	class SETARRAY implements Stm {
		public final Expr array;
		public final Expr index;
		public final Expr value;

		public SETARRAY(Expr array, Expr index, Expr value) {
			this.array = array;
			this.index = index;
			this.value = value;
		}
	}
}
