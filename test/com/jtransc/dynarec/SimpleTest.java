package com.jtransc.dynarec;

import com.jtransc.dynarec.compiler.InterpreterCompiler;
import com.jtransc.dynarec.compiler.JavascriptCompiler;
import org.junit.Assert;
import org.junit.Test;

import static com.jtransc.dynarec.builder.FunctionBuilder.*;

public class SimpleTest {
	private Function function1 = FUNCTION(RETURN(IADD(INT(1), INT(2))));
	private Function function2 = FUNCTION(
		IF(BOOL(true), STMS(
			RETURN(IADD(INT(1), INT(2)))
		), STMS(
			RETURN(INT(-1))
		))
	);
	private Local local = new Local();
	private Function function3 = FUNCTION(
		SETLOCAL(local, INT(1)),
		RETURN(IADD(LOCAL(local), INT(2)))
	);
	private Function function4 = FUNCTION(
		RETURN(INT(1)),
		RETURN(INT(2))
	);

	private InterpreterCompiler interpreter = new InterpreterCompiler();

	@Test
	public void testInterpreter() {
		Assert.assertEquals(3, interpreter.compile(function1).invoke());
	}

	@Test
	public void testJsCodegen() {
		Assert.assertEquals("return (1 + 2);", JavascriptCompiler.generateCode(function1));
	}

	@Test
	public void testInterpreter2() {
		Assert.assertEquals(3, interpreter.compile(function2).invoke());
	}

	@Test
	public void testInterpreter3() {
		Assert.assertEquals(3, interpreter.compile(function3).invoke());
	}

	@Test
	public void testInterpreter4() {
		Assert.assertEquals(1, interpreter.compile(function4).invoke());
	}
}