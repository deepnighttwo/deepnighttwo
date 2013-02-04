package com.amazon.mzang.tools.gls;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.amazon.mzang.tools.MySQLUtil;

public class GLs {

	public static void main(String[] args) throws SQLException {
		System.out.println("CA:");

		ResultSet rs = MySQLUtil
				.executeSQLUsingIOG(11, "variation",
						"select distinct gl from unhealthy_asin where iog=11 or iog=77");
		while (rs.next()) {
			System.out.print(rs.getString(1) + ",");
		}

		System.out.println("CN:");

		rs = MySQLUtil
				.executeSQLUsingIOG(70, "variation",
						"select distinct gl from unhealthy_asin where iog=70 or iog=71");
		while (rs.next()) {
			System.out.print(rs.getString(1) + ",");
		}

		System.out.println("FR:");
		rs = MySQLUtil.executeSQLUsingIOG(9, "variation",
				"select distinct gl from unhealthy_asin where iog=9 or iog=32");
		while (rs.next()) {
			System.out.print(rs.getString(1) + ",");
		}
		
		System.out.println("ES:");
		rs = MySQLUtil.executeSQLUsingIOG(9, "variation",
				"select distinct gl from unhealthy_asin where iog=85 or iog=96");
		while (rs.next()) {
			System.out.print(rs.getString(1) + ",");
		}
		
		System.out.println("IT:");
		rs = MySQLUtil.executeSQLUsingIOG(9, "variation",
				"select distinct gl from unhealthy_asin where iog=75 or iog=76");
		while (rs.next()) {
			System.out.print(rs.getString(1) + ",");
		}

	}

}
