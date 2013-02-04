package com.deepnighttwo.aircondition.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deepnighttwo.aircondition.acsum.util.ACSumHTMLContentUtil;
import com.deepnighttwo.aircondition.exception.HTMLParseException;
import com.deepnighttwo.aircondition.util.CalendarUtil;

@SuppressWarnings("serial")
public class AirConditionSumRetrieveDataServlet extends HttpServlet {

	private static final Logger log = Logger
			.getLogger(AirConditionSumRetrieveDataServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String startDate = req
				.getParameter(ACSumHTMLContentUtil.PARAM_START_DATE);
		String endDate = req.getParameter(ACSumHTMLContentUtil.PARAM_END_DATE);

		Calendar start = CalendarUtil.getCalendar(startDate);
		Calendar end = CalendarUtil.getCalendar(endDate);

		try {
			String error = ACSumHTMLContentUtil.getACSumData(start, end);
			if (error != null) {
				log.severe("Data between "
						+ CalendarUtil.getDateStringFromCalendar(start)
						+ " and " + CalendarUtil.getDateStringFromCalendar(end)
						+ " is failed to be added due to the following error:"
						+ error);
			} else {
				log.info("Data between "
						+ CalendarUtil.getDateStringFromCalendar(start)
						+ " and " + CalendarUtil.getDateStringFromCalendar(end)
						+ " is added.");
			}
		} catch (HTMLParseException e) {
			e.printStackTrace();
		}
	}

}