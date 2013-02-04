package com.javaeasy.encodingprocessor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.javaeasy.fileiterator.ProcessFileSysItemIF;

public class FileEncodingChanger implements ProcessFileSysItemIF {

    private List<String> processedFiles = new ArrayList<String>();

    private String sourceEncoding = "UTF-8";
    private String targetEncoding = "GBK";

    public FileEncodingChanger(String p_sourceEncoding, String p_targetEncoding) {
        sourceEncoding = p_sourceEncoding;
        targetEncoding = p_targetEncoding;
    }

    public void initProcessor(File root) {
    }

    public String[] getProcessFileExtentions() {
        return new String[] { "java" };
    }
    
    @Override
	public String[] getProcessFileNames() {
		return null;
	}

    public void preProcessFolder(File folder) {
        System.out.println("Processing Folder:" + folder.getPath());

    }

    public void postProcessFolder(File folder) {
        System.out.println("Processed Folder:" + folder.getPath());
    }

    public void preProcessFile(File file) {
        System.out.println("Processing File:" + file.getPath());
    }

    public void processFile(File file) {
        try {
            ChangeFileEncodingUtils.changeFileEncoding(file, sourceEncoding,
                    targetEncoding, false);

        } catch (IOException e) {
            System.err.println("File" + file.getPath()
                    + " conversion failed: \tFrom " + sourceEncoding + " to "
                    + targetEncoding);
            e.printStackTrace();
        }
    }

    public void postProcessFile(File file) {
        System.out.println("Processed File:" + file.getPath());
        processedFiles.add(file.getPath());
    }

    public void done() {
        int count = processedFiles.size();
        System.out.println("File processed:" + count);
        for (Iterator<String> it = processedFiles.iterator(); it.hasNext();) {
            System.out.println(it.next());
        }
    }

    public String getSourceEncoding() {
        return sourceEncoding;
    }

    public void setSourceEncoding(String sourceEncoding) {
        this.sourceEncoding = sourceEncoding;
    }

    public String getTargetEncoding() {
        return targetEncoding;
    }

    public void setTargetEncoding(String targetEncoding) {
        this.targetEncoding = targetEncoding;
    }

}
