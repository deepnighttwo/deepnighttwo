package circylearray;

public class CircyleArray {

	public static void useArray(String[] args) {

		int n = 17;
		if (args.length == 1) {
			try {
				n = Integer.valueOf(args[0]);
			} catch (Throwable ex) {
				// ignore exceptions
			}
		}
		int circyle = (n + 1) / 2;
		int[][] array = new int[n][n];
		int value = 1;
		int indexN = n - 1;
		for (int c = 0; c < circyle; c++) {

			int x = c;
			int y = c;
			for (; x <= indexN - c; x++) {
				array[y][x] = value++;
			}

			x = indexN - c;
			y = c + 1;
			for (; y <= indexN - c; y++) {
				array[y][x] = value++;
			}

			x = indexN - c - 1;
			y = indexN - c;
			for (; x >= c; x--) {
				array[y][x] = value++;
			}

			x = c;
			y = indexN - c - 1;
			for (; y >= c + 1; y--) {
				array[y][x] = value++;
			}

		}

		for (int y = 0; y < n; y++) {
			for (int x = 0; x < n; x++) {
				System.out.print(array[y][x] + "\t");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		useArray(args);
	}

	public static void countIt(String[] args) {}
}
