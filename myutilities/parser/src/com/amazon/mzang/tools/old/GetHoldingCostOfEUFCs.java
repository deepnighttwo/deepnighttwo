package com.amazon.mzang.tools.old;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.amazon.mzang.tools.MySQLUtil;

public class GetHoldingCostOfEUFCs {

	public static void main(String[] args) throws SQLException {
		ResultSet rs = MySQLUtil
				.executeSQL(
						MySQLUtil.EU_DB_Serv,
						"vendor_flex",
						"select realm,is_valid_fc, warehouse_id, holdingcost_est_currency,holdingcost_est from VENDOR_FCS group by warehouse_id order by realm;");
		String realm = "";
		String isValid = "";
		String whId = "";
		String currency = "";
		String holdingCost = "";
		while (rs.next()) {
			String realmN = rs.getString(1);
			isValid = rs.getString(2);
			whId = rs.getString(3);
			currency = rs.getString(4);
			holdingCost = rs.getString(5);
			if (realmN.equalsIgnoreCase(realm) == false) {
				log("");
				realm = realmN;
			}
			logL(realm);
			logL(isValid);
			logL(whId);
			logL(currency);
			logL(holdingCost);
			log("");
		}
	}

	private static void log(Object obj) {
		System.out.println(obj.toString());
	}

	private static void logL(Object obj) {
		if (obj == null) {
			obj = "N/A";
		}
		System.out.print(obj.toString() + ",");
	}

}
