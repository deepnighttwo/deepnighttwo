package com.amazon.mzang.tools.old;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.amazon.mzang.tools.FileUtil;

public class GLCheck {

	/**
	 * @param args
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		List<String[]> rows = FileUtil
				.readFileAsTable(
						new FileInputStream(
								"C:\\sbs\\UnhealthyAsinDetails-transformed.2012-05-15_to_2012-05-22.txt"),
						",");
		for (String[] row : rows) {
			if ("gl".equalsIgnoreCase(row[2])) {
				continue;
			}
			if ("325".equals(row[2].trim()) == false) {
				System.out.println(row[2]);
			}
		}
		System.out.println("finish!");
	}

}
