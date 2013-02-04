package com.amazon.mzang.tools.old;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.amazon.mzang.tools.FileUtil;

public class HoldingCostCompare {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		loopSQLGenOriginalBase();
	}

	private static void loopSQLGenOriginalBase() throws IOException {
		Map<String, String[]> fcs = FileUtil.readFileAsTableWithKey(
				"fcs_data.txt", "\t", 1);
		Map<String, String[]> hcNew = FileUtil.readFileAsTableWithKey("hc.txt",
				"\t", 0);

		System.out.println((String.format("%s, %s, %s, %s, %s, %s", "Building",
				"Original Value", "New Value ($/ft^3/Qtr)",
				"New Value ($/inch^3/Qtr)", "newValue - originalValue",
				"Change Percent(new-original)")));
		for (Entry<String, String[]> row : hcNew.entrySet()) {
			String building = row.getKey();
			if (fcs.get(building) == null) {
				System.out.println("ERROR " + row.getKey());
				continue;
			}
			String origValueStr = fcs.get(building)[10];
			double newValue = Double.valueOf(row.getValue()[4].trim())
					/ (12 * 12 * 12);
			if (origValueStr == null || origValueStr.trim().length() == 0) {
				System.out.println((String.format("%s, %s, %s, %s, %s, %s",
						building, "NULL", row.getValue()[4], newValue,
						newValue, "N/A")));
				continue;
			}
			double originalValue = Double.valueOf(origValueStr);
			System.out.println((String.format("%s, %s, %s, %s, %s, %s",
					building, originalValue, row.getValue()[4], newValue,
					(newValue - originalValue),
					((newValue - originalValue) * 100 / originalValue))));
		}
	}

}
