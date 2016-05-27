package com.jtransc.dynarec;

import com.jtransc.JTranscSystem;
import com.jtransc.dynarec.compiler.InterpreterCompiler;
import com.jtransc.dynarec.compiler.JavascriptCompiler;
import com.jtransc.dynarec.compiler.JvmCompiler;
import com.jtransc.dynarec.compiler.PhpCompiler;

public class FunctionCompilers {
	static public FunctionCompiler getSuitableCompiler() {
		if (JTranscSystem.isJs()) {
			return new JavascriptCompiler();
		} /*else if (JTranscSystem.isJava()) {
			return new JvmCompiler();
		} */else if (JTranscSystem.isPhp()) {
			return new PhpCompiler();
		} else {
			return new InterpreterCompiler();
		}
	}
}
