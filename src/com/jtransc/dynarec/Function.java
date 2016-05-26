package com.jtransc.dynarec;

import com.jtransc.JTranscSystem;
import com.jtransc.dynarec.evaluators.Interpreter;
import com.jtransc.dynarec.evaluators.JavascriptCompiler;

public class Function {
	public final Stm stm;

	public Function(Stm stm) {
		this.stm = stm;
	}

	public AnyInvoke compile() {
		if (JTranscSystem.isJs()) {
			return JavascriptCompiler.compile(this);
		} else {
			return Interpreter.compile(this);
		}
	}

	public <T> T compile(Class<T> clazz) {
		throw new RuntimeException("Not implemented yet!");
	}
}
