package com.snda.mzang.tvtogether.server.handler.processor;

import java.util.List;

import org.json.JSONObject;

import com.snda.mzang.tvtogether.base.B;
import com.snda.mzang.tvtogether.server.dao.ChannelDao;
import com.snda.mzang.tvtogether.server.entry.Channel;
import com.snda.mzang.tvtogether.server.protocol.IMessageProcessor;
import com.snda.mzang.tvtogether.server.util.JSONConverter;

public class GetChannelList implements IMessageProcessor {

	ChannelDao dao = ChannelDao.getInstance();

	public String getProcessorName() {
		return B.getChannelList;
	}

	public byte[] handle(JSONObject msg) {
		JSONObject ret = new JSONObject();
		try {

			List<Channel> channels = dao.selectAllChannels();
			JSONConverter.convertListToJSON(channels, B.channels, ret, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret.toString().getBytes();
	}
}