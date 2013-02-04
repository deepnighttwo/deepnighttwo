package fasttest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetNumber {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			String line = reader.readLine();

			int number = 0;
			try {
				number = Integer.valueOf(line);
			} catch (Exception ex) {
				System.exit(0);
			}

			int loop = (number + 1) / 2;

			boolean found = false;

			for (int i = 1; i <= loop; i++) {
				int sum = 0;
				for (int j = i; j <= loop; j++) {
					sum += j;
					if (sum == number) {
						for (int p = i; p <= j; p++) {
							System.out.print(p + ", ");
						}
						System.out.println();
						found = true;
						break;
					} else if (sum > number) {
						break;
					}
				}
			}
			if (found == false) {
				System.out.println("NONE");
			}
		}
	}
}
