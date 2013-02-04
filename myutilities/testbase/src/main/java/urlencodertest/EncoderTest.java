package urlencodertest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class EncoderTest {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String str = URLEncoder.encode("阿斯顿发生的放", "utf-8");
		System.out.println(str);
		String str1 = URLDecoder.decode(str, "utf-8");
		System.out.println(str1);

	}

}
