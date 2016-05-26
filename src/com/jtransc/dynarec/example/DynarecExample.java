package com.jtransc.dynarec.example;

import com.jtransc.dynarec.Expr;
import com.jtransc.dynarec.Function;
import com.jtransc.dynarec.Stm;

public class DynarecExample {
	static public void main(String[] args) {
		Function function = new Function(new Stm.Return(new Expr.IntLiteral(1)));
		System.out.println(function.compile().invoke());
	}
}
