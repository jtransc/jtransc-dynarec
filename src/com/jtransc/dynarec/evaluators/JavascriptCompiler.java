package com.jtransc.dynarec.evaluators;

import com.jtransc.annotation.haxe.HaxeMethodBody;
import com.jtransc.dynarec.AnyInvoke;
import com.jtransc.dynarec.Expr;
import com.jtransc.dynarec.Function;
import com.jtransc.dynarec.Stm;

public class JavascriptCompiler extends AstVisitor {
	private StringBuilder sb = new StringBuilder();

	@Override
	public void visit(Stm.Return stm) {
		sb.append("return ");
		super.visit(stm);
		sb.append(";");
	}

	@Override
	public void visit(Expr.Binop.Operator op) {
		switch (op) {
			case IADD:
				sb.append("+");
				break;
			case ISUB:
				sb.append("-");
				break;
			default:
				throw new RuntimeException("Invalid binary operator " + op);
		}
	}

	@Override
	public void visit(Expr.Binop expr) {
		sb.append("(");
		visit(expr.left);
		sb.append(" ");
		visit(expr.op);
		sb.append(" ");
		visit(expr.right);
		sb.append(")");
	}

	@Override
	public void visit(Expr.IntLiteral expr) {
		sb.append(expr.value);
	}

	private void generate(Stm stm) {
		if (stm instanceof Stm.Return) {
			sb.append("return ");
			generate(((Stm.Return) stm).expr);
			sb.append(";");
		} else {
			throw new RuntimeException("Unknown stm " + stm);
		}
	}

	private void generate(Expr expr) {
		if (expr instanceof Expr.IntLiteral) {
			sb.append(((Expr.IntLiteral) expr).value);
		} else if (expr instanceof Expr.Binop) {
			Expr.Binop binop = ((Expr.Binop) expr);
			generate(binop.left);
			generate(binop.right);
		} else {
			throw new RuntimeException("Unknown expr " + expr);
		}
	}

	// @TODO: Instead of evaluating everytime, generate the function and return it!
	@HaxeMethodBody(target = "js", value = "return HaxeNatives.box(untyped __js__('eval({0} + {1} + {2})', '(function() {', p0._str, '})()'));")
	native static private Object eval(String str);

	public static String generateCode(Function function) {
		JavascriptCompiler compiler = new JavascriptCompiler();
		compiler.visit(function);
		return compiler.sb.toString();
	}

	public static AnyInvoke compile(Function function) {
		final String code = generateCode(function);
		return new AnyInvoke() {
			@Override
			public Object invoke(Object... params) {
				return eval(code);
			}
		};
	}
}
