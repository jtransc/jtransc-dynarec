package com.jtransc.dynarec.builder;

import com.jtransc.annotation.JTranscInvisible;
import com.jtransc.dynarec.Expr;
import com.jtransc.dynarec.Function;
import com.jtransc.dynarec.Local;
import com.jtransc.dynarec.Stm;

import java.lang.reflect.Method;

@JTranscInvisible
public class FunctionBuilder {
	static public Expr.Binop IADD(Expr left, Expr right) {
		return new Expr.Binop(left, Expr.Binop.Operator.IADD, right);
	}

	static public Expr.Binop ISUB(Expr left, Expr right) {
		return new Expr.Binop(left, Expr.Binop.Operator.ISUB, right);
	}

	static public Expr.Binop NE(Expr left, Expr right) {
		return new Expr.Binop(left, Expr.Binop.Operator.NE, right);
	}

	static public Expr.IntLiteral INT(int value) {
		return new Expr.IntLiteral(value);
	}

	static public Expr.BoolLiteral BOOL(boolean value) {
		return new Expr.BoolLiteral(value);
	}

	static public Stm.Return RETURN(Expr expr) {
		return new Stm.Return(expr);
	}

	static public Stm.If IF(Expr cond, Stm body) {
		return new Stm.If(cond, body);
	}

	static public Stm.While WHILE(Expr cond, Stm body) {
		return new Stm.While(cond, body);
	}

	static public Stm.IfElse IF(Expr cond, Stm strue, Stm sfalse) {
		return new Stm.IfElse(cond, strue, sfalse);
	}

	static public Stm STM(Expr expr) {
		return new Stm.StmExpr(expr);
	}

	static public Stm STMS(Stm... items) {
		if (items.length == 1) {
			return items[0];
		} else {
			return new Stm.Stms(items);
		}
	}

	static public Function FUNCTION(Stm... items) {
		return new Function(STMS(items));
	}

	static public Expr.Local LOCAL(Local local) {
		return new Expr.Local(local);
	}

	static public Stm.SetLocal SETLOCAL(Local local, Expr expr) {
		return new Stm.SetLocal(local, expr);
	}

	static public Expr GETARRAY(Expr array, Expr index) {
		return new Expr.GetArray(array, index);
	}

	static public Stm SETARRAY(Expr array, Expr index, Expr value) {
		return new Stm.SetArray(array, index, value);
	}

	static public Expr CALLSTATIC(Method method, Expr... args) {
		return new Expr.InvokeStatic(method, args);
	}


	static public Expr NEWARRAY(Class<?> type, Expr size) {
		return new Expr.NewArray(type, size);
	}

}
