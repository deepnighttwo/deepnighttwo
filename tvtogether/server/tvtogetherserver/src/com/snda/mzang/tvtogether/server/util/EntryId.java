package com.snda.mzang.tvtogether.server.util;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public enum EntryId {

	USER("USER"), CHANNEL("CHANNEL"), SHOW("SHOW"), EPISODE("EPISODE");

	String prefix;

	EntryId(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * Use the random UUID to auto generate ID,
	 * 
	 * @return generated ID
	 */
	public String getUUID() {
		UUID uuid = UUID.randomUUID();
		return prefix + "-" + (uuid.toString());
	}

	public static void main(String... strings) {
		Set<Integer> lens = new HashSet<Integer>();
		for (int i = 0; i < 10000000; i++) {
			lens.add(EntryId.CHANNEL.getUUID().length());
		}
		System.out.println(lens);

	}
}
