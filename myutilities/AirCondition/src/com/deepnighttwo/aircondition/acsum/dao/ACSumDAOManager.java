package com.deepnighttwo.aircondition.acsum.dao;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.deepnighttwo.aircondition.dao.util.PMF;
import com.deepnighttwo.aircondition.util.CalendarUtil;

public class ACSumDAOManager {

	public static boolean checkAndAddCondition(Collection<AirConditionSum> acs) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistentAll(acs);
		} finally {
			pm.close();
		}
		return true;
	}

	public static boolean checkAndAddCondition(AirConditionSum acSum) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(acSum);
		} finally {
			pm.close();
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public static AirConditionSum getAirCondition(int year, int month, int day) {
		Calendar date = CalendarUtil.getCalendar(year, month, day);

		String query = "select from " + AirConditionSum.class.getName()
				+ "  where millionSec == " + date.getTimeInMillis();
		PersistenceManager pm = PMF.get().getPersistenceManager();

		List<AirConditionSum> condition = (List<AirConditionSum>) pm.newQuery(
				query).execute();
		if (condition != null && condition.size() > 0) {
			AirConditionSum ac = condition.get(0);
			ac.init();
			return ac;
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public static List<AirConditionSum> getAirConditions() {
		String query = "select from " + AirConditionSum.class.getName();
		PersistenceManager pm = PMF.get().getPersistenceManager();

		List<AirConditionSum> conditions = (List<AirConditionSum>) pm.newQuery(
				query).execute();

		initAirConditionSums(conditions);
		return conditions;
	}

	@SuppressWarnings("unchecked")
	public static List<AirConditionSum> getTopConditions(int count) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(AirConditionSum.class);
		query.setOrdering("millionSec desc");
		query.setRange(0, count);

		List<AirConditionSum> results = (List<AirConditionSum>) query.execute();
		initAirConditionSums(results);
		return results;
	}

	public static AirConditionSum getToppestCondition() {

		List<AirConditionSum> results = getTopConditions(1);

		if (results == null || results.size() == 0) {
			return null;
		}

		return results.get(0);
	}

	private static void initAirConditionSums(Collection<AirConditionSum> acs) {
		for (AirConditionSum ac : acs) {
			ac.init();
		}
	}

}
