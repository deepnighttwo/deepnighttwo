package com.amazon.mzang.tools.old;

import java.io.IOException;
import java.util.List;

import com.amazon.mzang.tools.FileUtil;

public class SplitCommand {

    private static volatile SplitCommand instance;

    public static SplitCommand getInstance() {
        if (instance == null) {
            synchronized (SplitCommand.class) {
                if (instance == null) {
                    instance = new SplitCommand();
                }
            }
        }
        return instance;
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
    	System.out.println(String.valueOf(null));
        List<String> lines = FileUtil.readFileAsLines("command");

        StringBuilder command = new StringBuilder();
        for (String line : lines) {
            for (char ch : line.toCharArray()) {
                command.append(ch);
                if (ch == ';') {
                    command.append("\r\n");
                }
            }
            command.append("\r\n");
        }
        System.out.println(command);
    }
}
