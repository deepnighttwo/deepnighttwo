package com.javaeasy.changeimport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.javaeasy.fileiterator.ProcessFileSysItemIF;

public class ImportChangeProcessorMFP implements ProcessFileSysItemIF {

	boolean processFilesInCurrentFolder = true;

	private static final String MIDDLE_PROC = "changeimport";

	private static final String NOT_TO_PROCESS = ".svn";

	private int changedImport = 0;

	private int changedImportAll = 0;

	private List<File> errorFiles = new ArrayList<File>();

	private static String[] ignoredPackageNames = { "com.ebay.tools.soa.v3",
			"com.ebay.soaframework.eclipse", "com.ebay.tools.soa.ebox",
			"com.ebay.tools.soa.errorlibrary", "com.ebay.tools.soa.external",
			"com.ebay.tools.soa.marketplace" };

	private static boolean shouldIgnore(String line) {
		for (String pkg : ignoredPackageNames) {
			if (line.contains(pkg)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void initProcessor(File root) {

	}

	@Override
	public String[] getProcessFileExtentions() {
		return null;
	}

	@Override
	public String[] getProcessFileNames() {
		return new String[] { "plugin.xml", "MANIFEST.MF", "fragment.xml" };
	}

	private static void log(String str) {
		System.out.println(str);
	}

	@Override
	public void preProcessFolder(File folder) {
		String fullPath = folder.getAbsolutePath();
		log("Processing folder :" + fullPath);

		if (fullPath.contains(NOT_TO_PROCESS)) {
			processFilesInCurrentFolder = false;
		} else {
			processFilesInCurrentFolder = true;
		}
	}

	@Override
	public void postProcessFolder(File folder) {
		log("Processed folder :" + folder);
	}

	@Override
	public void preProcessFile(File file) {
		log("Processing file: " + file);
	}

	@Override
	public void processFile(File file) {
		if (processFilesInCurrentFolder == false) {
			return;
		}
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			File newJavaFile = new File(file.getAbsolutePath() + MIDDLE_PROC);
			if (newJavaFile.isFile() == true) {
				newJavaFile.delete();
			}
			boolean creation = newJavaFile.createNewFile();
			if (creation == false) {
				errorFiles.add(file);
				log("Unable to create file: " + newJavaFile);
			}

			PrintWriter writer = new PrintWriter(new FileWriter(newJavaFile));
			while ((line = reader.readLine()) != null) {
				if (shouldIgnore(line) == false) {
					// should do a replacement
					changedImport++;
					line = line.replace("com.ebay.tools.soa.",
							"org.ebayopensource.turmeric.eclipse.");
				}
				writer.write(line + "\r\n");
			}

			reader.close();
			writer.close();

			file.delete();
			newJavaFile.renameTo(file);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			errorFiles.add(file);
		} catch (IOException e) {
			e.printStackTrace();
			errorFiles.add(file);
		}

	}

	@Override
	public void postProcessFile(File file) {
		if (processFilesInCurrentFolder == false) {
			return;
		}
		log("Processed file: " + file + ". Changed import: " + changedImport);
		changedImportAll += changedImport;
		changedImport = 0;
	}

	@Override
	public void done() {
		log("Processe finished. Changed import :" + changedImportAll);
		log("Error Files:");
		for (File errFile : errorFiles) {
			log(errFile.toString());
		}
	}

}
