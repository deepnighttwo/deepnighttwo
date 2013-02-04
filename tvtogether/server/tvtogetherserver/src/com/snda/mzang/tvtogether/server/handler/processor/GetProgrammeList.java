package com.snda.mzang.tvtogether.server.handler.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.snda.mzang.tvtogether.base.B;
import com.snda.mzang.tvtogether.server.dao.ChannelDao;
import com.snda.mzang.tvtogether.server.entry.Programme;
import com.snda.mzang.tvtogether.server.protocol.IMessageProcessor;
import com.snda.mzang.tvtogether.server.util.JSONConverter;

public class GetProgrammeList implements IMessageProcessor {

	ChannelDao dao = ChannelDao.getInstance();

	public String getProcessorName() {
		return B.getProgrammeList;
	}

	public byte[] handle(JSONObject msg) {
		JSONObject ret = new JSONObject();
		try {

			List<Programme> programmes = new ArrayList<Programme>();
			Date d1 = new Date("2012-04-24 00:00:00");
			Date d2 = new Date("2012-04-24 08:00:00");
			Date d3 = new Date("2012-04-24 09:00:00");
			Date d4 = new Date("2012-04-24 17:00:00");
			Date d5 = new Date("2012-04-24 23:00:00");

			Programme p = new Programme();
			p.setId("1");
			p.setName("午夜场");
			p.setImage("1.png");
			p.setComments("这是午夜场电视剧");
			p.setChannelName("广东电视台");
			p.setStartTime(d1);
			p.setEndTime(d2);
			programmes.add(p);

			p.setId("2");
			p.setName("早间剧场");
			p.setImage("2.png");

			p.setComments("这是早间剧场电视剧");
			p.setChannelName("广东电视台");
			p.setStartTime(d2);
			p.setEndTime(d3);
			programmes.add(p);

			p.setId("3");
			p.setName("午间剧场");
			p.setImage("3.png");

			p.setComments("这是午间剧场电视剧");
			p.setChannelName("广东电视台");
			p.setStartTime(d3);
			p.setEndTime(d4);
			programmes.add(p);

			p.setId("4");
			p.setName("午夜场");
			p.setImage("4.png");

			p.setComments("这是午夜场电视剧");
			p.setChannelName("广东电视台");
			p.setStartTime(d4);
			p.setEndTime(d5);
			programmes.add(p);

			JSONConverter.convertListToJSON(programmes, B.programmes, ret, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret.toString().getBytes();
	}
}