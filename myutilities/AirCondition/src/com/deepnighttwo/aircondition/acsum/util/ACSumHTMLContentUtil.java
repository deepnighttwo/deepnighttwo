package com.deepnighttwo.aircondition.acsum.util;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.deepnighttwo.aircondition.acsum.dao.ACSumDAOManager;
import com.deepnighttwo.aircondition.acsum.dao.AirConditionSum;
import com.deepnighttwo.aircondition.exception.HTMLParseException;
import com.deepnighttwo.aircondition.util.CalendarUtil;
import com.deepnighttwo.aircondition.util.FetchURLUtil;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

public class ACSumHTMLContentUtil {

	public static final String PARAM_START_DATE = "startDate";
	public static final String PARAM_END_DATE = "endDate";

	public static void pushACSumTaskToQueue(Calendar start, Calendar end) {
		String startStr = CalendarUtil.getDateStringFromCalendar(start);
		String endStr = CalendarUtil.getDateStringFromCalendar(end);
		Queue initializerWorkerQueue = QueueFactory
				.getQueue("acsuminitializerworker");
		initializerWorkerQueue.add(withUrl(
				"/dataretrieve/acsumRetrieveData?" + PARAM_START_DATE + "="
						+ startStr + "&" + PARAM_END_DATE + "=" + endStr)
				.method(Method.GET));

	}

	/**
	 * try to retrieve html content based on the given time window. parse the
	 * html content, get AirConditionSum and store them into db
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @throws IOException
	 *             Socket error most likely, can be recovered by retry;
	 * @throws HTMLParseException
	 *             target html content format is changed, need to modify code.
	 */
	public static String getACSumData(Calendar start, Calendar end)
			throws IOException, HTMLParseException {

		Map<String, String> params = new HashMap<String, String>();
		params.put("Tdate", CalendarUtil.getDateStringFromCalendar(end));
		params.put("Fdate", CalendarUtil.getDateStringFromCalendar(start));
		String htmlContent;

		htmlContent = FetchURLUtil.getContentUsingPost(
				"http://www.envir.gov.cn/airnews/index.asp", params, "GBK");

		Document doc = Jsoup.parse(htmlContent);

		Elements eles = doc.select("table[bordercolor] > tr");

		if (eles.size() == 0) {
			throw new HTMLParseException(
					"No row returned. May caused by socket error or target html format is changed: "
							+ CalendarUtil.getDateStringFromCalendar(start)
							+ " to "
							+ CalendarUtil.getDateStringFromCalendar(end));
		}

		int colCount = 4;

		checkColumnCount(eles, colCount);

		eles.remove(0);

		StringBuilder error = new StringBuilder("Invalidate row content:");
		boolean hasError = false;
		List<AirConditionSum> acs = new ArrayList<AirConditionSum>();
		String[] r = new String[colCount];
		int rawDataRowCount = eles.size();
		for (Element ele : eles) {
			Elements row = ele.select("td");
			for (int i = 0; i < colCount; i++) {
				r[i] = row.get(i).ownText();
			}
			AirConditionSum ac = ACSumFactory.getAirConditionSum(r[0], r[1],
					r[2], r[3]);
			if (ac == null) {
				error.append("\"" + Arrays.toString(r) + "\", ");
				hasError = true;
				continue;
			}
			acs.add(ac);
		}

		ACSumDAOManager.checkAndAddCondition(acs);

		int dayCount = CalendarUtil.dayDiff(end, start) + 1;
		int recordSize = acs.size();
		if (dayCount != recordSize) {
			hasError = true;
			if (rawDataRowCount == recordSize) {
				error.append("["
						+ CalendarUtil.getDateStringFromCalendar(start)
						+ "] to ["
						+ CalendarUtil.getDateStringFromCalendar(end)
						+ "] Record Missing from source. Expected: " + dayCount
						+ ", Actual: " + rawDataRowCount);
			} else {
				error.append("["
						+ CalendarUtil.getDateStringFromCalendar(start)
						+ "] to ["
						+ CalendarUtil.getDateStringFromCalendar(end)
						+ "] Record count doesn't match caused by parser. Expected: "
						+ dayCount + ", Actual: " + recordSize);
			}
		}

		return hasError ? error.toString() : null;
	}

	private static void checkColumnCount(Elements eles, int colCount)
			throws HTMLParseException {
		Elements title = eles.get(0).select("td");
		if (title.size() != colCount) {
			StringBuilder ret = new StringBuilder("Invalidate column count:"
					+ title.size() + " ");
			for (Element row : title) {
				ret.append(row.ownText() + "   ");
			}
			throw new HTMLParseException(ret.toString());
		}
	}
}
