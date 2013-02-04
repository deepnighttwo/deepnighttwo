package com.javaeasy.subtitleextractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.javaeasy.fileiterator.FileSysIterator;
import com.javaeasy.fileiterator.ProcessFileSysItemIF;

public class SubtitleExtractorMain implements ProcessFileSysItemIF {

	public static void main(String[] args) {

		List<ProcessFileSysItemIF> list = new ArrayList<ProcessFileSysItemIF>();
		list.add(new SubtitleExtractorMain());
		FileSysIterator.createInstance(list).runFileIterator();
	}

	int fileProcessedInFolder = 0;

	@Override
	public void initProcessor(File root) {

	}

	@Override
	public String[] getProcessFileExtentions() {
		return new String[] { "mkv" };
	}

	@Override
	public String[] getProcessFileNames() {
		return null;
	}

	@Override
	public void preProcessFolder(File folder) {
		fileProcessedInFolder = 0;
	}

	@Override
	public void postProcessFolder(File folder) {
		System.out.println(folder.getName() + ":" + fileProcessedInFolder);

	}

	@Override
	public void preProcessFile(File file) {

	}

	@Override
	public void processFile(File file) {
		String filePath = file.getAbsolutePath();
		String filename = filePath.substring(0, filePath.lastIndexOf('.'));
		try {
			Process process = Runtime.getRuntime().exec(
					"\"C:\\Program Files\\MKVToolNix\\mkvextract.exe\" --ui-language en tracks \""
							+ filePath + "\" 2:\"" + filename + ".chs.srt\" 3:\"" + filename
							+ ".eng.srt\"");
			InputStream is = process.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			while (br.readLine() != null) {

			}
			br.close();
			process.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}
		fileProcessedInFolder++;
	}

	@Override
	public void postProcessFile(File file) {

	}

	@Override
	public void done() {
		System.out.println("Finished");
	}

}
