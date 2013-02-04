package com.javaeasy.fileiterator;

import java.io.File;
import java.io.FileFilter;

public class FileSysIteratorUtils {

	public static final String ALLOW_ALL_FILE = "*";

	public static File[] findAllFoldersInFolder(File folder) {
		File[] allFolders = folder.listFiles(new FileFilter() {

			public boolean accept(File file) {
				if (file.exists() && file.isDirectory()) {
					return true;
				}
				return false;
			}

		});
		return allFolders;
	}

	public static File[] findAllFilesInFolder(File folder,
			String[] fileExtensions, String[] fileNames) {

		final String[] fileExtensionsInner = fileExtensions;

		final String[] fileNamesInner = fileNames;

		File[] javaFiles = folder.listFiles(new FileFilter() {

			public boolean accept(File pathname) {

				if (ALLOW_ALL_FILE.equals(fileExtensionsInner)
						|| ALLOW_ALL_FILE.equals(fileNamesInner)) {
					return true;
				}

				if (pathname.exists() && pathname.isFile()) {
					String fileName = pathname.getName().toLowerCase();
					if (fileNamesInner != null) {
						for (int i = 0; i < fileNamesInner.length; i++) {
							if (fileName.equalsIgnoreCase(fileNamesInner[i])) {
								return true;
							}
						}
					}
					if (fileExtensionsInner != null) {
						int lastDot = fileName.lastIndexOf('.') + 1;
						String fileExt = null;
						if (lastDot <= fileName.length()) {
							fileExt = fileName.substring(lastDot);
						}
						for (int i = 0; i < fileExtensionsInner.length; i++) {
							if (fileExt
									.equalsIgnoreCase(fileExtensionsInner[i])) {
								return true;
							}
						}
					}
				}
				return false;
			}
		});
		return javaFiles;
	}

}
