package com.jtransc.dynarec.util;

import com.jtransc.dynarec.Local;

import java.util.HashMap;

public class LocalNamer {
	private HashMap<Local, String> names = new HashMap<Local, String>();
	private int lastId = 0;

	public String get(Local local) {
		if (!names.containsKey(local)) {
			names.put(local, "v" + lastId++);
		}
		return names.get(local);
	}
}
