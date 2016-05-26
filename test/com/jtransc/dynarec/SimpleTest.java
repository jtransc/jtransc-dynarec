package com.jtransc.dynarec;

import org.junit.Assert;
import org.junit.Test;

public class SimpleTest {
	@Test
	public void test1() {
		Function function = new Function(new Stm.Return(new Expr.IntLiteral(1)));
		Assert.assertEquals(1, function.compile().invoke());
	}
}
