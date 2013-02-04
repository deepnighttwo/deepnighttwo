package com.deepnighttwo.pythoninjava;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class PythonInJavaMainApp {

	/**
	 * @param args
	 * @throws ScriptException
	 */
	public static void main(String[] args) throws ScriptException {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("python");

		if (engine == null) {
			System.out.println("No engine found for python");
		}

		engine.eval("a=9");
	}

}
