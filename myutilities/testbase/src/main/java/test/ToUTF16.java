package test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ToUTF16 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                System.in));// Charset.forName("UTF-8")
        System.out.println("Please type the string to convert:");
        char[] chars = new char[1024];
        int count = reader.read(chars);
        // String content = reader.readLine();
        // System.out.println(content);
        // int count = content.length();
        for (int i = 0; i < count - 2; i++) {
            char codePoint = chars[i];
            System.out.print(codePoint);
            int number = codePoint;
            System.out.print(Integer.toHexString(number));
        }
    }
}
