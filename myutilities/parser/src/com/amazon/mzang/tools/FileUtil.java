package com.amazon.mzang.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.amazon.mzang.tools.old.ParseTableFile;

public class FileUtil {

	public static List<String> readFileAsLines(InputStream stream)
			throws IOException {
		List<String> ret = new LinkedList<String>();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(stream));

		String line = null;
		while ((line = reader.readLine()) != null) {
			ret.add(line);
		}
		return ret;

	}

	public static List<String> readFileAsLines(String filename)
			throws IOException {
		return readFileAsLines(ParseTableFile.class
				.getResourceAsStream(filename));
	}

	public static List<String[]> readFileAsTable(String filename,
			String separator) throws IOException {
		return readFileAsTable(
				ParseTableFile.class.getResourceAsStream(filename), separator);
	}

	public static List<String[]> readFileAsTable(InputStream stream,
			String separator) throws IOException {
		List<String> lines = readFileAsLines(stream);
		List<String[]> ret = new LinkedList<String[]>();

		for (String line : lines) {
			String[] row = line.split(separator);
			ret.add(row);
		}
		return ret;
	}

	public static Map<String, String[]> readFileAsTableWithKey(String filename,
			String separator, int keyIndex) throws IOException {
		return readFileAsTableWithKey(
				ParseTableFile.class.getResourceAsStream(filename), separator,
				keyIndex);
	}

	public static Map<String, String[]> readFileAsTableWithKey(
			InputStream stream, String separator, int keyIndex)
			throws IOException {
		List<String> lines = readFileAsLines(stream);

		Map<String, String[]> table = new LinkedHashMap<String, String[]>();

		for (String line : lines) {
			String[] row = line.split(separator);
			table.put(row[keyIndex], row);
		}
		return table;
	}

}
