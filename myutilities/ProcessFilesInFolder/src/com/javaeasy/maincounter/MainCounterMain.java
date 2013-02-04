package com.javaeasy.maincounter;

import java.util.ArrayList;
import java.util.List;

import com.javaeasy.fileiterator.FileSysIterator;
import com.javaeasy.fileiterator.ProcessFileSysItemIF;

public class MainCounterMain {
    public static void main(String[] args) {
        List<ProcessFileSysItemIF> list = new ArrayList<ProcessFileSysItemIF>();
        list.add(new FileEncodingChanger());
        FileSysIterator.createInstance(list).runFileIterator();

    }
}
