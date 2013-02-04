package com.amazon.mzang.tools.old;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.amazon.mzang.tools.FileUtil;

public class GenSQLUpdateHoldingCost {

	public static void main(String[] args) throws IOException {
		loopSQLGenOriginalBase();
	}

	private static void loopSQLGenOriginalBase() throws IOException {
		List<String[]> table = FileUtil.readFileAsTable("originalfcdata", "\t");
		for (String[] row : table) {
			String id = row[0].trim();
			String costStr = row[10].trim();
			if("NULL".equalsIgnoreCase(costStr)){
				continue;
			}
			double costperQt = Double.valueOf(costStr) * 0.9;
			log(String
					.format("update VENDOR_FCS set HOLDINGCOST_EST=%s where ID='%s';",
							costperQt, id));
		}
	}

	private static void loopSQLGen() throws IOException {
		List<String[]> table = FileUtil.readFileAsTable("hc.txt", "\t");
		StringBuilder whids = new StringBuilder();
		Set<String> check = new TreeSet<String>();
		int a = 0;
		for (String[] row : table) {
			String whid = row[0].trim();
			String costStr = row[4].trim();
			double costperQt = Double.valueOf(costStr) * 0.9 / (12 * 12 * 12);
			log(String
					.format("update VENDOR_FCS set HOLDINGCOST_EST=%s where WAREHOUSE_ID='%s';",
							costperQt, whid));
			whids.append("'" + whid + "', ");
			a++;
			if (check.add(whid) == false) {
				log(whid);
			}
		}
		log(whids);
		log(a);
		log(check.size());
	}

	private static void log(Object obj) {
		System.out.println(obj.toString());
	}

	// private static void lognc(Object obj) {
	// System.out.print(obj.toString());
	// }

}
