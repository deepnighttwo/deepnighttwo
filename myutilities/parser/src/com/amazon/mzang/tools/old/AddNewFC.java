package com.amazon.mzang.tools.old;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.amazon.mzang.tools.FileUtil;

public class AddNewFC {

	public static void main(String[] args) throws IOException {
		getGLs();
	}

	public static void getGLs() throws IOException {
		List<String[]> line = FileUtil.readFileAsTable("gls.txt", " ");
		List<Integer> ggg = new ArrayList<Integer>();
		for (String[] fc : line) {
			String gl = fc[0].trim();
			Integer v = Integer.parseInt(gl);
			ggg.add(v);
		}
		Collections.sort(ggg);
		
		log(ggg.size());

		Set<Integer> glMerge = new TreeSet<Integer>();
		glMerge.addAll(ggg);
		log(glMerge.size());

		String[] allGLs = new String[] { "0", "14", "15", "20", "21", "22",
				"23", "27", "44", "50", "53", "60", "63", "65", "74", "75",
				"79", "86", "107", "111", "114", "121", "123", "125", "129",
				"136", "147", "153", "158", "160", "171", "180", "193", "194",
				"195", "196", "197", "198", "199", "200 ", "201", "219", "226",
				"228", "229", "234", "236", "241", "246", "251", "256", "258",
				"259", "260", "261", "262", "263", "264", "265", "266", "267",
				"279", "293", "297", "298", "304", "307", "309", "311", "313",
				"316", "318", "325", "327", "328", "334", "336", "340", "349",
				"350", "351", "353", "354", "355", "356", "360", "362", "364",
				"366", "367", "370", "392", "394", "395", "396", "397", "400",
				"402", "405", "406", "407", "408", "409", "410", "411" };
		for (String str : allGLs) {
			Integer v = Integer.valueOf(str.trim());
			glMerge.add(v);
		}
		
		log(glMerge.size());
		log("");
		for (Integer i : glMerge) {
			logL("\"" + i + "\",");
		}
		log("");

	}

	private static void logL(Object obj) {
		System.out.print(obj.toString());
	}

	private static void log(Object obj) {
		System.out.println(obj.toString());
	}

	public static void s() throws IOException {
		List<String> line = FileUtil.readFileAsLines("sssssss");
		log("in");
		for (String str : line) {
			if (str.trim().length() == 0) {
				continue;
			}
			log("'" + str + "',");
		}
	}

	public static void genAddFCSQL() throws IOException {
		List<String[]> newfcs = FileUtil.readFileAsTable("newfc", "\t");
		for (String[] fc : newfcs) {
			StringBuilder sql = new StringBuilder(
					"INSERT INTO vendor_flex.VENDOR_FCS VALUES (");
			for (int i = 0; i < fc.length; i++) {
				String cell = fc[i].trim();
				if ("NULL".equalsIgnoreCase(cell)) {
					sql.append("NULL");
				} else {
					if (i == 2 || i == 3 || i == 5 | i == 6 || i == 10) {
						sql.append(cell);
					} else if (i == 4 || i == 14) {
						sql.append("b'" + cell + "'");
					} else {
						sql.append("'" + cell + "'");
					}
				}
				sql.append(",");
			}
			sql.deleteCharAt(sql.length() - 1);
			sql.append(");");
			log(sql);
		}

	}
}
