package com.amazon.mzang.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class MySQLUtil {

    public static final String US_DB_Serv = "jdbc:mysql://oih-mysql-na.db.amazon.com/";
    public static final String JP_DB_Serv = "jdbc:mysql://oih-mysql-fe.db.amazon.com/";
    public static final String CN_DB_Serv = "jdbc:mysql://oih-mysql-cn.db.amazon.com/";
    public static final String EU_DB_Serv = "jdbc:mysql://oih-mysql-eu.db.amazon.com/";

    private static Connection conn = null;

    public static ResultSet executeSQL(String dbServ, String db, String sql) {
        String driver = "com.mysql.jdbc.Driver";

        String url = dbServ + db;

        String user = "oihadmin";

        String password = "";

        try {

            Class.forName(driver);

            if (conn == null)
                conn = DriverManager.getConnection(url, user, password);

            // if (!conn.isClosed())
            // System.out.println("Succeeded connecting to the Database!");

            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static ResultSet executeSQLUsingIOG(int iog, String db, String sql) {
        String dbServ = iog2SqlSvrUrl.get(iog);
        if (dbServ == null) {
            dbServ = EU_DB_Serv;
        }
        return executeSQL(dbServ, db, sql);
    }

    private static Map<Integer, String> iog2SqlSvrUrl = new HashMap<Integer, String>();

    static {
        iog2SqlSvrUrl.put(1, US_DB_Serv);
        iog2SqlSvrUrl.put(2, US_DB_Serv);
        iog2SqlSvrUrl.put(101, US_DB_Serv);

        iog2SqlSvrUrl.put(11, US_DB_Serv);
        iog2SqlSvrUrl.put(77, US_DB_Serv);

        iog2SqlSvrUrl.put(10, JP_DB_Serv);
        iog2SqlSvrUrl.put(33, JP_DB_Serv);

        iog2SqlSvrUrl.put(70, CN_DB_Serv);
        iog2SqlSvrUrl.put(71, CN_DB_Serv);
    }

}
