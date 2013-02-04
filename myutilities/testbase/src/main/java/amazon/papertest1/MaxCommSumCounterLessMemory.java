package amazon.papertest1;

public class MaxCommSumCounterLessMemory implements IMaxCommSumCounter {

	@Override
	public int countSum(int[] input) {

		if (input == null || input.length == 0) {
			return -1;
		}

		quickSort(input, 0, input.length);

		int maxTimes = 0;
		int number = 0;

		int cTimes = 1;
		int cNumber = input[0];
		for (int i = 1, len = input.length; i < len; i++) {
			int c = input[i];
			if (c == cNumber) {
				cTimes++;
				continue;
			}

			if ((cTimes > maxTimes) || (cNumber > number && cTimes == maxTimes)) {
				maxTimes = cTimes;
				number = cNumber;
				cTimes = 1;
				cNumber = c;
			}

		}
		return maxTimes * number;
	}

	/**
	 * quick sort from small to big
	 * 
	 * @param input
	 * @param start
	 * @param end
	 */
	private void quickSort(int[] input, int start, int end) {

		if (start >= end) {
			return;
		}

		int st = input[start];

		int p = start;

		for (int i = start + 1; i < end; i++) {
			if (st > input[i]) {
				input[p] = input[i];
				p++;
				input[i] = input[p];
			}
		}
		input[p] = st;
		quickSort(input, start, p - 1);
		quickSort(input, p + 1, end);
	}
}
