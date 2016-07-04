package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.util.ArrayList;
<<<<<<< HEAD
=======
import java.util.Date;
>>>>>>> 72ff47106c94f8f51f9c8365d5aa5cd06f47d2bd
import java.util.List;
import java.util.TimeZone;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class AAA {

	/**
	 * it is hard to compare and say column are equal. the question is : does
	 * count of a value matters. For example, for column forecast, it doesn't
	 * matter how many rows in original and how many rows in new. As long as
	 * they have the save value, it can be consider as equal. This is the case
	 * here is dealing with. It covers almost all inputs such as forecast, TIP,
	 * Carton, Price, Contribution and so on. The case that is not dealing with
	 * is count of the value. For example,"Exclusion Reason" column, even in
	 * orig and new, there are two values "A" and "B". But in orig there are 3A
	 * and 2B but in new there are 3B and 2A. this will be consider as equal in
	 * this method.
	 */
	public static void main(String[] args) throws Exception {

<<<<<<< HEAD
=======
        Date date = new Date(1406696963924l);
        System.out.println(date);

>>>>>>> 72ff47106c94f8f51f9c8365d5aa5cd06f47d2bd
		DecimalFormat df = new DecimalFormat("ABCDE00000");
		System.out.println(df.format(45));

		System.out.println(TimeZone.getDefault());

		DecimalFormat db = new DecimalFormat("0000000.###");
		System.out.println(db.format(45.6983654321354));

		List<String> ttt = new ArrayList<String>();
		ttt.add("asdfasdf");
		ttt.add(null);
		ttt.add("asdfasdf");
		ttt.add(null);
		ttt.add("asdfasdf");

		for (String aaa : ttt) {
			System.out.println(aaa);
		}
	}

	static void hhh() throws Exception {

		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs,
					String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs,
					String authType) {
			}
		} };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			;
		}

		URL url = new URL("https://dynamic.12306.cn/otsweb/");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		// connection.setDoOutput(true);
		connection.setRequestMethod("GET");

		// OutputStreamWriter writer = new
		// OutputStreamWriter(connection.getOutputStream());
		// writer.write(paramsStr);
		// writer.close();

		StringBuilder content = new StringBuilder(4096);

		BufferedReader br = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		String line = null;
		while ((line = br.readLine()) != null) {
			content.append(line + "\r\n");
		}
		// System.out.println(content.toString());

	}

}
