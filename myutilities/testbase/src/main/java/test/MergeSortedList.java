package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class MergeSortedList {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Comparable> mergeList(List<Comparable> a,
			List<Comparable> b) {

		List<Comparable> ret = new ArrayList<Comparable>(a.size() + b.size());

		if (a.size() == 0) {
			return b;
		}

		if (b.size() == 0) {
			return a;
		}

		Iterator<Comparable> ia = a.iterator();
		Iterator<Comparable> ib = b.iterator();

		ia.hasNext();
		ib.hasNext();
		Comparable ac = ia.next();
		Comparable bc = ib.next();

		while (true) {
			if (ac == null) {
				ret.add(bc);
				addRest(ib, ret);
				return ret;
			}
			if (bc == null) {
				ret.add(ac);
				addRest(ia, ret);
				return ret;
			}
			if (ac.compareTo(bc) > 0) {
				ret.add(bc);
				if (ib.hasNext()) {
					bc = ib.next();
				} else {
					bc = null;
				}
			} else {
				ret.add(ac);
				if (ia.hasNext()) {
					ac = ia.next();
				} else {
					ac = null;
				}
			}

		}

	}

	@SuppressWarnings("rawtypes")
	private static void addRest(Iterator<Comparable> ia, List<Comparable> ret) {
		while (ia.hasNext()) {
			ret.add(ia.next());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testEmptyA() {
		List<Comparable> a = listGen(0);
		List<Comparable> b = listGen(5000);

		List<Comparable> ret = mergeList(a, b);

		Assert.assertEquals(a.size() + b.size(), ret.size());

		Comparable small = Integer.MIN_VALUE;
		for (Comparable c : ret) {
			Assert.assertTrue(small.compareTo(c) <= 0);
			small = c;
		}

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testEmptyB() {
		List<Comparable> a = listGen(1000);
		List<Comparable> b = listGen(0);

		List<Comparable> ret = mergeList(a, b);

		Assert.assertEquals(a.size() + b.size(), ret.size());

		Comparable small = Integer.MIN_VALUE;
		for (Comparable c : ret) {
			Assert.assertTrue(small.compareTo(c) <= 0);
			small = c;
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testIt() {
		List<Comparable> a = listGen(1000);
		List<Comparable> b = listGen(5000);

		List<Comparable> ret = mergeList(a, b);

		Assert.assertEquals(a.size() + b.size(), ret.size());

		Comparable small = Integer.MIN_VALUE;
		for (Comparable c : ret) {
			Assert.assertTrue(small.compareTo(c) <= 0);
			small = c;
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Comparable> listGen(final int size) {
		List<Comparable> ret = new ArrayList<Comparable>(size);

		for (int i = 0; i < size; i++) {
			ret.add(new Integer((int) (Math.random() * size * 100000)));
		}

		Collections.sort(ret);

		return ret;
	}

}
