package com.deepnighttwo.aircondition.acsum.util;

import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Logger;

import com.deepnighttwo.aircondition.acsum.dao.ACSumDAOManager;
import com.deepnighttwo.aircondition.acsum.dao.AirConditionSum;
import com.deepnighttwo.aircondition.util.CalendarUtil;
import com.deepnighttwo.aircondition.util.ISystemInitializer;

public class ACSumInitializer implements ISystemInitializer {

	private static final Logger log = Logger.getLogger(ACSumFactory.class
			.getName());

	int batchCount = 50;
	int ioRetry = 7;
	int contentRetry = 3;
	String startDate;

	public ACSumInitializer() {
		Properties props = new Properties();
		try {
			props.load(ACSumInitializer.class
					.getResourceAsStream("acsumsystem.properties"));
		} catch (IOException e) {
			log.severe("Unablt to load resrouce acsumsystem.properties");
		}

		try {
			batchCount = Integer.parseInt(props.getProperty("batchCount"));
		} catch (Exception ex) {
			log.severe("Failed to parse batchCount from acsumsystem.properties. Using "
					+ batchCount + " as default.");
		}

		try {
			ioRetry = Integer.parseInt(props.getProperty("ioRetry"));
		} catch (Exception ex) {
			log.severe("Failed to parse ioRetry from acsumsystem.properties. Using "
					+ ioRetry + " as default.");
		}

		try {
			contentRetry = Integer.parseInt(props.getProperty("contentRetry"));
		} catch (Exception ex) {
			log.severe("Failed to parse contentRetry from acsumsystem.properties. Using "
					+ contentRetry + " as default.");
		}

		startDate = props.getProperty("startDate");
	}

	@Override
	public void initSystem() {

		AirConditionSum ac = ACSumDAOManager.getToppestCondition();
		Calendar date = null;

		if (ac == null) {
			date = CalendarUtil.getCalendar(startDate);
			if (date == null) {
				log.severe("Failed to load/parse startDate from acsumsystem.properties:"
						+ startDate
						+ ". AirConditionSum system initializer exiting...");
				return;
			}
		} else {
			date = ac.getDate();
		}

		date.add(Calendar.DAY_OF_MONTH, 1);
		Calendar today = CalendarUtil.getToday();
		if (date.compareTo(today) > 0) {
			log.info("AirConditionSum system doesn't need to be initialized this time."
					+ " AirConditionSum system initializer exiting...");
			return;
		}
		date.add(Calendar.DAY_OF_MONTH, -1);

		int diffDay = CalendarUtil.dayDiff(today, date);

		Calendar start = date;
		Calendar end = (Calendar) start.clone();
		end.add(Calendar.DAY_OF_MONTH, batchCount - 1);

		while (diffDay >= batchCount) {
			handleTimeWindow(start, end);
			diffDay -= batchCount;
			start.add(Calendar.DAY_OF_MONTH, batchCount);
			end.add(Calendar.DAY_OF_MONTH, batchCount);
		}

		if (diffDay > 0) {
			end = (Calendar) start.clone();
			end.add(Calendar.DAY_OF_MONTH, batchCount - 1);
			handleTimeWindow(start, end);
		}

	}

	private void handleTimeWindow(Calendar start, Calendar end) {
		// int ioRetryCounter = this.ioRetry;
		// int contentRetryCounter = this.contentRetry;
		//
		// boolean retry = false;
		// String error;
		// do {
		// retry = false;
		// try {
		ACSumHTMLContentUtil.pushACSumTaskToQueue(start, end);
		// if (error == null) {
		// log.info("Retrieved records successfully.");
		// } else {
		// log.severe(error);
		// }
		// } catch (IOException e) {
		// e.printStackTrace();
		// retry = (--ioRetryCounter) > 0;
		// } catch (HTMLParseException e) {
		// e.printStackTrace();
		// retry = (--contentRetryCounter) > 0;
		// }
		// } while (retry);

	}

}
