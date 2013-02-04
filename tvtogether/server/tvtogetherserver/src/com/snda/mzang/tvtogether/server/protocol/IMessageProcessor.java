package com.snda.mzang.tvtogether.server.protocol;

import org.json.JSONObject;

public interface IMessageProcessor {

	String getProcessorName();

	byte[] handle(JSONObject data);

}