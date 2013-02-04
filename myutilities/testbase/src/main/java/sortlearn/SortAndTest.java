package sortlearn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class SortAndTest {

	private static final int LEN = 1000000;

	@SuppressWarnings("rawtypes")
	private static Comparable[] ELEMENTS_ORIGINAL = new Comparable[LEN];
	
	@SuppressWarnings("rawtypes")
	private static List<Comparable> COMPARE = new ArrayList<Comparable>(LEN);

	@SuppressWarnings("rawtypes")
	private static Comparable[] ELE_TO_SORT = new Comparable[LEN];

	@SuppressWarnings("unchecked")
	@BeforeClass
	public static void createArray() {
		for (int i = 0; i < LEN; i++) {
			ELEMENTS_ORIGINAL[i] = new Integer((int) (Math.random() * 100000));
			COMPARE.add(ELEMENTS_ORIGINAL[i]);
		}
		Collections.sort(COMPARE);
	}

	@Before
	public void copyArray() {
		System.arraycopy(ELEMENTS_ORIGINAL, 0, ELE_TO_SORT, 0, LEN);
	}

	@After
	public void checkAndCleanArray() {
		isSortedAndTheSame();
		for (int i = 0; i < LEN; i++) {
			ELE_TO_SORT[i] = null;
		}
	}

	@AfterClass
	public static void cleanOriginalArray() {
		for (int i = 0; i < LEN; i++) {
			ELEMENTS_ORIGINAL[i] = null;
		}
	}

	@SuppressWarnings("unchecked")
	public void isSortedAndTheSame() {
		for (int i = 0; i < LEN; i++) {
			Assert.assertTrue(COMPARE.get(i).compareTo(ELE_TO_SORT[i]) == 0);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void insertSort(Comparable[] elements) {
		for (int i = 1; i < elements.length; i++) {
			Comparable current = elements[i];
			int j = i;
			while (j > 0 && elements[j - 1].compareTo(current) > 0) {
				elements[j] = elements[j - 1];
				j--;
			}
			if (j != i) {
				elements[j] = current;
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void shellSort(Comparable[] elements) {
		int len = elements.length;
		// initial step value is (array length)/2. Then change it to half of
		// itself
		int step = len / 2;
		// keep going while step is bigger than 0
		while (step > 0) {
			for (int i = step; i < len; i++) {
				int current = i;
				int previous = -1;
				Comparable currValue = elements[current];
				while (((previous = current - step) >= 0)
						&& elements[previous].compareTo(currValue) > 0) {
					elements[current] = elements[previous];
					current = previous;
					continue;
				}

				if (current != i) {
					elements[current] = currValue;
				}

			}
			step = step / 2;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void quickSort(Comparable[] elements, int start, int end) {
		if (end - start <= 0) {
			return;
		}

		Comparable current = elements[start];
		int currPos = start;

		for (int i = start + 1; i <= end; i++) {
			Comparable element = elements[i];
			if (element.compareTo(current) < 0) {
				elements[currPos] = element;
				currPos++;
				elements[i] = elements[currPos];
			}
		}

		if (currPos != start) {
			elements[currPos] = current;
		}

		quickSort(elements, start, currPos - 1);
		quickSort(elements, currPos + 1, end);

	}

	@Test
	@Ignore("This case is really very slow. It is not suggested to open it if the array is larger than 100000")
	public void testInnerSort() {
		insertSort(ELE_TO_SORT);
	}

	@Test
	public void testShellSort() {
		shellSort(ELE_TO_SORT);
	}

	@Test
	public void testQuickSort() {
		quickSort(ELE_TO_SORT, 0, LEN - 1);
	}

}
