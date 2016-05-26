package com.jtransc.dynarec;

import com.jtransc.JTranscSystem;
import com.jtransc.dynarec.evaluators.InterpreterCompiler;
import com.jtransc.dynarec.evaluators.JavascriptCompiler;
import com.jtransc.dynarec.evaluators.JvmCompiler;

public class FunctionCompilers {
	static public FunctionCompiler getSuitableCompiler() {
		if (JTranscSystem.isJs()) {
			return new JavascriptCompiler();
		} else if (JTranscSystem.isJava()) {
			return new JvmCompiler();
		} else {
			return new InterpreterCompiler();
		}
	}
}
