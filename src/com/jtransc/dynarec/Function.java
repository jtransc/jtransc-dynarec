package com.jtransc.dynarec;

import com.jtransc.dynarec.util.AstVisitor;

import java.util.HashSet;
import java.util.Set;

public class Function {
	public final Stm stm;

	public Function(Stm stm) {
		this.stm = stm;
	}

	public Local[] getLocals() {
		final Set<Local> locals = new HashSet<Local>();
		new AstVisitor() {
			@Override
			public void visit(Local local) {
				locals.add(local);
			}
		}.visit(this);
		return locals.toArray(new Local[locals.size()]);
	}
}
