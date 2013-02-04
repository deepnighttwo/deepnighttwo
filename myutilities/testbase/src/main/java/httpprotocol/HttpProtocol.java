package httpprotocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class HttpProtocol {

	/**
	 * @param args
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException,
			IOException {
		List<String[]> httpParams = new ArrayList<String[]>();

		httpParams.add(new String[] { "Host", "www.google.com.hk" });
		httpParams.add(new String[] { "User-Agent", "DeepNightTwo" });
		httpParams.add(new String[] { "Accept", "text/html" });
		httpParams.add(new String[] { "Accept-Language", "en-us,en;q=0.5" });
		httpParams.add(new String[] { "Accept-Encoding", "deflate" });
		httpParams.add(new String[] { "Accept-Charset",
				"ISO-8859-1,utf-8;q=0.7,*;q=0.7" });
		httpParams.add(new String[] { "Connection", "keep-alive" });
		callHttpGet("www.google.com.hk", 80, "/", httpParams);
	}

	public static void callHttpGet(String host, int port, String path,
			List<String[]> httpparams) throws UnknownHostException, IOException {
		Socket socket = new Socket(host, port);
		OutputStream os = socket.getOutputStream();
		StringBuilder request = new StringBuilder();
		request.append("GET " + path + " HTTP/1.1" + "\r\n");
		for (String[] param : httpparams) {
			request.append(param[0] + ": " + param[1] + "\r\n");
		}
		request.append("\r\n");

		os.write(request.toString().getBytes());
		os.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				socket.getInputStream(), "Big5"));
		String line = null;
		while ((line = br.readLine()) != null) {
			System.out.println(line + "\r\n");
		}
		br.close();
	}

}
