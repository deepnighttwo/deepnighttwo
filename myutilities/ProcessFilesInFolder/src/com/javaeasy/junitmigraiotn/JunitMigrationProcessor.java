package com.javaeasy.junitmigraiotn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import com.javaeasy.fileiterator.ProcessFileSysItemIF;

public class JunitMigrationProcessor implements ProcessFileSysItemIF {

	PrintWriter errLog;

	@Override
	public void initProcessor(File root) {
		File f = new File("D:\\errjunitmigration.log");
		try {
			f.delete();
			f.createNewFile();
			errLog = new PrintWriter(new FileWriter(f));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String[] getProcessFileExtentions() {
		return new String[] { "java" };
	}

	@Override
	public String[] getProcessFileNames() {
		return null;
	}

	@Override
	public void preProcessFolder(File folder) {
		log("Processing folder " + folder.getName() + "...");
	}

	@Override
	public void postProcessFolder(File folder) {
		log("Folder " + folder.getName() + " processed done");

	}

	@Override
	public void preProcessFile(File file) {
		log("Processing file " + file.getName());
	}

	@Override
	public void processFile(File file) {
		String full = file.getPath();
		if (full.contains(".svn")) {
			return;
		}

		File tempFile = new File(file.getPath() + ".tmp");
		try {
			tempFile.createNewFile();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream(tempFile), "UTF-8"));
			String line = null;
			String parentClass = "";
			while ((line = br.readLine()) != null) {
				if (line.contains("extends TestCase")) {
					line = line.replace("extends TestCase", "");
				} else {
					if (line.contains("extends")) {
						String[] words = line.split(" ");
						for (int i = 0; i < words.length; i++) {
							if ("extends".equals(words[i])) {
								parentClass = words[i + 1];
							}
						}
					}
				}
				if (line.contains("super.setUp")) {
					line = line.replace("super.setUp", parentClass + ".setUp");
				}

				if (line.contains("void test")
						&& (!line.contains("private void test"))) {
					pw.write("	@Test\r\n");
				}
				if (line.contains("void setUp")) {
					pw.write("	@BeforeClass\r\n");
					int voidIdx = line.indexOf("void");
					line = line.substring(0, voidIdx) + " static "
							+ line.substring(voidIdx);
				}
				if (line.contains("void tearDown")) {
					pw.write("	@AfterClass\r\n");
					int voidIdx = line.indexOf("void");
					line = line.substring(0, voidIdx) + " static "
							+ line.substring(voidIdx);
				}
				pw.write(line + "\r\n");
			}
			pw.flush();
			br.close();
			pw.close();

		} catch (Throwable e) {
			errLog.write("Exception while processing " + file.getName() + " "
					+ e.getLocalizedMessage() + "\r\n");

			e.printStackTrace();
		}

		boolean deleteRet = file.delete();
		if (deleteRet == false) {
			errLog.write("Failed to delete " + file.getName() + "\r\n");
		}

		boolean renameRet = tempFile.renameTo(file);
		if (renameRet == false) {
			errLog.write("Failed to rename to " + file.getName() + "\r\n");
		}
	}

	private static void log(Object obj) {
		System.out.println(obj);
	}

	@Override
	public void postProcessFile(File file) {

	}

	@Override
	public void done() {
		errLog.flush();
		errLog.close();
	}

}
