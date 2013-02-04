package com.deepnighttwo.aircondition.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class FetchURLUtil {

	public static String getContentUsingPost(String urlStr,
			Map<String, String> params, String encoding) throws IOException {

		String paramsStr = URLParameterBuilder.getURLParameters(params, false);

		URL url = new URL(urlStr);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");

		OutputStreamWriter writer = new OutputStreamWriter(
				connection.getOutputStream());
		writer.write(paramsStr);
		writer.close();

		StringBuilder content = new StringBuilder(4096);

		BufferedReader br = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), encoding));
		String line = null;
		while ((line = br.readLine()) != null) {
			content.append(line + "\r\n");
		}
		return content.toString();
	}

	public static String getContentUsingGet(String urlStr,
			Map<String, String> params, String encoding) throws IOException {

		String paramsStr = URLParameterBuilder.getURLParameters(params, true);

		URL url = new URL(urlStr + paramsStr);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(false);
		connection.setRequestMethod("GET");

		StringBuilder content = new StringBuilder(4096);

		BufferedReader br = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), encoding));
		String line = null;
		while ((line = br.readLine()) != null) {
			content.append(line);
		}
		return content.toString();
	}

}
