package com.javaeasy.fileiterator;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class RootFolderSelector {

    public RootFolderSelector() {

    }

    public static File getRootFolder() {
        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.showOpenDialog(mainFrame);
        return fc.getSelectedFile();
    }

}
