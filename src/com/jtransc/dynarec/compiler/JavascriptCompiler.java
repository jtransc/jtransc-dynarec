package com.jtransc.dynarec.compiler;

import com.jtransc.annotation.haxe.HaxeMethodBody;
import com.jtransc.dynarec.*;
import com.jtransc.dynarec.util.AstVisitor;
import com.jtransc.dynarec.util.LocalNamer;

public class JavascriptCompiler extends FunctionCompiler {
	public static String generateCode(Function function) {
		Impl compiler = new Impl();
		compiler.visit(function);
		return compiler.sb.toString();
	}

	public AnyInvoke compile(Function function) {
		final String code = generateCode(function);
		return new AnyInvoke() {
			@Override
			public Object invoke(Object... params) {
				return eval(code);
			}
		};
	}

	// @TODO: Instead of evaluating every time, generate the function and return it!
	@HaxeMethodBody(target = "js", value = "return HaxeNatives.box(untyped __js__('eval({0} + {1} + {2})', '(function() {', p0._str, '})()'));")
	native static private Object eval(String str);

	static private class Impl extends AstVisitor {
		private StringBuilder sb = new StringBuilder();

		private LocalNamer localNamer = new LocalNamer();

		@Override
		public void visit(Function function) {
			for (Local local : function.getLocals()) {
				sb.append("var ");
				sb.append(localNamer.get(local));
				sb.append(";");
			}
			super.visit(function);
		}

		@Override
		public void visit(Stm.Return stm) {
			sb.append("return ");
			super.visit(stm);
			sb.append(";");
		}

		@Override
		public void visit(Stm.Stms stm) {
			sb.append("{");
			super.visit(stm);
			sb.append("}");
		}

		@Override
		public void visit(Stm.SetLocal stm) {
			visit(stm.local);
			sb.append("=");
			visit(stm.expr);
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
		public void visit(Stm.If stm) {
			sb.append("if (");
			visit(stm.cond);
			sb.append(")");
			visit(stm.body);
		}

		@Override
		public void visit(Stm.IfElse stm) {
			sb.append("if (");
			visit(stm.cond);
			sb.append(")");
			visit(stm.strue);
			sb.append("else");
			visit(stm.sfalse);
		}

		@Override
		public void visit(Expr.Literal expr) {
			sb.append(expr.getValue());
		}

		@Override
		public void visit(Local local) {
			sb.append(localNamer.get(local));
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
	}
}
