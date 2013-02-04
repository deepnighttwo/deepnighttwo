package com.amazon.mzang.tools.old;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.amazon.mzang.tools.MySQLUtil;

public class FindErrorGLConfig {

	public static void main(String... args) throws SQLException {
		ResultSet filters = MySQLUtil.executeSQL(MySQLUtil.US_DB_Serv,
				"workflow_db", "select * from filter_values");

		while (filters.next()) {
			String buyerFilterId = filters.getString(1);
			String keyName = filters.getString(2);
			String value = filters.getString(3);
			if ("iog".equalsIgnoreCase(keyName)) {
				try {
					Integer.valueOf(value.trim());
				} catch (Exception ex) {
					System.out.println("\"" + buyerFilterId + "\",");
				}
			}
		}

	}

}
