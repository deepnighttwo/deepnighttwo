package com.javaeasy.changeimport;

import java.util.ArrayList;
import java.util.List;

import com.javaeasy.cuefileprocessor.CueFileProcessor;
import com.javaeasy.fileiterator.FileSysIterator;
import com.javaeasy.fileiterator.ProcessFileSysItemIF;

public class AppMainMFP {

	/**
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		List<ProcessFileSysItemIF> list = new ArrayList<ProcessFileSysItemIF>();
		list.add(new ImportChangeProcessorMFP());
		FileSysIterator.createInstance(list).runFileIterator();
	}

}
