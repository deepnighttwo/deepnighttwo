package encoding;

import java.io.UnsupportedEncodingException;

public class TestDefaultEncoding {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String str = "一二三四五六";
        byte[] bin = str.getBytes("UTF-16");
        for (int i : bin) {
            System.out.print(Integer.toHexString(i) + " ");
        }
        System.out.println();
    }
}
