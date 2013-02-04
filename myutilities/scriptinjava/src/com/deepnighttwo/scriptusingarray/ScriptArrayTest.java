package com.deepnighttwo.scriptusingarray;

import java.util.HashMap;
import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptArrayTest {

	/**
	 * @param args
	 * @throws ScriptException
	 */
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws ScriptException {
		ScriptEngineManager seManager = new ScriptEngineManager();
		ScriptEngine jsEngine = seManager.getEngineByName("javascript");
		if (jsEngine == null) {
			System.out.println("No engine found for javascript");
			System.exit(1);
		}
		jsEngine.eval("println('Coooool! Let\\'s Rock \\'n Roll with JS in java now!');");
		// jsEngine.put("arrjava", new String[] { "aaa", "bbb", "ccc" });
		jsEngine.put("arrjava", new double[] { 111.111, 222.222, 33.33 });
		jsEngine.put("cmp", new HashMap<String, Double>());
		jsEngine.eval("function getArrValue(arr){var v = arr[1];"
				+ "println((v+arr[0]));}");

		// parseFloat() parseInt()
		jsEngine.eval("function mapValue(){cmp.put('d1',3.8);cmp.put('d2',9)}");
		jsEngine.eval("var bd1=0; var bd2=0; function bindingValue(){bd1=3.8;bd2=9;}");

		jsEngine.eval("mapValue();println(cmp.get('d1') + cmp.get('d2'))");
		jsEngine.eval("bindingValue();println('double plus='+(bd1+bd2))");

		// 对于数组，声明了明确的类型，在js中是可以得到正确的类型的，比如double，int， 不会被转成string
		jsEngine.put("vvv", 1);
		jsEngine.eval("println('vvv plus='+(vvv+vvv))");

		jsEngine.put("vvvArr", new int[] { 1 });
		jsEngine.eval("println('vvvArr plus='+(vvvArr[0]+vvvArr[0]))");

		jsEngine.eval("println('arrjava plus='+(arrjava[0]+arrjava[1]))");

		// 对于map，都是string。这也不难理解，因为map。get方法的返回值类型是object，所以肯定会被转成string。规则应该是，如果知道是哪个基本类型，比如int，double，那么在js中就不会改变。
		Map<String, Double> dm = new HashMap<String, Double>();
		jsEngine.put("dm", dm);
		dm.put("d1", 1.1);
		dm.put("d2", 2.3);
		jsEngine.eval("println('vvv plus='+(dm.get('d1')+dm.get('d2')))");

		jsEngine.eval("function getRet(){return 1<2};");

		System.out.println(jsEngine.eval("getRet();"));

		jsEngine.eval("function getRetTTT(){return parseInt('1');};");

		System.out.println(jsEngine.eval("getRetTTT();").getClass().getName());

		Bindings b = jsEngine.createBindings();
		System.out.println(jsEngine.eval("getRetTTT();", b).getClass()
				.getName());

	}

	private static void defineFunctionAddInJs(ScriptEngine jsEngine,
			Bindings bindings) throws ScriptException {
		jsEngine.eval("function getArrValue(arr){var v = arr[1];"
				+ "println('value from java array'+v);}");
	}

}
