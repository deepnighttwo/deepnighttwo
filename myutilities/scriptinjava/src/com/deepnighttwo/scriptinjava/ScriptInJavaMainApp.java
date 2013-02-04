package com.deepnighttwo.scriptinjava;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.deepnighttwo.commonjavacode.JavaClassForScript;

public class ScriptInJavaMainApp {

	/**
	 * @param args
	 * @throws ScriptException
	 * @throws NoSuchMethodException
	 */
	public static void main(String[] args) throws ScriptException,
			NoSuchMethodException {
		ScriptEngineManager seManager = new ScriptEngineManager();
		ScriptEngine jsEngine = seManager.getEngineByName("javascript");
		if (jsEngine == null) {
			System.out.println("No engine found for javascript");
			System.exit(1);
		}
		jsEngine.eval("println('Coooool! Let\\'s Rock \\'n Roll with JS in java now!');");

		/** Context and bindings */
		System.out.println("=======About Bindings=======");
		aboutBindings(jsEngine);
		System.out.println("============================");

		System.out.println("");

		defineFunctionAddInJs(jsEngine);
		jsEngine.put("javaClassForJS", new JavaClassForScript());

		/** call java method from JS */
		System.out.println("=======Interactions between java and js=======");
		invokeJavaUsingJs(jsEngine);
		System.out.println("==============================================");

		System.out.println("");

		/** invoke js function using js, using java and performance test */
		System.out.println("=======Performance Test=======");

		int loop = 1000000;

		long start = System.currentTimeMillis();

		invokeJsLoopFunctionUsingJs(jsEngine, loop);

		System.out.println("JS loop (from js) uses "
				+ (System.currentTimeMillis() - start) / 1000 + " sec(s).");

		start = System.currentTimeMillis();
		for (int i = 0; i < loop; i++) {
			@SuppressWarnings("unused")
			int num = 9 + 8;
		}
		System.out.println("Java uses " + (System.currentTimeMillis() - start)
				+ " mill sec(s).");

		start = System.currentTimeMillis();
		for (int i = 0; i < loop; i++) {
			invokeJsFunctionUsingJs(jsEngine, i);
		}
		System.out.println("JS (from js) uses "
				+ (System.currentTimeMillis() - start) / 1000 + " sec(s).");

		start = System.currentTimeMillis();
		for (int i = 0; i < loop; i++) {
			invokeJsFunctionUsingJava(jsEngine, i);
		}
		System.out.println("JS (from java) uses "
				+ (System.currentTimeMillis() - start) / 1000 + " sec(s).");
		System.out.println("=============================");

		start = System.currentTimeMillis();

		invokeJavaFromJS(jsEngine, 7, loop);

		System.out.println("Call Java using JS uses "
				+ (System.currentTimeMillis() - start) / 1000 + " sec(s).");
		System.out.println("=============================");

	}

	private static void defineFunctionAddInJs(ScriptEngine jsEngine)
			throws ScriptException {
		jsEngine.eval("function addTwoNumbers(value){var num = 9+8+value}");
	}

	private static void invokeJsFunctionUsingJs(ScriptEngine jsEngine, int i)
			throws ScriptException {
		jsEngine.eval("addTwoNumbers(" + i + ")");
	}

	private static void invokeJsLoopFunctionUsingJs(ScriptEngine jsEngine, int i)
			throws ScriptException {
		jsEngine.eval("for(var v=0;v<" + i + ";v++){addTwoNumbers(" + i + ")}");
	}

	private static void invokeJsFunctionUsingJava(ScriptEngine jsEngine, int i)
			throws ScriptException, NoSuchMethodException {
		Invocable invocable = (Invocable) jsEngine;
		invocable.invokeFunction("addTwoNumbers", i);
	}

	private static void invokeJavaFromJS(ScriptEngine jsEngine, int i, int loop)
			throws ScriptException {
		jsEngine.eval("for(var v=0;v<" + loop
				+ ";v++){javaClassForJS.addTwoNumbers(" + i + ")}");
	}

	private static void invokeJavaUsingJs(ScriptEngine jsEngine)
			throws ScriptException {
		// add an object to ENGINE_SCOPE
		jsEngine.eval("println('Invoking java code from JS...');"
				+ "var num = javaClassForJS.getRandomNumber(99);"
				+ "println('Number returned from java is '+num);"
				+ "var str = javaClassForJS.getRandomString('GoGoGo');"
				+ "println('String returned from java is '+str);"
				+ "var stNum = javaClassForJS.getRandomNumberStatic(987);"
				+ "println('Number returned from java static method is '+stNum);");

		// all variables created in javascript are stored in ENGINE_SCOPE
		// bindings
		System.out.println("Get variables from js in java:");
		System.out.println(jsEngine.get("num"));
		Object jsObject = jsEngine.get("str");
		System.out.println(jsObject);
		System.out.println(jsEngine.get("stNum"));

	}

	private static void aboutBindings(ScriptEngine jsEngine)
			throws ScriptException {
		Bindings bindings = jsEngine.createBindings();
		bindings.put("value", "Value from bindings conext");
		jsEngine.eval("println(value)", bindings);
		// the following code will trigger an exception
		try {
			jsEngine.eval("println(value)");
		} catch (ScriptException ex) {
			System.out.println("Exception :" + ex.getMessage());
		}
		// here is the script engine runtime context
		ScriptContext scriptConext = jsEngine.getContext();
		System.out.println(scriptConext);
		// just for this script engine;
		Bindings engineBindings = jsEngine
				.getBindings(ScriptContext.ENGINE_SCOPE);
		System.out.println(engineBindings.size());
		// shared with all script engines created by the same
		// ScriptEngineManager
		System.out.println(jsEngine.getBindings(ScriptContext.GLOBAL_SCOPE)
				.size());
		// set ENGINE_SCOPE bindings and now the invocation has no exception.
		jsEngine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
		jsEngine.eval("println(value)");
	}
}
