package com.amazon.mzang.tools.asindetailsbs;

import java.util.TreeMap;

public class SortedMapTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TreeMap<Integer, Object> sorted = new TreeMap<Integer, Object>();
		for (int i = 0; i < 10000; i++) {
			sorted.put((int) (Math.random() * 1000000), null);
		}

		int small = -1;
		while (sorted.size() > 0) {
			int nextSmall = sorted.firstKey();
			System.out.println(nextSmall);
			if (small >= nextSmall) {
				throw new RuntimeException(small + " - " + nextSmall);
			}
			sorted.remove(nextSmall);
			small = nextSmall;
		}
		System.out.println("Finished!");
	}

}
