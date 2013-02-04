package com.amazon.mzang.tools.old;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SpliteIOGGL {

    // private static Set<String> getPortalGLs(String org) throws SQLException {
    // ResultSet rs = MySQLUtil.executeSQL(MySQLUtil.US_DB_Serv, "workflow_db",
    // "select distinct gl from  buyer where org='" + org + "'order by gl");
    // Set<String> gls = new HashSet<String>();
    // while (rs.next()) {
    // gls.add(rs.getString(1).trim());
    // }
    // return gls;
    // }

    public static void main(String[] args) throws IOException, SQLException {
        Map<String, String[]> map = GLUtils.getAllGLKeyGL();
        List<String> usNewGL = Arrays.asList(new String[] {
                "0", "14", "15", "20", "21", "22", "23", "27", "44", "50", "53", "60", "63", "65", "74", "75", "79",
                "86", "107", "111", "114", "121", "123", "125", "129", "136", "147", "153", "158", "160", "171", "180",
                "193", "194", "195", "196", "197", "198", "199", "200 ", "201", "219", "226", "228", "229", "234",
                "236", "241", "246", "251", "256", "258", "259", "260", "261", "262", "263", "264", "265", "266",
                "267", "279", "293", "297", "298", "304", "307", "309", "311", "313", "316", "318", "325", "327",
                "328", "334", "336", "340", "349", "350", "351", "353", "354", "355", "356", "360", "362", "364",
                "366", "367", "370", "392", "394", "395", "396", "397", "400", "402", "405", "406", "407", "408",
                "409", "410", "411", "421", "422"
        });

        // List<String> usedGLs = Arrays.asList(new String[] {
        // "15", "74", "325", "309", "14", "199", "60", "121", "364", "193", "107", "86", "79", "65", "75", "328",
        // "197", "198", "0", "236", "265", "259", "340", "27", "351", "160", "196", "349", "307", "228", "353",
        // "129", "293", "396", "251", "318", "111", "370", "425", "424", "153", "360", "195", "21", "147", "229",
        // "200", "201", "23", "421", "422", "63", "194", "267", "263", "241", "400"
        // });

        List<String> nocarton = Arrays.asList(new String[] {

        });
        log("GL,GL Name,Which Service,Carton Quanitity,Status\r\n");
        int correct = 0, duplicated = 0, missing = 0;
        // int correctUsed = 0, duplicatedUsed = 0, missingUsed = 0;
        for (String gl : map.keySet()) {
            String[] row = map.get(gl);
            log(gl);
            // log(usedGLs.contains(gl) ? ",Y" : ",N");
            log("," + row[1].trim());
            if (row.length >= 3 && row[2].trim().trim().length() > 0) {
                log("(\"" + row[2].trim() + "\")");
            }
            boolean latest = true;
            boolean cq = true;
            if (usNewGL.contains(gl)) {
                log(",Latest");
            } else {
                log(",Legacy");
                latest = false;
            }
            if (nocarton.contains(gl)) {
                cq = false;
                log(",N");
            } else {
                log(",Y");
            }

            if (latest == true && cq == true) {
                log(",Duplicated");
            } else if (latest == false && cq == false) {
                log(",Missing");
            } else {
                log(",Correct");
            }

            // if (usedGLs.contains(gl) == true) {
            if (latest == true && cq == true) {
                duplicated++;
            } else if (latest == false && cq == false) {
                missing++;
            } else {
                correct++;
            }
            // } else {
            // if (latest == true && cq == true) {
            // duplicatedUsed++;
            // } else if (latest == false && cq == false) {
            // missingUsed++;
            // } else {
            // correctUsed++;
            // }
            // }
            log("\r\n");
        }

        log("\r\n\r\nAll GL Summary\r\n");
        int total = correct + missing + duplicated;
        log(String.format("Correct: " + correct + "(%2.2f%%)\r\n", correct * 100.0 / total));
        log(String.format("Duplicated: " + duplicated + "(%2.2f%%)\r\n", duplicated * 100.0 / total));
        log(String.format("Missing: " + missing + "(" + "(%2.2f%%)\r\n", missing * 100.0 / total));

        // log("\r\nUsed GL Summary\r\n");
        // int totalUsed = correctUsed + missingUsed + duplicatedUsed;
        // log(String.format("Correct: " + correct + "(%2.2f%%)\r\n", correctUsed * 100.0 / totalUsed));
        // log(String.format("Duplicated: " + duplicated + "(%2.2f%%)\r\n", duplicatedUsed * 100.0 / totalUsed));
        // log(String.format("Missing: " + missing + "(" + "(%2.2f%%)\r\n", missingUsed * 100.0 / totalUsed));
    }

    private static void log(Object obj) {
        System.out.print(obj);
    }
}
