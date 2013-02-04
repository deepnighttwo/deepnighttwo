package com.amazon.mzang.tools.old;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import com.amazon.mzang.tools.MySQLUtil;

public class ParseTableFile {

	public static void main(String[] args) throws IOException, SQLException {
		genSQL();
	}

	private static String getPercent(String percent) {
		percent = percent.trim();
		if (percent.charAt(percent.length() - 1) == '%') {
			return percent.substring(0, percent.lastIndexOf('%'));
		}
		return percent;
	}

	public static void genSQLGLMarkdown0517() {
		try {

			List<String[]> markdown = parseRowData("mdrate", '\t');
			String templateSQL = "INSERT INTO `markdown_policies` (`marketplace_id`, `iog`, `gl`, `additional_attributes`, `priority`, `price_type`, `value`, `create_date`, `created_by`, `last_update_date`, `last_updated_by`) VALUES (3240, %s, 194, '\"category\":\"%s\"', 0, 'PRCNTCOST', %s, now(), 'nobody', now(), 'nobody');";
			// IOG, GL, category, percent

			for (String[] row : markdown) {
				String category = row[0];
				String percent = getPercent(row[2]);
				log(String.format(templateSQL, "70", category, percent));
				log(String.format(templateSQL, "71", category, percent));
				log("");

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void genSQL() throws SQLException {

		try {
			Map<String, String[]> delta = parseTableData("datainput");

			ResultSet ex = MySQLUtil.executeSQL(MySQLUtil.US_DB_Serv,
					"vendor_flex", "select * from LIQUIDATION_POLICY");

			Set<String> iog1 = new TreeSet<String>();

			Set<String> iog2 = new TreeSet<String>();

			while (ex.next()) {
				String iog = ex.getString(1);
				String gl = ex.getString(2);
				if (iog.equals("1")) {
					iog1.add(gl);
				} else if (iog.equals("2")) {
					iog2.add(gl);
				}
			}

			for (Entry<String, String[]> e : delta.entrySet()) {
				String gl = e.getKey();
				String percent = getPercent(e.getValue()[1]);

				if (iog1.contains(gl)) {
					log("update LIQUIDATION_POLICY set PRICE_OR_PERCENT="
							+ percent + " where IOG=1 and GL='" + gl + "';");
				} else {
					log("INSERT INTO `LIQUIDATION_POLICY` (`IOG`, `GL`, `ORG`, `VENDOR`,`PRICE_TYPE`, `PRICE_OR_PERCENT`, `PER_ORDER_CAP`) VALUES (1, "
							+ gl
							+ ", 'USAmazon', 'GENC1', 'PRCNTCOST', "
							+ percent + ", 9999);");
				}

				if (iog2.contains(gl)) {
					log("update LIQUIDATION_POLICY set PRICE_OR_PERCENT="
							+ percent + " where IOG=2 and GL='" + gl + "';");
				} else {
					log("INSERT INTO `LIQUIDATION_POLICY` (`IOG`, `GL`, `ORG`, `VENDOR`,`PRICE_TYPE`, `PRICE_OR_PERCENT`, `PER_ORDER_CAP`) VALUES (2, "
							+ gl
							+ ", 'USAmazon', 'GENC1', 'PRCNTCOST', "
							+ percent + ", 9999);");
				}
				log("");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void diff() {
		try {
			Map<String, String[]> delta = parseTableData("datainput");
			log("New row count:" + delta.size());

			Map<String, String[]> org = parseTableData("compare_org.txt");
			log("Original row count:" + org.size());

			Set<String> ds = new TreeSet<String>();
			ds.addAll(delta.keySet());

			Set<String> os = new TreeSet<String>();
			os.addAll(org.keySet());

			ds.removeAll(org.keySet());
			log("New GLs(total " + ds.size() + "):" + ds);

			os.removeAll(delta.keySet());
			log("Missing GLs(total " + os.size() + "):" + os);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void log(Object obj) {
		System.out.println(obj.toString());
	}

	private static void lognc(Object obj) {
		System.out.print(obj.toString());
	}

	public static Map<String, String[]> parseTableData(String filename)
			throws IOException {
		return parseTableData(filename, '\t');
	}

	public static Map<String, String[]> parseTableData(String filename,
			char separator) throws IOException {
		Map<String, String[]> ret = new LinkedHashMap<String, String[]>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				ParseTableFile.class.getResourceAsStream(filename)));

		String line = null;
		while ((line = reader.readLine()) != null) {
			String[] row = line.split(String.valueOf(separator));
			ret.put(row[0], row);
		}

		return ret;
	}

	public static List<String[]> parseRowData(String filename, char separator)
			throws IOException {
		List<String[]> ret = new ArrayList<String[]>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				ParseTableFile.class.getResourceAsStream(filename)));

		String line = null;
		while ((line = reader.readLine()) != null) {
			String[] row = line.split(String.valueOf(separator));
			ret.add(row);
		}

		return ret;
	}

	public static void genSQLGLMarkdown() {
		try {

			List<String[]> markdown = parseRowData("glmarkdown-cn", '\t');
			String templateSQL = "INSERT INTO `markdown_policies` (`marketplace_id`, `iog`, `gl`, `additional_attributes`, `priority`, `price_type`, `value`, `create_date`, `created_by`, `last_update_date`, `last_updated_by`) VALUES (3240, %s, %s, '\"category\":\"%s\"', 0, 'PRCNTCOST', %s, now(), 'nobody', now(), 'nobody');";
			// IOG, GL, category, percent

			for (String[] row : markdown) {
				String GL = row[0];
				String category = row[1];
				String percent = getPercent(row[2]);
				log(String.format(templateSQL, "70", GL, category, percent));
				log(String.format(templateSQL, "71", GL, category, percent));
				log("");

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static Map<Integer, Set<Integer>> parseIOG2GL(String filename)
			throws IOException {
		Map<Integer, Set<Integer>> ret = new LinkedHashMap<Integer, Set<Integer>>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				ParseTableFile.class.getResourceAsStream(filename)));
		char separator = '\t';
		String line = null;
		while ((line = reader.readLine()) != null) {
			String[] row = line.split(String.valueOf(separator));
			Integer IOG;
			Integer GL;

			if (row.length < 2) {
				continue;
			}
			try {
				IOG = Integer.parseInt(row[0]);
			} catch (Exception ex) {
				log("ignored IOG:" + row[0]);
				continue;
			}

			try {
				GL = Integer.parseInt(row[1]);
			} catch (Exception ex) {
				log("ignored GL:" + row[1]);
				continue;
			}

			Set<Integer> gls = ret.get(IOG);
			if (gls == null) {
				gls = new TreeSet<Integer>();
				ret.put(IOG, gls);
			}
			gls.add(GL);
		}

		return ret;
	}

	public static Map<Integer, Set<Integer>> getNotEffectedOnes(String filename)
			throws IOException {
		Map<Integer, Set<Integer>> ret = new LinkedHashMap<Integer, Set<Integer>>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				ParseTableFile.class.getResourceAsStream(filename)));
		String line = null;
		while ((line = reader.readLine()) != null) {
			if (line.trim().length() == 0) {
				continue;
			}
			line = line.replace('(', ' ');
			line = line.replace(')', ' ');
			line = line.replace('\"', ' ');
			line = line.replace(';', ' ');
			String[] i2g = line.split("=");
			Integer iog = Integer.parseInt(i2g[0].trim());
			String[] gls = i2g[1].split(",");
			Set<Integer> glsSet = new TreeSet<Integer>();
			ret.put(iog, glsSet);
			for (String glstr : gls) {
				Integer gl1 = Integer.parseInt(glstr.trim());
				glsSet.add(gl1);
			}
		}
		return ret;

	}

	public static void getEffectedGLs() throws IOException {
		Map<Integer, Set<Integer>> i2g = parseIOG2GL("allioggls");
		Map<Integer, Set<Integer>> nei2g = getNotEffectedOnes("noteffected");
		for (Entry<Integer, Set<Integer>> e : i2g.entrySet()) {
			Object IOG = e.getKey();
			Set<Integer> notEffectedGls = nei2g.get(IOG);
			Set<Integer> notEffectedGlsCopy = new TreeSet<Integer>();
			notEffectedGlsCopy.addAll(notEffectedGls);
			Set<Integer> allGlsInIOG = e.getValue();
			if (notEffectedGls != null) {
				notEffectedGlsCopy.removeAll(allGlsInIOG);
				if (notEffectedGlsCopy.size() > 0) {
					log("Missing GLs:" + notEffectedGlsCopy);
				}
				lognc(IOG + ": Effected GLs in IOG ");
				allGlsInIOG.removeAll(notEffectedGls);
				log(allGlsInIOG);
			} else {
				lognc(IOG + ": Effected GLs in IOG ");
				log(allGlsInIOG);
			}
			log("");
		}
	}
}
