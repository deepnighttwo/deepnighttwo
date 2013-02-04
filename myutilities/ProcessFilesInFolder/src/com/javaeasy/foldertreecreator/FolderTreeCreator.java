package com.javaeasy.foldertreecreator;

import java.io.File;

import com.javaeasy.fileiterator.FileSysIteratorUtils;
import com.javaeasy.fileiterator.ProcessFileSysItemIF;

public class FolderTreeCreator implements ProcessFileSysItemIF {

    // private static final String FOLDER_LINE = "©¦";
    // private static final String FILE_ITEM = "©À©¤";
    // private static final String FILE_END = "©¸©¤";

    public void done() {
        System.out.println("Transformation Complete");
    }

    public String[] getProcessFileExtentions() {
        return new String[]{FileSysIteratorUtils.ALLOW_ALL_FILE};
    }
    
    @Override
	public String[] getProcessFileNames() {
		return null;
	}

    public void initProcessor(File root) {

    }

    public void postProcessFile(File file) {
        // TODO Auto-generated method stub

    }

    public void postProcessFolder(File folder) {
        // TODO Auto-generated method stub

    }

    public void preProcessFile(File file) {
        System.out.println(file.getName());
    }

    public void preProcessFolder(File folder) {
        // TODO Auto-generated method stub

    }

    public void processFile(File file) {
        // TODO Auto-generated method stub

    }

}
