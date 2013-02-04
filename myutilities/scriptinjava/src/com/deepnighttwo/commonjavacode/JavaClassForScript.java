package com.deepnighttwo.commonjavacode;

public class JavaClassForScript {
	private int val = (int) (1000 * Math.random());

	private String helloStr = "Hellow Script. Welcome for invoking.";

	private static int stVal = (int) (1000 * Math.random());

	private static String stHelloStr = "Hellow Script. Welcome for invoking.";

	public int getRandomNumber(int base) {
		System.out.println(helloStr);
		return val + (int) (base * Math.random());
	}

	public String getRandomString(String base) {
		System.out.println(helloStr);
		return base + (int) (val * Math.random());
	}

	public static int getRandomNumberStatic(int base) {
		System.out.println(stHelloStr);
		return stVal + (int) (base * Math.random());
	}

	public static int addTwoNumbers(int a) {
		return a + 9;
	}

}