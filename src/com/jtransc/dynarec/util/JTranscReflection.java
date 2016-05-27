package com.jtransc.dynarec.util;

import com.jtransc.annotation.haxe.HaxeMethodBody;

import java.lang.reflect.Method;

public class JTranscReflection {
	@HaxeMethodBody("return N.str(p0._internalName);")
	static public String getInternalName(Class<?> clazz) {
		return clazz.getName();
	}

	@HaxeMethodBody("return N.str(p0._internalName);")
	static public String getInternalName(Method method) {
		return method.getName();
	}
}
