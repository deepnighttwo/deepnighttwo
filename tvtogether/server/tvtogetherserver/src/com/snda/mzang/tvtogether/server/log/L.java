package com.snda.mzang.tvtogether.server.log;

public class L {

	public static void error(Object obj) {
		System.err.println(obj.toString());

	}

	public static void info(Object obj) {
		System.out.println(obj.toString());

	}

	public static void warning(Object obj) {
		System.err.println(obj.toString());
	}

}
