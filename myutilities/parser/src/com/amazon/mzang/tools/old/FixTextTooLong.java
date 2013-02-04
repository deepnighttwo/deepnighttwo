package com.amazon.mzang.tools.old;

import java.io.IOException;
import java.util.List;

import com.amazon.mzang.tools.FileUtil;

public class FixTextTooLong {

	public static void main(String... strings) throws IOException {
		String fileName = "BuyerUnhealthyExclusions.2012-07-08.tsv";
		List<String> lines = FileUtil.readFileAsLines(fileName);
		for (String line : lines) {
			String[] row = line.split("\t");
			if (row[6].length() >= 256) {
				System.out.println(row[6].length() + "\t" + row[6]);
			}

		}
	}
}
