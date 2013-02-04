package com.deepnighttwo.aircondition.acsum.util;

import java.util.logging.Logger;

import com.deepnighttwo.aircondition.acsum.dao.AirConditionSum;


public class ACSumFactory {

	private static final Logger log = Logger.getLogger(ACSumFactory.class
			.getName());

	public static AirConditionSum getAirConditionSum(String dateStr,
			String dustStr, String so2Str, String no2Str) {

		String[] date = dateStr.split("-");
		if (date.length != 3) {
			return null;
		}

		try {
			int year = Integer.parseInt(date[0]);
			int month = Integer.parseInt(date[1]);
			int day = Integer.parseInt(date[2]);

			int dust = Integer.parseInt(dustStr);
			int so2 = Integer.parseInt(so2Str);
			int no2 = Integer.parseInt(no2Str);

			AirConditionSum ret = new AirConditionSum(year, month, day, dust,
					so2, no2);

			return ret;

		} catch (Exception ex) {
			log.severe(ex.getMessage());
			return null;
		}
	}

}
