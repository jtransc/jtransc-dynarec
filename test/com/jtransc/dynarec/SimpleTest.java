package com.jtransc.dynarec;

import com.jtransc.dynarec.evaluators.InterpreterCompiler;
import com.jtransc.dynarec.evaluators.JavascriptCompiler;
import org.junit.Assert;
import org.junit.Test;

public class SimpleTest {
	private Function function1 = new Function(new Stm.Return(new Expr.Binop(new Expr.IntLiteral(1), Expr.Binop.Operator.IADD, new Expr.IntLiteral(2))));
	private InterpreterCompiler interpreter = new InterpreterCompiler();

	@Test
	public void testInterpreter() {
		Assert.assertEquals(3, interpreter.compile(function1).invoke());
	}

	@Test
	public void testJsCodegen() {
		Assert.assertEquals("return (1 + 2);", JavascriptCompiler.generateCode(function1));
	}
}