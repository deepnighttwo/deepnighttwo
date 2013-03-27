package com.amazon.mzang.tools.updatecost;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.amazon.mzang.tools.MySQLUtil;

public class UpdateCost {

    /**
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {
        ResultSet rs = MySQLUtil.executeSQL(MySQLUtil.EU_DB_Serv, "vendor_flex",
                "select * from VENDOR_FCS as a where a.REALM='GBAmazon'");
        String[] gls = {
                "15", "74", "65", "63"
        };

        while (rs.next()) {
            String whId = rs.getString(1);
            String whName = rs.getString(2);
            for (String gl : gls) {
                String check = String
                        .format("select * from FC_GL_COST as a where  a.WAREHOUSE_ID='%s' and  a.WAREHOUSE_INDEX='%s' and a.gl=%s;",
                                whName, whId, gl);
                ResultSet ck = MySQLUtil.executeSQL(MySQLUtil.EU_DB_Serv, "vendor_flex", check);
                if (ck.next()) {
                    System.out
                            .println(String
                                    .format("update FC_GL_COST set TRANSPORTION_OUT_ESTIMATE=0.2 where WAREHOUSE_ID='%s' and  WAREHOUSE_INDEX='%s' and gl=%s;",
                                            whName, whId, gl));
                } else {
                    System.out
                            .println(String
                                    .format("insert into FC_GL_COST(id, WAREHOUSE_ID,GL,WAREHOUSE_INDEX,TRANSPORTION_OUT_ESTIMATE) values(contact('GLCOST',uuid()),'%s',%s,'%s',0.2);",
                                            whName, gl, whId));
                }
            }
        }

    }

}
