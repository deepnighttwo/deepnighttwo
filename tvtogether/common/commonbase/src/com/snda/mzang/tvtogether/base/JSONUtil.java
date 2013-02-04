package com.snda.mzang.tvtogether.base;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtil {

	public static Object getObj(JSONObject data, String key) {
		if (data == null) {
			return null;
		}
		try {
			return data.get(key);
		} catch (JSONException e) {
			return null;
		}
	}

	public static String getString(JSONObject data, String key) {
		if (data == null) {
			return null;
		}
		try {
			return (String) data.get(key);
		} catch (JSONException e) {
			return null;
		}
	}

	public static boolean getBoolean(JSONObject data, String key) {
		if (data == null) {
			return false;
		}
		try {
			return data.getBoolean(key);
		} catch (JSONException e) {
			return false;
		}
	}

	public static String[] getStringArray(JSONObject data, String key) {
		if (data == null) {
			return null;
		}
		try {
			JSONArray array = data.getJSONArray(key);
			String[] ret = new String[array.length()];
			for (int i = 0; i < ret.length; i++) {
				ret[i] = array.getString(i);
			}
			return ret;
		} catch (JSONException e) {
			return null;
		}
	}

	public static JSONObject[] getJSONObjArray(JSONObject data, String key) {
		if (data == null) {
			return null;
		}
		try {
			JSONArray array = data.getJSONArray(key);
			JSONObject[] ret = new JSONObject[array.length()];
			for (int i = 0; i < ret.length; i++) {
				ret[i] = array.getJSONObject(i);
			}
			return ret;
		} catch (JSONException e) {
			return null;
		}
	}

}
