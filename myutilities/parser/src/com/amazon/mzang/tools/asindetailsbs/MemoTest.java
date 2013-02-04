package com.amazon.mzang.tools.asindetailsbs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class MemoTest {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		test(new HashSet<String>(3000000), "HashSet");
		test(new TreeSet<String>(), "TreeSet");
	}

	public static void test(Set<String> aaa, String name) throws IOException {

		BufferedReader origReader = new BufferedReader(new FileReader(new File(
				"G:\\UnhealthyAsinDetails-transformed.2012-08-21_to_2012-08-28.txt")));
		String line = origReader.readLine();
		long start = System.currentTimeMillis();
		while ((line = origReader.readLine()) != null) {
			String asin = new String(line.substring(0, line.indexOf(',')));
			aaa.add(asin);
		}
		System.out.println(name + " write:" + (System.currentTimeMillis() - start) / 1000);

		origReader = new BufferedReader(new FileReader(new File(
				"G:\\UnhealthyAsinDetails-transformed.2012-08-21_to_2012-08-28.txt")));
		line = origReader.readLine();
		start = System.currentTimeMillis();
		while ((line = origReader.readLine()) != null) {
			String asin = new String(line.substring(0, line.indexOf(',')));
			aaa.contains(asin);
		}
		System.out.println(name + " find:" + (System.currentTimeMillis() - start) / 1000);

	}

}
