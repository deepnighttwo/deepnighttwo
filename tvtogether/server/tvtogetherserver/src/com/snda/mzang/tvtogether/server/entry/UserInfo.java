package com.snda.mzang.tvtogether.server.entry;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.snda.mzang.tvtogether.base.B;

public class UserInfo implements IFieldMapper {

	public static Map<String, String> FIELD_MAP;

	static {
		Map<String, String> fieldMap = new HashMap<String, String>();
		fieldMap.put("id", B.userId);

		FIELD_MAP = Collections.unmodifiableMap(fieldMap);
	}

	@Override
	public Map<String, String> getFieldMap() {
		return FIELD_MAP;
	}

	public static enum UserStatus {
		ENABLE("enable"), DISABLE("disable");

		private String value;

		UserStatus(String value) {
			this.value = value;
		}

		public String getStatus() {
			return value;
		}

		public UserStatus getStatus(String value) {
			if ("enable".equals(value)) {
				return ENABLE;
			} else if ("disable".equals(value)) {
				return DISABLE;
			}
			return null;
		}

		public String toString() {
			return value;
		}
	}

	String id;
	String userName;
	String userPassword;
	String icon;
	String comments;
	String favor;
	Double locationX;
	Double locationY;
	Date registerTime;
	Date lastLogin;
	String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getFavor() {
		return favor;
	}

	public void setFavor(String favor) {
		this.favor = favor;
	}

	public Double getLocationX() {
		return locationX;
	}

	public void setLocationX(Double locationX) {
		this.locationX = locationX;
	}

	public Double getLocationY() {
		return locationY;
	}

	public void setLocationY(Double locationY) {
		this.locationY = locationY;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

}
