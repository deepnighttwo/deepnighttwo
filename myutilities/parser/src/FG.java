import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

public class FG {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		DecimalFormat df = new DecimalFormat("B000000000");

		PrintWriter pw = new PrintWriter("/home/mengzang/ilb.txt");
		for (int i = 0; i < 1000005; i++) {
			String asin = df.format(i);
			pw.write(asin);
		}

	}

}
