package test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileLine {

	public static void main(String[] args) throws IOException {
		File f = new File("C:\\ttt.txt");
		if (f.exists() == false || f.isFile() == false) {
			f.createNewFile();
		}
		PrintWriter pw = new PrintWriter(new FileWriter(f));
		pw.write("rn: AAA" + "\r\n" + "BBB");
		pw.write("r: AAA" + "\r" + "BBB");
		pw.write("n: AAA" + "\n" + "BBB");
		pw.flush();

		String str = (int) ('\r') + "\t" + (int) ('\n');
		System.out.println(str);
		pw.close();
	}

}
