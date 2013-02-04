package com.javaeasy.fileiterator;

import java.io.File;

public interface ProcessFileSysItemIF {

	void initProcessor(File root);

	/**
	 * null - Please ignore this condition. No file is allowed by this
	 * condition; "*" - Please allow all file. Any condition is * will disable
	 * other conditions; Value - Please compare this condition
	 * 
	 * @return
	 */
	String[] getProcessFileExtentions();

	String[] getProcessFileNames();

	void preProcessFolder(File folder);

	void postProcessFolder(File folder);

	void preProcessFile(File file);

	void processFile(File file);

	void postProcessFile(File file);

	void done();

}
