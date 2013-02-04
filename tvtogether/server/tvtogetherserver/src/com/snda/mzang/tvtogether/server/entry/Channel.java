package com.snda.mzang.tvtogether.server.entry;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.snda.mzang.tvtogether.base.B;

public class Channel implements IFieldMapper {

	public static Map<String, String> FIELD_MAP;

	static {
		Map<String, String> fieldMap = new HashMap<String, String>();
		fieldMap.put("id", B.channelId);

		FIELD_MAP = Collections.unmodifiableMap(fieldMap);
	}

	@Override
	public Map<String, String> getFieldMap() {
		return FIELD_MAP;
	}

	public static enum ChannelStatus {
		ENABLE("enable"), DISABLE("disable");

		private String value;

		ChannelStatus(String value) {
			this.value = value;
		}

		public String getStatus() {
			return value;
		}

		public ChannelStatus getStatus(String value) {
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
	String name;
	String comments;
	String image;
	String status;
	Date updateTime;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
