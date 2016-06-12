package com.jtransc.dynarec.compiler;

import com.jtransc.annotation.JTranscMethodBody;
import com.jtransc.annotation.haxe.HaxeMethodBody;
import com.jtransc.dynarec.*;
import com.jtransc.dynarec.util.AstVisitor;
import com.jtransc.dynarec.util.JTranscReflection;
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
	@JTranscMethodBody(target = "js", value = "return N.box(eval('(function() {' + N.istr(p0) + '})()'));")
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
			sb.append(" = ");
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
				case NE:
					sb.append("!=");
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
		public void visit(Stm.While stm) {
			sb.append("while (");
			visit(stm.cond);
			sb.append(")");
			visit(stm.body);
		}

		@Override
		public void visit(Expr.IntLiteral expr) {
			sb.append(expr.getValue());
		}

		@Override
		public void visit(Expr.BoolLiteral expr) {
			sb.append(expr.getValue());
		}

		@Override
		public void visit(Stm.StmExpr stm) {
			visit(stm.expr);
			sb.append(";");
		}

		@Override
		public void visit(Expr.Local expr) {
			visit(expr.local);
		}

		@Override
		public void visit(Local local) {
			sb.append(localNamer.get(local));
		}

		@Override
		public void visit(Expr.NewArray expr) {
			//super.visit(expr);
			// TODO: Use type!
			sb.append("new Int8Array(");
			visit(expr.size);
			sb.append(")");
		}

		@Override
		public void visit(Stm.SetArray stm) {
			visit(stm.array);
			sb.append("[");
			visit(stm.index);
			sb.append("] = ");
			visit(stm.value);
			sb.append(";");
		}

		@Override
		public void visit(Expr.GetArray expr) {
			visit(expr.array);
			sb.append("[");
			visit(expr.index);
			sb.append("]");
		}

		@Override
		public void visit(Expr.InvokeStatic expr) {
			String internalClassName = JTranscReflection.getInternalName(expr.method.getDeclaringClass());
			String internalMethodName = JTranscReflection.getInternalName(expr.method);
			//com_jtransc_dynarec_example_BrainfuckDynarec_$BrainfuckRuntime_$
			sb.append(internalClassName);
			sb.append("['");
			sb.append(internalMethodName);
			sb.append("'](");
			for (int n = 0; n < expr.args.length; n++) {
				if (n != 0) sb.append(", ");
				visit(expr.args[n]);
			}
			sb.append(")");
		}
	}
}
