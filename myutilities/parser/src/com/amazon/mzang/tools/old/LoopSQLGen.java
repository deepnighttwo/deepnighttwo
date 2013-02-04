package com.amazon.mzang.tools.old;

public class LoopSQLGen {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		loopSQLGen();
	}

	private static void log(Object obj) {
		System.out.println(obj.toString());
	}

	// private static void lognc(Object obj) {
	// System.out.print(obj.toString());
	// }

	public static void loopSQLGen() {
		int[] gls = new int[] { 79, 86, 193, 200, 263, 325, 328, 422 };
		for (int gl : gls) {
			log("INSERT INTO blocked_automation_definition (id, gl, org, block_reason, start_date, end_date, last_rows_matched, where_clause, rule_position, block_reason_code, is_included)"
					+ " VALUES(uuid(), "
					+ gl
					+ ", 'USAmazon', 'block all return - default initialization setting', NULL, NULL, 0, '( removal_type = ''Return'' )', 0, 'BLOCK_RETURN', 0);");

			log("INSERT INTO blocked_automation_definition (id, gl, org, block_reason, start_date, end_date, last_rows_matched, where_clause, rule_position, block_reason_code, is_included)"
					+ " VALUES(uuid(), "
					+ gl
					+ ", 'USAmazon',  'block all liquidate - default initialization setting', NULL, NULL, 0, '( removal_type = ''Liquidate'' )', 0, 'BLOCK_LIQUIDATE', 0);");

			log("INSERT INTO blocked_automation_definition (id, gl, org, block_reason, start_date, end_date, last_rows_matched, where_clause, rule_position, block_reason_code, is_included)"
					+ " VALUES(uuid(), "
					+ gl
					+ ", 'USAmazon',  'block all markdown - default initialization setting', NULL, NULL, 0, '( removal_type = ''Markdown'' )', 0, 'BLOCK_MARKDOWN', 0);");
			log("");
		}

	}
}
