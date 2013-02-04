package javarefcheck;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TestPhantomReference {

	/**
	 * @param args
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws SecurityException,
			NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException, InterruptedException {
		List l = new ArrayList();
		ReferenceQueue<Big> rq = new ReferenceQueue<Big>();

		int size = 1;
		for (int i = 0; i < size; i++) {
			Big b = new Big();
			PhantomReference<Big> pf = new PhantomReference<Big>(b, rq);
			b = null;
			// need to make sure the PhantomReference is not GCed. Otherwise, it
			// will never be enqueued.
			l.add(pf);
		}
		List<Big> l2 = new ArrayList<Big>();

		for (int i = 0; i < size * 10; i++) {
			Big b = new Big();
			l2.add(b);
		}

		// pf = null;
		System.gc();
		for (int i = 0; i < size; i++) {
			Reference<? extends Big> pf2 = rq.remove();
			if (pf2 != null) {
				System.gc();
				Thread.yield();
				System.gc();
				// pf2.clear();
				System.gc();
				System.gc();
				Thread.yield();
				System.gc();
				System.gc();
				Field fs = Reference.class.getDeclaredField("referent");
				fs.setAccessible(true);
				Big obj = (Big) fs.get(pf2);
				System.out.println(obj);
				obj.initArray();
				obj.checksum();
			}
			System.gc();
		}

//		for (Big bbb : l2) {
//			bbb.checksum();
//		}
	}
}

class Big {
	int[][] data = new int[1024][1024];

	public void initArray() {
		System.out.println("initialing");
		for (int i = 0; i < 1024; i++) {
			for (int j = 0; j < 1024; j++) {
				data[i][j] = i + j;
			}
		}
		System.out.println("initialed");

	}

	public void checksum() {
		int sum = 0;
		System.out.println("suming");
		for (int i = 0; i < 1024; i++) {
			for (int j = 0; j < 1024; j++) {
				sum += data[i][j];
			}
		}
		System.out.println("sum:" + sum);
	}

	protected void finalize() {
		System.out.println("Big Finalized.");
	}
}
