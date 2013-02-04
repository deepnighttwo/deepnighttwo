/**
 * This source code belongs to Moon Zang, the author.
 * To use it for commercial/business purpose, please contact DeepNightTwo@gmail.com
 * @author  Moon Zang
 * 
 */

package demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DemoMain {
    private final static String SOURCE_TABLE_NAME = "sourcetest";
    private final static String TARGET_TABLE_NAME = "targettest";
    private final static String TABLE_MARK = "$TABLENAME$";
    private final static String TABLE_SCHAME = "create table " + TABLE_MARK
            + " (id serial, " + "bcol boolean, icol integer, lcol bigint, "
            + "fcol float, dcol double, "
            + "ccol char(100), vccol varchar(200), txtcol text, "
            + "tscol timestamp " + ");";
    private final static String INSERT_STATEMENT = "insert into " + TABLE_MARK
            + " (bcol, icol, lcol, fcol , dcol, ccol, vccol, txtcol, tscol)"
            + " values (?,?,?,?,?,?,?,?,?)";

    private static Connection conn;
    static {
        try {
            conn = DemoContext.getSourceConnection();
            PreparedStatement p = conn.prepareStatement("");
            p.addBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        cleanDemoEnv();
        initDemoEnv(500000);
        readData();
    }

    private static void readData() {
        try {
            Connection pconn = DemoContext.getSourceConnection();
            pconn.setAutoCommit(false);
            Statement stmt = pconn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from "
                    + SOURCE_TABLE_NAME);
            int i = 0;
            long start = System.currentTimeMillis();
            while (rs.next()) {
                rs.getInt(1);
                rs.getBoolean(2);
                rs.getInt(3);
                rs.getLong(4);
                rs.getFloat(5);
                rs.getDouble(6);
                rs.getString(7);
                rs.getString(8);
                rs.getString(9);
                rs.getTimestamp(10);
                i++;
            }
            long end = System.currentTimeMillis();
            System.out.println(end - start + "\t" + i + "rows");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void initDemoEnv(int lineCount) {
        prepareSourceTable();
        prepareTargetTable();
        prepareSourceData(lineCount);
    }

    public static void cleanDemoEnv() {
        removeSourceTable();
        removeTargetTable();
    }

    private static void prepareSourceTable() {
        log("Creating source table...");
        String sql = TABLE_SCHAME.replace(TABLE_MARK, SOURCE_TABLE_NAME);
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log("Source table created!");
    }

    private static void removeSourceTable() {
        log("Dropping source table...");
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("drop table " + SOURCE_TABLE_NAME);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log("Source table dropped!");
    }

    static class Insertor implements Runnable {
        private int start, end;

        public Insertor(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            try {
                Connection pconn = DemoContext.getSourceConnection();
                pconn.setAutoCommit(false);
                PreparedStatement prepStmt = pconn
                        .prepareStatement(INSERT_STATEMENT.replace(TABLE_MARK,
                                SOURCE_TABLE_NAME));
                // Timestamp dt = DataGenerator.getTimestampValue();
                for (int i = start; i < end; i++) {
                    prepStmt.setBoolean(1, DataGenerator.getBooleanValue());
                    prepStmt.setInt(2, DataGenerator.getIntegerValue());
                    prepStmt.setLong(3, DataGenerator.getLongValue());
                    prepStmt.setFloat(4, DataGenerator.getFloatValue());
                    prepStmt.setDouble(5, DataGenerator.getDoubleValue());
                    prepStmt.setString(6, DataGenerator.getStringValue(100));
                    prepStmt.setString(7, DataGenerator.getStringValue(200));
                    prepStmt.setString(8, DataGenerator.getStringValue(500));
                    prepStmt.setTimestamp(9, DataGenerator.getTimestampValue());

                    prepStmt.addBatch();
                    if (i % 2000 == 0) {
                        prepStmt.executeBatch();
                    }
                }
                prepStmt.executeBatch();
                prepStmt.close();
                pconn.commit();
                pconn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void prepareSourceData(int lineCount) {
        log("Prepare source table data...");
        long startt = System.currentTimeMillis();
        int threadUnit = 100000;
        int threadCount = lineCount / threadUnit
                + ((lineCount % threadUnit) == 0 ? 0 : 1);
        Thread[] workers = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            int start = i * threadUnit;
            int end = Math.min(i * threadUnit + threadUnit, lineCount);
            workers[i] = new Thread(new Insertor(start, end));
            workers[i].start();
        }
        int finished = 0;
        while (finished != threadCount) {
            finished = 0;
            for (int i = 0; i < threadCount; i++) {
                if (workers[i] != null
                        && workers[i].getState() == Thread.State.TERMINATED) {
                    workers[i] = null;
                }
                if (workers[i] == null) {
                    finished++;
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        log("Duration:" + (end - startt) / 1000.0);
        log("Source data done!");
    }

    public static void prepareTargetTable() {
        log("Prepare target table...");

        String sql = TABLE_SCHAME.replace(TABLE_MARK, TARGET_TABLE_NAME);
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log("Target table created!");
    }

    public static void removeTargetTable() {
        log("Creating target table...");
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("drop table " + TARGET_TABLE_NAME);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log("Target table dropped!");
    }

    private static void log(String msg) {
        System.out.println(msg);
    }

}