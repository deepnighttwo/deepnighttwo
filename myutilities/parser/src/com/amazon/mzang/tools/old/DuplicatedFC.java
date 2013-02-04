package com.amazon.mzang.tools.old;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.amazon.mzang.tools.MySQLUtil;

public class DuplicatedFC {
    public static void main(String[] args) throws SQLException {
        ResultSet fcs = MySQLUtil
                .executeSQL(
                        MySQLUtil.EU_DB_Serv,
                        "vendor_flex",
                        "select * from VENDOR_FCS  where warehouse_id in (select warehouse_id from VENDOR_FCS group by warehouse_id having  count(warehouse_id) > 1) order by warehouse_id");
        String warehouseIdPre = "";
        System.out.println("Realm,Warehouse id,IOG,Currency,Holding Cost,Status");
        int count = 0;
        String preLine = null;
        while (fcs.next()) {
            String realm = fcs.getString(21);
            String warehouseId = fcs.getString(2);
            String iog = fcs.getString(4);
            String holdingCostCurrency = fcs.getString(8);
            String holdingCost = fcs.getString(11);
            String status = fcs.getString(19);
            if (warehouseIdPre.equals(warehouseId) == false) {
                System.out.println();
                warehouseIdPre = warehouseId;
            }
            System.out.println(realm + "," + warehouseId + "," + iog + "," + holdingCostCurrency + "," + holdingCost
                    + "," + status);
        }
    }
}
