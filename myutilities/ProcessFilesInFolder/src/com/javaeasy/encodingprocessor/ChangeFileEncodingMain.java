package com.javaeasy.encodingprocessor;

import java.util.ArrayList;
import java.util.List;

import com.javaeasy.fileiterator.FileSysIterator;
import com.javaeasy.fileiterator.ProcessFileSysItemIF;

public class ChangeFileEncodingMain {
    public static void main(String[] args) {
        String targetEncoding = "UTF-8";
        String sourceEncoding = "GBK";

        List<ProcessFileSysItemIF> list = new ArrayList<ProcessFileSysItemIF>();
        list.add(new FileEncodingChanger(sourceEncoding, targetEncoding));
        FileSysIterator.createInstance(list).runFileIterator();

    }
}
