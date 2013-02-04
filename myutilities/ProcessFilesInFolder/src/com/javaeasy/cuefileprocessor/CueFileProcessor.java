package com.javaeasy.cuefileprocessor;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.javaeasy.fileiterator.FileSysIteratorUtils;
import com.javaeasy.fileiterator.ProcessFileSysItemIF;

public class CueFileProcessor implements ProcessFileSysItemIF {

	public static CueFileProcessor createInstance() {
		return new CueFileProcessor();
	}

	private CueFileProcessor() {

	}

	public void initProcessor(File root) {
		Charset targetCharset = null;
		try {
			targetCharset = Charset.forName("UTF-8");
			targetEncoder = targetCharset.newEncoder();
		} catch (UnsupportedCharsetException ex) {
			ex.printStackTrace();
		}
	}

	public String[] getProcessFileExtentions() {
		return new String[] { "ape", "flac", "wav", "wv" };
	}

	@Override
	public String[] getProcessFileNames() {
		return null;
	}

	public void preProcessFolder(File folder) {

		if (findExistingCueFile(folder)) {
			return;
		}
		if (!hasMusicFiles(folder)) {
			return;
		}
		String cueFileName = folder.getName();
		m_curCueFile = new File(folder, cueFileName + CUE);

		try {
			m_curCueFile.createNewFile();
			cueFileCreated.add(m_curCueFile.getPath());

			m_curFileContent.append(CUE_TITLE.replace(PERFORMER, cueFileName)
					.replace(ALBUM, cueFileName));

		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		trackNo = 1;

		System.out.println("Processing Folder:" + folder.getPath());

	}

	public boolean findExistingCueFile(File folder) {
		File[] cueFile = FileSysIteratorUtils.findAllFilesInFolder(folder,
				new String[] { "cue" }, null);
		return cueFile != null && cueFile.length > 0;
	}

	public boolean hasMusicFiles(File folder) {
		File[] musicFile = FileSysIteratorUtils.findAllFilesInFolder(folder,
				getProcessFileExtentions(), null);
		return musicFile != null && musicFile.length > 0;
	}

	public void postProcessFolder(File folder) {

		if (m_curCueFile == null) {
			return;
		}
		try {
			DataOutputStream cueFileOutput = new DataOutputStream(
					new FileOutputStream(m_curCueFile));
			CharBuffer sourceBuf = CharBuffer.wrap(m_curFileContent);
			ByteBuffer targetBytesBuf = targetEncoder.encode(sourceBuf);
			cueFileOutput.write(BOM_FOR_UTF8, 0, 3);
			cueFileOutput.write(targetBytesBuf.array(), 0,
					targetBytesBuf.limit());
			cueFileOutput.flush();
			cueFileOutput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		m_curFileContent = new StringBuilder();
		m_curCueFile = null;

		System.out.println("Processed Folder:" + folder.getPath());
	}

	public void preProcessFile(File file) {
		System.out.println("Processing File:" + file.getPath());
	}

	public void processFile(File musicFile) {
		if (m_curCueFile == null) {
			return;
		}
		StringBuilder curItem = new StringBuilder();

		String fileName = musicFile.getName();
		String[] trackMsg = this.getTrackMsgFromFileName(fileName);

		curItem.append(MUSIC_ITEM.replace(SOURCE_FILE, fileName)
				.replace(TRACK_Number, trackMsg[0])
				.replace(TRACK_TITLE, trackMsg[1]));
		m_curFileContent.append(curItem);
	}

	private String[] getTrackMsgFromFileName(String fileName) {
		String[] trackMsg = new String[2];
		StringBuilder number = new StringBuilder();
		int curCharIdx = 0;
		char curChar = 0;
		while (Character.isDigit((curChar = fileName.charAt(curCharIdx)))) {
			number.append(curChar);
			curCharIdx++;
		}
		if (number.length() > 0) {
			trackMsg[0] = number.toString();
		} else {
			trackMsg[0] = String.valueOf(trackNo);
			trackNo++;
		}

		while (UNWANTED_CHARS_IN_TITLE.indexOf(fileName.charAt(curCharIdx)) != -1) {
			curCharIdx++;
		}

		int lastDot = fileName.lastIndexOf('.');

		trackMsg[1] = fileName.substring(curCharIdx, lastDot);

		return trackMsg;

	}

	public void postProcessFile(File file) {
		System.out.println("Processed File:" + file.getPath());
		processedFiles.add(file.getPath());
	}

	public void done() {
		int count = processedFiles.size();
		System.out.println("File Processed\t:" + count);
		for (Iterator<String> it = processedFiles.iterator(); it.hasNext();) {
			System.out.println(it.next());
		}

		count = cueFileCreated.size();
		System.out.println("Cue File Created\t:" + count);
		for (Iterator<String> it = cueFileCreated.iterator(); it.hasNext();) {
			System.out.println(it.next());
		}
	}

	private int trackNo = 1;

	private List<String> processedFiles = new ArrayList<String>();

	public static List<String> cueFileCreated = new ArrayList<String>();

	CharsetEncoder targetEncoder = null;

	private static final String UNWANTED_CHARS_IN_TITLE = " .-_()";

	private File m_curCueFile = null;
	private StringBuilder m_curFileContent = new StringBuilder();

	private static final byte[] BOM_FOR_UTF8 = new byte[] { (byte) 0xEF,
			(byte) 0xBB, (byte) 0xBF };

	private static final String CUE = ".cue";

	private static final String DUMMY = "**??";

	private static final String PERFORMER = DUMMY + "PERFORMER" + DUMMY;
	private static final String ALBUM = DUMMY + "ALBUM" + DUMMY;

	private static final String SOURCE_FILE = DUMMY + "SOURCEFILE" + DUMMY;
	private static final String TRACK_Number = DUMMY + "TRACKNO" + DUMMY;
	private static final String TRACK_TITLE = DUMMY + "TRACKTITLE" + DUMMY;

	private static final String CUE_TITLE = "PERFORMER \"" + PERFORMER
			+ "\"\nTITLE \"" + ALBUM + "\"" + "\n";
	private static final String MUSIC_ITEM = "FILE \"" + SOURCE_FILE
			+ "\" WAVE\n" + "  TRACK " + TRACK_Number + " AUDIO\n"
			+ "    TITLE \"" + TRACK_TITLE + "\"\n    INDEX 01 00:00:00\n";

}
