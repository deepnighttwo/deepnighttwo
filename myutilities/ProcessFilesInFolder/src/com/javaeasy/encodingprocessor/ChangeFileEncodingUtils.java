package com.javaeasy.encodingprocessor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.UnsupportedCharsetException;

public class ChangeFileEncodingUtils {

    public static final String LINE_SEPARATOR = System
            .getProperty("line.separator");

    // public static final Charset JAVA_NESTING_RUNTIME_ENCODING = Charset
    // .forName("UTF-16BE");

    public static boolean changeFileEncoding(File sourceFile,
            String sourceEncoding, String targetEncoding,
            boolean isBakSourceFile) throws IOException {
        if ((!sourceFile.exists()) || (!sourceFile.isFile())
                || (sourceFile.length() == 0)) {
            return false;
        }
        Charset sourceCharset = null;
        Charset targetCharset = null;
        CharsetEncoder targetEncoder = null;
        try {
            sourceCharset = Charset.forName(sourceEncoding);
            targetCharset = Charset.forName(targetEncoding);
            targetEncoder = targetCharset.newEncoder();
        } catch (UnsupportedCharsetException ex) {
            ex.printStackTrace();
        }

        BufferedReader sourceReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(sourceFile), sourceCharset));

        File targetFile = createTargetFile(sourceFile, true,
                ".conversiontarget");

        DataOutputStream targetOutput = new DataOutputStream(
                new FileOutputStream(targetFile));
        char[] buffer = new char[1024];
        int bufSize = 0;
        while ((bufSize = sourceReader.read(buffer)) > 0) {
            CharBuffer sourceBuf = CharBuffer.wrap(buffer, 0, bufSize);
            ByteBuffer targetBytesBuf = targetEncoder.encode(sourceBuf);
            targetOutput.write(targetBytesBuf.array(), 0, targetBytesBuf
                    .limit());
        }
        sourceReader.close();
        targetOutput.flush();
        targetOutput.close();

        if (isBakSourceFile) {
            File tempFile = createTargetFile(sourceFile, false,
                    ".sourcefilebak");
            File sourceCopy = createTargetFile(sourceFile, false, "");
            sourceFile.renameTo(tempFile);
            targetFile.renameTo(sourceCopy);
        } else {
            sourceFile.delete();
            targetFile.renameTo(sourceFile);
        }

        return true;
    }

    private static File createTargetFile(File sourceFile, boolean createFile,
            String newExtension) throws IOException {
        String name = sourceFile.getName();
        File targetFile = new File(sourceFile.getParent(), name + newExtension);
        if (targetFile.exists()) {
        } else if (createFile) {
            targetFile.createNewFile();
        }
        return targetFile;
    }

}
