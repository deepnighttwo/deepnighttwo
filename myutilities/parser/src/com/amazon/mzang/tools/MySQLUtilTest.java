package com.amazon.mzang.tools;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLUtilTest {

	public static void main(String[] args) throws SQLException {
		ResultSet rs = MySQLUtil
				.executeSQL(
						MySQLUtil.EU_DB_Serv,
						"vendor_flex",
						"select warehouse_id, holdingcost_est_currency,holdingcost_est   from VENDOR_FCS group by warehouse_id order by realm;");
		while (rs.next()) {
			System.out.println(rs.getString(1));
		}
	}

}
