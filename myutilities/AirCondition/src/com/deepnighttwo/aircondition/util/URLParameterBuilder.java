package com.deepnighttwo.aircondition.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class URLParameterBuilder {

	public static String getURLParameters(Map<String, String> params,
			boolean questionMark) throws UnsupportedEncodingException {

		if (params == null || params.size() == 0) {
			return "";
		}

		StringBuilder ret = new StringBuilder(params.size() * 16);

		for (String key : params.keySet()) {
			String value = params.get(key);
			ret.append('&');
			String encodedKey = URLEncoder.encode(key, "UTF-8");
			ret.append(encodedKey);
			ret.append('=');
			String encodedValue = URLEncoder.encode(value, "UTF-8");
			ret.append(encodedValue);
		}

		if (questionMark) {
			ret.setCharAt(0, '?');
		} else {
			ret.deleteCharAt(0);
		}

		return ret.toString();
	}

}
