package test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class PrintChar {

	public static void main(String[] args) throws UnsupportedEncodingException {
		DateFormat format = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, Locale.CHINA);
		System.out.println(format.format(new Date()));
		char ch = '=';// ☆★▢▣▤▥▦▧▨▩☬☭☮☯
		System.out.println((int) ch);
		for (int i = -100; i < 100; i++) {
			int sd = ch;
			sd += i;
			char nch = (char) sd;
			System.out.print(nch);
		}

		String str = "asdfasdfasdf%1Sasdf";
		System.out.println(String.format(str, "FFFFFFFFFFFFFFFFFFFFFFFFFFF"));

		System.out.println(URLDecoder.decode(str1, "UTF-8"));
	}

	private static String str1 = "POST&http%3A%2F%2Fwww.flickr.com%2Fservices%2Foauth%2Frequest_token&oauth_callback%3Doob%26oauth_consumer_key%3D1f24ea7aa557e67fb2dfdc4632242285%26oauth_nonce%3D8806429035050569847%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D1308840274%26oauth_version%3D1.0";
}
