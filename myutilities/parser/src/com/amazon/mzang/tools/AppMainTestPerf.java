package com.amazon.mzang.tools;

import java.text.DecimalFormat;

public class AppMainTestPerf {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 5000 * 65 * 20; i++) {
			new Double(Math.random() * 10000);
		}
		System.out.println("Random=" + (System.currentTimeMillis() - start));

		start = System.currentTimeMillis();
		DecimalFormat df = new DecimalFormat(".##");
		for (int i = 0; i < 5000 * 65 * 20; i++) {
			df.format(new Double(Math.random() * 10000));
		}
		System.out.println("format=" + (System.currentTimeMillis() - start));

		start = System.currentTimeMillis();
		for (int i = 0; i < 5000 * 65 * 20; i++) {
			new Double(Math.random() * 10000).toString();
		}
		System.out.println("toString=" + (System.currentTimeMillis() - start));

		start = System.currentTimeMillis();
		for (int i = 0; i < 5000 * 65 * 20; i++) {
			String.valueOf(new Double(Math.random() * 10000));
		}
		System.out.println("toString=" + (System.currentTimeMillis() - start));
	}

}
