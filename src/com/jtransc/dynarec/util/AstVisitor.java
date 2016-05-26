package com.jtransc.dynarec.util;

import com.jtransc.dynarec.Expr;
import com.jtransc.dynarec.Function;
import com.jtransc.dynarec.Local;
import com.jtransc.dynarec.Stm;

public class AstVisitor {
	public void visit(Function function) {
		visit(function.stm);
	}

	public void visit(Stm stm) {
		if (stm instanceof Stm.Return) {
			visit((Stm.Return)stm);
		} else if (stm instanceof Stm.Stms) {
			visit((Stm.Stms)stm);
		} else if (stm instanceof Stm.If) {
			visit((Stm.If)stm);
		} else if (stm instanceof Stm.SetLocal) {
			visit((Stm.SetLocal)stm);
		} else if (stm instanceof Stm.IfElse) {
			visit((Stm.IfElse)stm);
		} else {
			throw new RuntimeException("Unknown stm " + stm);
		}
	}

	public void visit(Stm.Stms stms) {
		for (Stm stm : stms.items) {
			visit(stm);
		}
	}

	public void visit(Stm.SetLocal stm) {
		visit(stm.local);
		visit(stm.expr);
	}

	public void visit(Stm.Return stm) {
		visit(stm.expr);
	}

	public void visit(Stm.If stm) {
		visit(stm.cond);
		visit(stm.body);
	}

	public void visit(Stm.IfElse stm) {
		visit(stm.cond);
		visit(stm.strue);
		visit(stm.sfalse);
	}

	public void visit(Expr expr) {
		if (expr instanceof Expr.Literal) {
			visit((Expr.Literal)expr);
		} else if (expr instanceof Expr.Local) {
			visit((Expr.Local)expr);
		} else if (expr instanceof Expr.Binop) {
			visit((Expr.Binop)expr);
		} else {
			throw new RuntimeException("Unknown expr " + expr);
		}
	}

	public void visit(Expr.Literal expr) {
		if (expr instanceof Expr.IntLiteral) {
			visit((Expr.IntLiteral)expr);
		} else if (expr instanceof Expr.BoolLiteral) {
			visit((Expr.BoolLiteral)expr);
		} else {
			throw new RuntimeException("Unknown literal " + expr);
		}
	}

	public void visit(Expr.Local expr) {
		visit(expr.local);
	}

	public void visit(Expr.IntLiteral expr) {
	}

	public void visit(Expr.BoolLiteral expr) {
	}

	public void visit(Expr.Binop.Operator operator) {

	}

	public void visit(Expr.Binop expr) {
		visit(expr.left);
		visit(expr.op);
		visit(expr.right);
	}

	public void visit(Local local) {
	}
}
