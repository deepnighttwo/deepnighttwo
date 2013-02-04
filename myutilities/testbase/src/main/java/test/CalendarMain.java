package test;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class CalendarMain {

	public static final TimeZone TIME_ZONE_SH = TimeZone
			.getTimeZone("Asia/Shanghai");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Calendar c = Calendar.getInstance(TIME_ZONE_SH,
				Locale.SIMPLIFIED_CHINESE);
		System.out.println(c.get(Calendar.MONTH));
		System.out.println(c.get(Calendar.DAY_OF_MONTH));
	}

}
