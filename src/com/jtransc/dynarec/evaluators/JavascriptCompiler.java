package com.jtransc.dynarec.evaluators;

import com.jtransc.annotation.haxe.HaxeMethodBody;
import com.jtransc.dynarec.AnyInvoke;
import com.jtransc.dynarec.Expr;
import com.jtransc.dynarec.Function;
import com.jtransc.dynarec.Stm;

public class JavascriptCompiler {
	public String generate(Function function) {
		final StringBuilder sb = new StringBuilder();
		generate(function.stm, sb);
		return sb.toString();
	}

	private void generate(Stm stm, StringBuilder sb) {
		if (stm instanceof Stm.Return) {
			sb.append("return ");
			generate(((Stm.Return) stm).expr, sb);
			sb.append(";");
		} else {
			throw new RuntimeException("Unknown stm " + stm);
		}
	}

	private void generate(Expr expr, StringBuilder sb) {
		if (expr instanceof Expr.IntLiteral) {
			sb.append(((Expr.IntLiteral) expr).value);
		} else {
			throw new RuntimeException("Unknown expr " + expr);
		}
	}

	@HaxeMethodBody(target = "js", value = "return HaxeNatives.box(untyped __js__('eval({0} + {1} + {2})', '(function() {', p0._str, '})()'));")
	native static private Object eval(String str);

	public static AnyInvoke compile(Function function) {
		final String code = new JavascriptCompiler().generate(function);
		return new AnyInvoke() {
			@Override
			public Object invoke(Object... params) {
				return eval(code);
			}
		};
	}
}
