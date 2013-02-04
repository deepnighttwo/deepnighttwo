package com.deepnighttwo.aircondition.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class CalendarUtil {

	public static final int DEFAULT_HOUR = 12;

	public static final long MILLION_SEC_PER_DAY = 24 * 60 * 60 * 1000;

	public static final TimeZone TIME_ZONE_SH = TimeZone
			.getTimeZone("Asia/Shanghai");

	/**
	 * format is expected as yyyy-(m)m-(d)d
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Calendar getCalendar(String dateStr) {
		String[] date = dateStr.split("-");
		if (date.length != 3) {
			return null;
		}
		int year = Integer.parseInt(date[0]);
		int month = Integer.parseInt(date[1]);
		int day = Integer.parseInt(date[2]);
		return getCalendar(year, month, day);
	}

	/**
	 * Jan is 0 in Calendar, so sub 1
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Calendar getCalendar(int year, int month, int day) {
		Calendar date = getCalendar();
		date.set(year, month - 1, day, DEFAULT_HOUR, 0, 0);
		date.set(Calendar.MILLISECOND, 0);
		return date;
	}

	public static Calendar getCalendar() {
		Calendar date = Calendar.getInstance(TIME_ZONE_SH,
				Locale.SIMPLIFIED_CHINESE);
		return date;
	}

	public static Calendar getToday() {
		Calendar date = getCalendar();
		date.setTimeInMillis(System.currentTimeMillis());

		date.set(Calendar.HOUR, DEFAULT_HOUR);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		return date;
	}

	public static int dayDiff(Calendar c1, Calendar c2) {
		long diffMillion = c1.getTimeInMillis() - c2.getTimeInMillis();
		return (int) (diffMillion / MILLION_SEC_PER_DAY);
	}

	public static String getDateStringFromCalendar(Calendar date) {
		SimpleDateFormat displaySDF = new SimpleDateFormat("yyyy-MM-dd",
				Locale.SIMPLIFIED_CHINESE);
		displaySDF.setTimeZone(TIME_ZONE_SH);

		return displaySDF.format(date.getTime());
	}

}
