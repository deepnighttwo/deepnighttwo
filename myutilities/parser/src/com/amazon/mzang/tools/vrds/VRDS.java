package com.amazon.mzang.tools.vrds;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.zip.GZIPInputStream;

public class VRDS {

	/**
	 * @param args
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new GZIPInputStream(
								new FileInputStream(
										"C:\\Users\\mengzang\\Desktop\\VRDS2CSV-transformed.2012-11-10_to_2012-11-17.txt.gz"))));
		String line = null;
		HashSet<String> cur = new HashSet<String>();
		int c = 0;
		while ((line = br.readLine()) != null) {
			c++;
			String[] row = line.split(",");
			// if (cur.contains(row[30])) {
			// continue;
			// }

			if (row[30].equals("null") == true) {
				continue;
			}
			cur.add(row[30]);
			System.out.println(row[0] + " - " + row[1] + " - " + row[4] + " - "
					+ row[16] + " - " + row[30] + " - " + row[31] + " - "
					+ row[32] + " - " + row[33] + " - " + row[34] + " - " + row[13] + " - " + row[14] + " - "+ row[7] + " - ");
		}
		System.out.println("finished line " + c);
	}

}
