package com.javaeasy.maincounter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.javaeasy.fileiterator.ProcessFileSysItemIF;

public class FileEncodingChanger implements ProcessFileSysItemIF {

    public static int MAIN_COUNTER = 0;

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

    }

    public void postProcessFolder(File folder) {
    }

    public void preProcessFile(File file) {
    }

    public void processFile(File file) {
        try {
            BufferedReader sourceReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), "GBK"));
            String line = null;
            while ((line = sourceReader.readLine()) != null) {
                if (line.indexOf("main") > -1) {
                    System.out.println(line);
                    MAIN_COUNTER++;
                }
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void postProcessFile(File file) {
    }

    public void done() {
        System.out.println(MAIN_COUNTER);
    }

}
