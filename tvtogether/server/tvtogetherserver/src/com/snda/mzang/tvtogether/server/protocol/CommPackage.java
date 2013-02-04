package com.snda.mzang.tvtogether.server.protocol;

import org.json.JSONObject;

public class CommPackage {

	public String type;

	public JSONObject data;

	@Override
	public String toString() {
		return "CommPackage [type=" + type + ", data=" + data + "]";
	}

}
