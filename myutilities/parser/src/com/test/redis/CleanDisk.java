package com.test.redis;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CleanDisk {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        byte[] e = new byte[1024 * 1024];
        OutputStream os = new FileOutputStream("/home/mengzang/eeeeeeeeeee");

        int size = 1024 * 110;
        for (int i = 0; i < size; i++) {
            os.write(e);
            if (i % 1024 == 0) {
                System.out.println((i / 1024) + "G");
            }
        }
        System.out.println("Finished.");
    }

}
