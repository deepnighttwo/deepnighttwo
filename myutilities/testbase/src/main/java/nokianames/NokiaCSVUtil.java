package nokianames;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class NokiaCSVUtil {

	private static final int MING = 1;

	private static final int XING = 3;

	private static final int MOBILE = 13;

	private static final int COMMON_MOBILE = 14;

	private static final String file = "C:\\Documents and Settings\\mzang\\Desktop\\Copy of 通讯录备份.csv";

	public static void printColumns() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), Charset.forName("UTF-16LE")));
		String line = null;
		List<String[]> allList = new ArrayList<String[]>();
		while ((line = br.readLine()) != null) {
			String[] row = line.split(",");
			allList.add(row);
		}
		br.close();
		int columnCount = allList.get(0).length;
		for (int i = 0; i < columnCount; i++) {
			System.out.print(i + "\t");
			for (int j = 0; j < allList.size(); j++) {
				System.out.print(allList.get(j)[i] + "\t");
			}
			System.out.println();
		}
	}

	private static void updateAddressBook() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), Charset.forName("UTF-16LE")));
		String line = null;
		List<String[]> allList = new ArrayList<String[]>();
		while ((line = br.readLine()) != null) {
			String[] row = line.split(",");
			String[] mings = row[XING].substring(1, row[XING].length() - 1)
					.split(" ");
			if (mings.length == 2) {
				row[MING] = mings[1];
				row[XING] = mings[0];
			}
			allList.add(row);
		}
		br.close();
		int columnCount = allList.get(0).length;
		for (int i = 0; i < columnCount; i++) {
			System.out.print(i + "\t");
			for (int j = 0; j < allList.size(); j++) {
				System.out.print(allList.get(j)[i] + "\t");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		try {
			updateAddressBook();
			// printColumns();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
