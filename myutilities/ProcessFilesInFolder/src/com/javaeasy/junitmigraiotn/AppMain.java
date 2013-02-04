package com.javaeasy.junitmigraiotn;

import java.util.ArrayList;
import java.util.List;

import com.javaeasy.fileiterator.FileSysIterator;
import com.javaeasy.fileiterator.ProcessFileSysItemIF;

public class AppMain {

	/**
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		List<ProcessFileSysItemIF> list = new ArrayList<ProcessFileSysItemIF>();
		list.add(new JunitMigrationProcessor());
		FileSysIterator.createInstance(list).runFileIterator();
	}

}
