package com.deepnighttwo.aircondition.acsum.dao;

import java.util.Calendar;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.deepnighttwo.aircondition.util.CalendarUtil;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class AirConditionSum {

	@PrimaryKey
	@Persistent
	private Long millionSec;

	private Calendar date;

	@Persistent
	private int dust;
	@Persistent
	private int so2;
	@Persistent
	private int no2;

	public AirConditionSum() {

	}

	/**
	 * must invoke this to initial date if this instance is created by ORMapping
	 */
	public void init() {
		this.date = CalendarUtil.getCalendar();
		date.setTimeInMillis(millionSec);
	}

	public AirConditionSum(int year, int month, int day, int dust, int so2,
			int no2) {
		this.date = CalendarUtil.getCalendar(year, month, day);
		this.dust = dust;
		this.so2 = so2;
		this.no2 = no2;
		millionSec = date.getTimeInMillis();
	}

	public int getDust() {
		return dust;
	}

	public void setDust(int dust) {
		this.dust = dust;
	}

	public int getSo2() {
		return so2;
	}

	public void setSo2(int so2) {
		this.so2 = so2;
	}

	public int getNo2() {
		return no2;
	}

	public void setNo2(int no2) {
		this.no2 = no2;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public int getYear() {
		return date.get(Calendar.YEAR);
	}

	public int getMonth() {
		return date.get(Calendar.MONTH);
	}

	public int getDay() {
		return date.get(Calendar.DAY_OF_MONTH);
	}

	public String toString() {
		return CalendarUtil.getDateStringFromCalendar(date) + " : Dust=" + dust
				+ ", SO2=" + so2 + ", NO2=" + no2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + dust;
		result = prime * result + no2;
		result = prime * result + so2;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AirConditionSum other = (AirConditionSum) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (dust != other.dust)
			return false;
		if (no2 != other.no2)
			return false;
		if (so2 != other.so2)
			return false;
		return true;
	}

	public long getMillionSec() {
		return millionSec;
	}

	public void setMillionSec(long millionSec) {
		this.millionSec = millionSec;
	}

}
