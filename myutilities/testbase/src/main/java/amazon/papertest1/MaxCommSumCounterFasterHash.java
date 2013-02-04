package amazon.papertest1;

import java.util.HashMap;

public class MaxCommSumCounterFasterHash implements IMaxCommSumCounter {

	private static final int BUCKET_DEFAULT = 999;

	public int countSum(int[] input) {

		if (input == null || input.length == 0) {
			return -1;
		}

		HashMap<Integer, IntHolder> map = new HashMap<Integer, IntHolder>(BUCKET_DEFAULT);

		int times = 0;
		int mostCommon = 0;
		for (int i = 0, len = input.length; i < len; i++) {
			int c = input[i];
			IntHolder ci = map.get(c);
			if (ci == null) {
				ci = new IntHolder();
				map.put(c, ci);
			} else {
				ci.plusOne();
			}

			if ((ci.value > times)) {
				times = ci.value;
				mostCommon = c;
			}
		}
		return times * mostCommon;
	}

	private static class IntHolder {

		int value = 1;

		public void plusOne() {
			value++;
		}

	}
}
