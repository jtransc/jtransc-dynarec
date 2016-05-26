package com.jtransc.dynarec.example;

import com.jtransc.dynarec.Expr;
import com.jtransc.dynarec.Function;
import com.jtransc.dynarec.FunctionCompilers;
import com.jtransc.dynarec.Stm;

public class DynarecExample {
	static public void main(String[] args) {
		Function function = new Function(new Stm.Return(new Expr.Binop(new Expr.IntLiteral(1), Expr.Binop.Operator.IADD, new Expr.IntLiteral(2))));
		System.out.println(FunctionCompilers.getSuitableCompiler().compile(function).invoke());
	}
}
