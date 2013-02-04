package com.javaeasy.cuefileprocessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.javaeasy.fileiterator.FileSysIterator;
import com.javaeasy.fileiterator.ProcessFileSysItemIF;

public class CueFileProcessorMain {
    public static void main(String[] args) throws IOException {
        List<ProcessFileSysItemIF> list = new ArrayList<ProcessFileSysItemIF>();
        list.add(CueFileProcessor.createInstance());
        FileSysIterator.createInstance(list).runFileIterator();
    }
}
