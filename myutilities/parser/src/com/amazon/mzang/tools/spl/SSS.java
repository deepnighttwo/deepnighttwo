package com.amazon.mzang.tools.spl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class SSS {

    /**
     * @param args
     * @throws Throwable
     */
    public static void main(String[] args) throws Throwable {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\mengzang\\Desktop\\a.txt"));
        PrintWriter pw = new PrintWriter(new FileWriter("C:\\Users\\mengzang\\Desktop\\b.txt"));
        String line = null;
        boolean contain = false;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("New: Compare Value: ")) {
                contain = true;
                continue;
            }
            if (contain == true) {
                pw.write(line + "\r\n");
            }
            if (line.trim().length() == 0) {
                contain = false;
            }
        }
        pw.close();
        br.close();
    }

}
