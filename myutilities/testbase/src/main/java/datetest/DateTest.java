package datetest;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Date date = new Date(System.currentTimeMillis());
		Calendar c = Calendar.getInstance(Locale.CHINA);
	}

}
