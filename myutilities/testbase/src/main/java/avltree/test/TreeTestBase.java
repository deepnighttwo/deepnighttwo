package avltree.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TreeTestBase {
	
	protected int loop = 50;
	protected int unit = 500000;
	
	private static Random r = new Random();

	
	protected static boolean output = false;

	
	protected static Integer getRamdomInt() {
		return r.nextInt();
	}

	protected static void log(Object obj) {
		if (output)
			System.out.println(obj);
	}

	protected static void logc(Object obj) {
		if (output)
			System.out.print(obj);
	}

	protected void log() {
		if (output)
			System.out.println();
	}


	/**
	 * util method for find bugs
	 * 
	 * @return
	 */
	public int[] getSimpleArray(int[] a) {

		List<Integer> temp = new ArrayList<Integer>();
		for (int i = 0; i < a.length; i++) {
			temp.add(a[i]);
		}
		Collections.sort(temp);

		for (int v = 0; v < temp.size(); v++) {
			for (int i = 0; i < a.length; i++) {
				if (temp.get(v) == a[i]) {
					a[i] = v;
				}
			}
		}
		log(a);
		return a;
	}

}
