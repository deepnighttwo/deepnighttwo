package com.amazon.mzang.tools;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangeCNCost {

    static Map<String, FCDetails> dbfc = new HashMap<String, FCDetails>();
    static Map<String, FCDetails> filefc = new HashMap<String, FCDetails>();

    /**
     * @param args
     * @throws SQLException
     * @throws IOException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws SecurityException
     */
    public static void main(String[] args) throws SQLException, IOException, SecurityException,
            IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
        buildDBFC();
        buildFileFC();
        System.out.println("Warehouse ID," + "Orig Receipt Cost,New Receipt Cost,Change,"
                + "Orig Remove Cost, New Remove Cost,Change," + "Orig Transin Cost,New Transin Cost,Change,"
                + "Orig Transout Cost,New Transout Cost,Change," + "Orig Holding Cost,new Holding Cost, Change");

        for (String key : filefc.keySet()) {
            FCDetails orig = dbfc.get(key);
            if (orig == null) {
                System.out.println(key + " is not found");
                continue;
            }
            orig.compareFCDetailsExcel(filefc.get(key));

        }
    }

    static void buildFileFC() throws IOException {
        List<String[]> rows = FileUtil.readFileAsTable("changecost.txt", "\t");
        for (String[] row : rows) {
            FCDetails fc = new FCDetails();
            fc.warehousegl = "Warehouse " + row[7].trim() + "- GL " + row[0];
            fc.receiptCost = getDouble(row[5].trim());
            fc.removeCost = getDouble(row[6].trim());
            fc.transInCost = getDouble(row[3].trim());
            fc.transOutCost = getDouble(row[4].trim());
            fc.holdingCost = getDouble(row.length > 10 ? row[9].trim() : "-100");
            filefc.put(fc.warehousegl, fc);
        }
    }

    static double getDouble(String str) {
        try {
            return Double.valueOf(str);
        } catch (Throwable th) {
            return -100;
        }
    }

    static void buildDBFC() throws SQLException {
        ResultSet rs = MySQLUtil.executeSQL(MySQLUtil.CN_DB_Serv, "vendor_flex", "select * from FC_GL_COST");
        while (rs.next()) {
            FCDetails fc = new FCDetails();
            fc.warehousegl = "Warehouse " + rs.getString(2) + "- GL " + rs.getInt(3);
            fc.receiptCost = rs.getDouble(5);
            fc.removeCost = rs.getDouble(6);
            fc.transInCost = rs.getDouble(8);
            fc.transOutCost = rs.getDouble(9);
            fc.holdingCost = rs.getDouble(7);
            dbfc.put(fc.warehousegl, fc);
        }
    }
}

class FCDetails {
    String warehousegl;
    double receiptCost;
    double removeCost;
    double transInCost;
    double transOutCost;
    double holdingCost;

    void compareFCDetailsExcel(FCDetails fc) throws SecurityException, IllegalArgumentException, NoSuchFieldException,
            IllegalAccessException {
        lognl(fc.warehousegl + ",");
        changeDetailExcel("receiptCost", fc);
        changeDetailExcel("removeCost", fc);
        changeDetailExcel("transInCost", fc);
        changeDetailExcel("transOutCost", fc);
        changeDetailExcel("holdingCost", fc);
        log("");
    }

    void changeDetailExcel(String propName, FCDetails fc) throws SecurityException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException {
        Field filed = FCDetails.class.getDeclaredField(propName);
        filed.setAccessible(true);
        double origVal = (Double) (filed.get(this));
        double newVal = (Double) (filed.get(fc));
        if (origVal > 0) {
            lognl(origVal + ",");
        } else {
            lognl("N/A,");
        }
        if (newVal > 0) {
            lognl(newVal + ",");
        } else {
            lognl("N/A,");
        }
        if (newVal > 0 && origVal > 0) {
            double percent = (newVal - origVal) * 100 / origVal;
            DecimalFormat d = new DecimalFormat("2.2");
            String pct = d.format(percent) + "%";
            lognl(pct + ",");
        } else {
            lognl("N/A,");
        }

    }

    void compareFCDetails(FCDetails fc) throws SecurityException, IllegalArgumentException, NoSuchFieldException,
            IllegalAccessException {
        log("Details Change of FC:" + fc.warehousegl);
        log(changeDetail("Receipt Cost", "receiptCost", fc));
        log(changeDetail("Remove Cost", "removeCost", fc));
        log(changeDetail("Transin Cost", "transInCost", fc));
        log(changeDetail("Transout Cost", "transOutCost", fc));
        log(changeDetail("Holding Cost", "holdingCost", fc));
        log("");
    }

    String changeDetail(String displayName, String propName, FCDetails fc) throws SecurityException,
            NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field filed = FCDetails.class.getDeclaredField(propName);
        filed.setAccessible(true);
        double origVal = (Double) (filed.get(this));
        double newVal = (Double) (filed.get(fc));
        String outputFmt = "Compare of %s: Original :%s, New: %s, Change:%s";
        String pct = "N/A";
        if (newVal > 0 && origVal > 0) {
            double percent = (newVal - origVal) * 100 / origVal;
            DecimalFormat d = new DecimalFormat("2.2");
            pct = d.format(percent) + "%";
        }

        return String.format(outputFmt, displayName, origVal, newVal, pct);
    }

    void log(Object obj) {
        System.out.println(obj);
    }

    void lognl(Object obj) {
        System.out.print(obj);
    }

}
