package avltree.test;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.junit.Test;

import avltree.AVLTree;

public class TreeQueryPerf extends TreeTestBase {

	int len = 8000000;

	@Test
	public void testAVLTreePerformance() {

		AVLTree<Integer, String> tree = new AVLTree<Integer, String>();
		List<Integer> keys = new ArrayList<Integer>(len);

		for (int j = 0; j < len; j++) {
			String dummyValue = "";
			int a = getRamdomInt();
			tree.put(a, dummyValue);
			keys.add(a);

		}

		long start = System.currentTimeMillis();

		Integer[] arr = keys.toArray(new Integer[0]);
		for (Integer key : arr) {
			tree.get(key);
		}
		System.out.println(System.currentTimeMillis() - start);

	}

	@Test
	public void testRBTreePerformance() {

		TreeMap<Integer, String> tree = new TreeMap<Integer, String>();
		List<Integer> keys = new ArrayList<Integer>(len);

		for (int j = 0; j < len; j++) {
			String dummyValue = "";
			int a = getRamdomInt();
			tree.put(a, dummyValue);
			keys.add(a);

		}

		long start = System.currentTimeMillis();

		Integer[] arr = keys.toArray(new Integer[0]);
		for (Integer key : arr) {
			tree.get(key);
		}
		System.out.println(System.currentTimeMillis() - start);

	}
}
