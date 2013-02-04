package com.deepnighttwo.aircondition.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deepnighttwo.aircondition.acsum.dao.ACSumDAOManager;
import com.deepnighttwo.aircondition.acsum.dao.AirConditionSum;
import com.deepnighttwo.aircondition.acsum.util.ACSumFactory;
import com.deepnighttwo.aircondition.acsum.util.ACSumHTMLContentUtil;
import com.deepnighttwo.aircondition.util.CalendarUtil;

@SuppressWarnings("serial")
public class AirConditionSumRetrieveTodayDataServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(ACSumFactory.class
			.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		Calendar today = CalendarUtil.getToday();
		AirConditionSum ac = ACSumDAOManager.getToppestCondition();
		if (today.equals(ac.getDate())) {
			log.info("Data for "
					+ CalendarUtil.getDateStringFromCalendar(today)
					+ " already exists.");
		}
		// try {
		ACSumHTMLContentUtil.pushACSumTaskToQueue(today, today);
		// if (error != null) {
		// log.severe(error);
		// } else {
		// log.severe("Data for "
		// + CalendarUtil.getDateStringFromCalendar(today)
		// + " added.");
		// }
		// } catch (HTMLParseException e) {
		// e.printStackTrace();
		// }

	}

}