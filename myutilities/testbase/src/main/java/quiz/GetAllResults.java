package quiz;

public class GetAllResults {

	public void getAllResultsBasedOnArray(int dui[][], int[] order, int[] result) {
		int interval = 1;
		int current = 0;
		while (current < order.length) {
			result[current] = interval;
			if (dui[order[current]][order[current + interval]] == order[current + 1]) {
				order[current] = order[current + 1];
			}
		}
	}
}
