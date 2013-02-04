package com.amazon.mzang.tools;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WeekCountB0042VJG3K {

    private static double hc = 0.7665 + 0.01918;
    private static double revnew = 19.160000 - 10;

    public static void main(String[] args) throws IOException {
        normal();
        logl();
        logl();
        markdown();
    }

    private static void normal() throws IOException {
        List<String> lines = FileUtil.readFileAsLines("weekforecastB0042VJG3K.txt");
        Map<Integer, Double> forecast = new LinkedHashMap<Integer, Double>();
        for (String line : lines) {
            String[] weeks = line.split("\t");
            for (String week : weeks) {
                week = week.trim();
                String[] f = week.split(":");
                Integer w = Integer.valueOf(f[0].trim().substring(5));
                Double wf = Double.valueOf(f[1].trim());
                forecast.put(w, wf);
            }
        }
        System.out.println(forecast);
        double total = 224;
        double tm = 0;
        log("Holding Cost per Item Per week=" + hc);
        logl();
        log("Money Get from normal Sale per item=" + revnew);
        logl();
        log("Week,Forecast,Total Inventory,Money Got This Week,Money Got Per Item,Total Money\r\n");
        for (int i = 0; i <= 51; i++) {
            if (total < 0) {
                break;
            }
            log(i + ",");
            double s = forecast.get(i);

            log(s + ",");
            log(total + ",");
            if (total < s) {
                s = total;
                total = -10000000;
            } else {
                total -= s;
            }
            tm += (revnew - hc * i) * s;
            log((revnew - hc * i) * s + ",");
            log((revnew - hc * i) + ",");
            log(tm + ",");
            logl();
        }

    }

    private static void markdown() throws IOException {
        List<String> lines = FileUtil.readFileAsLines("weekforecastB0042VJG3K.txt");
        Map<Integer, Double> forecast = new LinkedHashMap<Integer, Double>();
        for (String line : lines) {
            String[] weeks = line.split("\t");
            for (String week : weeks) {
                week = week.trim();
                String[] f = week.split(":");
                Integer w = Integer.valueOf(f[0].trim().substring(5));
                Double wf = Double.valueOf(f[1].trim());
                forecast.put(w, wf);
            }
        }
        System.out.println(forecast);
        double revnewMkd = 10.895307 - 8.6722892;
        double mkd = 92;
        double total = 168 - mkd;
        double mkdiii = 1 + 1.4466990;
        double tm = 0;
        log("Week,Forecast,Total Inventory,Markdown Inventory,Money Got This Week,Money Got Per Item,Total Money\r\n");
        for (int i = 0; i <= 51; i++) {
            double rrr = 0;
            if (total < 0) {
                break;
            }
            log(i + ",");
            double s = forecast.get(i);
            if (mkd > 0) {
                s = s * mkdiii;
                rrr = revnewMkd;
                mkd -= s;
            } else if (total < s) {
                s = total;
                total = -10000000;
            } else {
                rrr = revnew;
                total -= s;
            }

            log(s + ",");
            log(total + ",");
            log(mkd > 0 ? mkd : 0 + ",");

            tm += (rrr - hc * i) * s;
            log((rrr - hc * i) * s + ",");
            log((rrr - hc * i) + ",");
            log(tm + ",");

            logl();
        }

    }

    private static void log(Object obj) {
        System.out.print(obj);
    }

    private static void logl() {
        System.out.println();
    }
}
