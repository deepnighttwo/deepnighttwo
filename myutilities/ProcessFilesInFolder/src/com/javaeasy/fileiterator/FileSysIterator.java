package com.javaeasy.fileiterator;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileSysIterator {

    public List<ProcessFileSysItemIF> processors = new ArrayList<ProcessFileSysItemIF>();

    private FileSysIterator(List<ProcessFileSysItemIF> processors) {
        this.processors = processors;
    }

    public static FileSysIterator createInstance(
            List<ProcessFileSysItemIF> processors) {
        if (processors == null || processors.size() == 0) {
            return null;
        }

        return new FileSysIterator(processors);
    }

    public void runFileIterator() {

        File theRootFolder = RootFolderSelector.getRootFolder();
        if (theRootFolder == null) {
            System.out.println("Program Exit!");
            System.exit(0);
        }

        for (Iterator<ProcessFileSysItemIF> processorsIt = processors
                .iterator(); processorsIt.hasNext();) {
            ProcessFileSysItemIF processor = processorsIt.next();
            processor.initProcessor(theRootFolder);

        }

        List<File> rootFolders = new ArrayList<File>();
        rootFolders.add(theRootFolder);

        while (rootFolders.size() > 0) {

            List<File> nextGenRootList = new ArrayList<File>();
            for (Iterator<File> rootFoldersIt = rootFolders.iterator(); rootFoldersIt
                    .hasNext();) {
                File curFolder = rootFoldersIt.next();

                File[] folders = FileSysIteratorUtils
                        .findAllFoldersInFolder(curFolder);
                for (int i = 0; i < folders.length; i++) {
                    nextGenRootList.add(folders[i]);
                }

                for (Iterator<ProcessFileSysItemIF> processorsIt = processors
                        .iterator(); processorsIt.hasNext();) {
                    ProcessFileSysItemIF processor = processorsIt.next();

                    processor.preProcessFolder(curFolder);

                    String[] extensions = processor.getProcessFileExtentions();
                    
                    String[] fileNames = processor.getProcessFileNames();

                    File[] processFiles = FileSysIteratorUtils
                            .findAllFilesInFolder(curFolder, extensions,fileNames);

                    for (int curFileIdx = 0; curFileIdx < processFiles.length; curFileIdx++) {
                        processor.preProcessFile(processFiles[curFileIdx]);
                        processor.processFile(processFiles[curFileIdx]);
                        processor.postProcessFile(processFiles[curFileIdx]);
                    }

                    processor.postProcessFolder(curFolder);
                }
            }

            rootFolders.clear();

            rootFolders = nextGenRootList;

        }

        for (Iterator<ProcessFileSysItemIF> processorsIt = processors
                .iterator(); processorsIt.hasNext();) {
            ProcessFileSysItemIF processor = processorsIt.next();
            processor.done();
        }

        System.exit(0);
    }
}
