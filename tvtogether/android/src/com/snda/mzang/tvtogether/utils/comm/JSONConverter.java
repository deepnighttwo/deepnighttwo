package com.snda.mzang.tvtogether.utils.comm;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONConverter<T> implements IContentConverter<T> {

	public static IContentConverter<JSONObject> JSON = new JSONConverter<JSONObject>();

	private JSONConverter() {

	}

	@SuppressWarnings("unchecked")
	public T convert(byte[] content) throws JSONException {
		String str = new String(content);
		return (T) new JSONObject(str);
	}

}
