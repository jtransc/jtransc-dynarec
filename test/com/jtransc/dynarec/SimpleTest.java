package com.jtransc.dynarec;

import com.jtransc.dynarec.evaluators.JavascriptCompiler;
import org.junit.Assert;
import org.junit.Test;

public class SimpleTest {
	private Function function1 = new Function(new Stm.Return(new Expr.Binop(new Expr.IntLiteral(1), Expr.Binop.Operator.IADD, new Expr.IntLiteral(2))));

	@Test
	public void test1() {
		Assert.assertEquals(3, function1.compile().invoke());
	}

	@Test
	public void test2() {
		Assert.assertEquals("return (1 + 2);", JavascriptCompiler.generateCode(function1));
	}
}