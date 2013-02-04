package httpprotocol;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class TestHttpClient {

	private static String LOCATION = "http://www.wanjuanchina.net/logging.php";

	public static void main(String... args) throws ClientProtocolException,
			IOException, URISyntaxException {
		HttpClient httpclient = new DefaultHttpClient();
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("action", "login"));

		System.out.println(URLEncodedUtils.format(qparams, "UTF-8"));

		URI uri = URIUtils.createURI("http", "www.wanjuanchina.net", -1,
				"/logging.php", URLEncodedUtils.format(qparams, "UTF-8"), null);
		HttpGet httpget = new HttpGet(LOCATION);
		System.out.println(httpget.getURI());

		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();
			int l;
			byte[] tmp = new byte[2048];
			while ((l = instream.read(tmp)) != -1) {
				// System.out.println(new String(tmp, "GBK"));
			}
		}

	}

}
