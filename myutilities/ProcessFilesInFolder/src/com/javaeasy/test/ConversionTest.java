package com.javaeasy.test;

import java.io.File;
import java.io.IOException;

import com.javaeasy.encodingprocessor.ChangeFileEncodingUtils;

public class ConversionTest {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        ChangeFileEncodingUtils.changeFileEncoding(
                new File("E:\\TestCar.java"), "UTF-8", "UTF-16BE", true);
    }
}
