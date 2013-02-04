package com.deepnighttwo.aircondition.util;

import java.util.Calendar;

public class TestCalendarFac {

	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
			Calendar date = CalendarUtil.getCalendar(2011, 7, 15);
			System.out.println(date.get(Calendar.MILLISECOND));
		}
	}

}
