package com.deepnighttwo.scopebinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScopeBindingTest {

	/**
	 * @param args
	 * @throws ScriptException
	 */
	public static void main(String[] args) throws ScriptException {
		ScriptEngineManager seManager = new ScriptEngineManager();
		ScriptEngine jsEngine = seManager.getEngineByName("javascript");
		if (jsEngine == null) {
			System.out.println("No engine found for javascript");
			System.exit(1);
		}
		jsEngine.eval("println('Coooool! Let\\'s Rock \\'n Roll with JS in java now!');");
		Bindings b = jsEngine.createBindings();
		jsEngine.eval(
				"function globalFunc(intVal,strVal){println('intvar='+intVal+', strVal=strVal')}; ",
				b);
		jsEngine.eval(
				"function globalFuncArr(arr){arr[2]=arr[0]+arr[1];println(arr[2]);}; ",
				b);

		jsEngine.setBindings(b, ScriptContext.GLOBAL_SCOPE);

		Bindings b2 = jsEngine.createBindings();

		jsEngine.eval(
				"var aaa = new Array(3); aaa[0]=0.2;aaa[1]=1.9;aaa[2]=9.9;", b2);


		// jsEngine.eval("globalFunc(i,s);", b2);
		jsEngine.eval("globalFuncArr(aaa);", b2);
		jsEngine.eval("println('localbinding aaa[2]='+aaa[2]);", b2);
	}

	public static void evalScriptFromEvn(String env, ScriptEngine engine,
			Bindings binding) {
		String envVal = System.getenv(env);
		if (envVal == null) {
			return;
		}
		if (envVal.charAt(0) == File.separatorChar) {
			try {
				engine.eval(new FileReader(envVal), binding);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		} else {
			try {
				engine.eval(envVal, binding);
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}
	}
}
