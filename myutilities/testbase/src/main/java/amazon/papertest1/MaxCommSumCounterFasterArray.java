package amazon.papertest1;

public class MaxCommSumCounterFasterArray implements IMaxCommSumCounter {

	private static final int MAX = 999;

	public int countSum(int[] input) {

		if (input == null || input.length == 0) {
			return -1;
		}

		int[] arr = new int[MAX];

		int bucket = 0;
		for (int i = 0, len = input.length; i < len; i++) {
			int c = input[i];
			if (c >= MAX) {
				throw new RuntimeException("Input element is bigger than max.");
			}
			arr[c]++;
			if ((arr[c] > arr[bucket]) || (c > bucket && arr[c] == arr[bucket])) {
				bucket = c;
			}
		}
		return bucket * arr[bucket];
	}
}
