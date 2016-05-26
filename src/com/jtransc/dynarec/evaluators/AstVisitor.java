package com.jtransc.dynarec.evaluators;

import com.jtransc.dynarec.Expr;
import com.jtransc.dynarec.Function;
import com.jtransc.dynarec.Stm;

public class AstVisitor {
	public void visit(Function function) {
		visit(function.stm);
	}

	public void visit(Stm stm) {
		if (stm instanceof Stm.Return) {
			visit((Stm.Return)stm);
		} else {
			throw new RuntimeException("Unknown stm " + stm);
		}
	}

	public void visit(Stm.Return stm) {
		visit(stm.expr);
	}

	public void visit(Expr expr) {
		if (expr instanceof Expr.IntLiteral) {
			visit((Expr.IntLiteral)expr);
		} else if (expr instanceof Expr.Binop) {
			visit((Expr.Binop)expr);
		} else {
			throw new RuntimeException("Unknown expr " + expr);
		}
	}

	public void visit(Expr.IntLiteral expr) {
	}

	public void visit(Expr.Binop.Operator operator) {

	}

	public void visit(Expr.Binop expr) {
		visit(expr.left);
		visit(expr.op);
		visit(expr.right);
	}
}
