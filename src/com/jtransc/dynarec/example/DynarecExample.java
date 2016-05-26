package com.jtransc.dynarec.example;

import com.jtransc.dynarec.Function;
import com.jtransc.dynarec.FunctionCompiler;
import com.jtransc.dynarec.FunctionCompilers;
import com.jtransc.dynarec.Local;

import static com.jtransc.dynarec.builder.FunctionBuilder.*;

public class DynarecExample {
	static public void main(String[] args) {
		Local local = new Local();
		Function function = FUNCTION(
			SETLOCAL(local, BOOL(true)),
			IF(LOCAL(local), STMS(
				RETURN(IADD(LOCAL(local), INT(2)))
			), STMS(
				RETURN(INT(-1))
			))
		);
		FunctionCompiler suitableCompiler = FunctionCompilers.getSuitableCompiler();
		System.out.println(suitableCompiler.getClass().getName());
		System.out.println(suitableCompiler.compile(function).invoke());
	}
}
