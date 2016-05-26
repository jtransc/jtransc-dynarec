package com.jtransc.dynarec;

abstract public class FunctionCompiler {
	public AnyInvoke compile(Function function) {
		return compile(AnyInvoke.class, function);
	}

	public <T> T compile(Class<T> clazz, Function function) {
		throw new RuntimeException("Not implemented yet!");
	}
}
