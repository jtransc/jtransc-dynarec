package com.jtransc.dynarec;

public class Function {
	public final Stm stm;

	public Function(Stm stm) {
		this.stm = stm;
	}

	public AnyInvoke compile() {
		final Function function = this;
		return new AnyInvoke() {
			final Interpreter interpreter = new Interpreter();

			@Override
			public Object invoke(Object... params) {
				return interpreter.interpret(function);
			}
		};
	}

	public <T> T compile(Class<T> clazz) {
		throw new RuntimeException("Not implemented yet!");
	}
}
