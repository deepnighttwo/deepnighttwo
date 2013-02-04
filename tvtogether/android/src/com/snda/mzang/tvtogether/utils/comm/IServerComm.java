package com.snda.mzang.tvtogether.utils.comm;

import org.json.JSONObject;

public interface IServerComm {
	public JSONObject sendMsg(JSONObject msg);

	public <T> T sendMsg(JSONObject msg, IContentConverter<T> converter);

	public <T> T sendMsg(String handlerName, byte[] message, IContentConverter<T> converter);

}
