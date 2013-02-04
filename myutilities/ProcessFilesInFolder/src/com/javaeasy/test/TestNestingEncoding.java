package com.javaeasy.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

public class TestNestingEncoding {
    public static void main(String[] args) throws IOException {
        String a = "Œ“";
        Charset sourceCharset = null;
        Charset targetCharset = null;
        try {
            sourceCharset = Charset.forName("GBK");
            targetCharset = Charset.forName("UTF-8");
        } catch (UnsupportedCharsetException ex) {
            ex.printStackTrace();
        }

        BufferedReader sourceReaderg = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File("E:\\GBK")),
                        sourceCharset));
        BufferedReader sourceReaderu = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(new File("E:\\UTF8")),
                        targetCharset));
        String g = sourceReaderg.readLine();
        String u = sourceReaderu.readLine();
        int ai = a.charAt(0);
        int gi = g.charAt(0);
        int ui = u.charAt(0);

        // byte[] fromfile = g.getBytes("UTF-16BE");// 25105

        System.out.println(ai + "\t" + gi + "\t" + ui);

    }
}