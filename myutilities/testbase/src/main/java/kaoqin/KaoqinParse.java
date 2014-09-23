package kaoqin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * User: mzang
 * Date: 2014-06-30
 * Time: 10:27
 */
public class KaoqinParse {

    static Date[][] se = new Date[][]{
            {stringDate("07:00"), stringDate("12:00")},
            {stringDate("13:00"), stringDate("19:00")},
            {stringDate("19:30"), stringDate("23:59")}
    };

    public static void main(String[] args) throws IOException, ParseException {
        readAndParse();

    }

    static void readAndParse() throws IOException, ParseException {
        int minutes = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(KaoqinParse.class
                .getResourceAsStream("/kaoqin.txt")));
        String line = null;
        int comeDays = 0;
        // 2014-07-25	9:17	18:30	弹性工时	弹性工时-按日	2	09:00-18:00

        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\t");
            if (parts.length != 7) {
                continue;
            }
            int min = getMinute(parts);
            if (min > 0) {
                minutes += min;
                comeDays++;
            }
        }

        System.out.println("来上班的天数:" + comeDays);
        System.out.println("按8小时算的工作天数" + minutes / 60.0 / 8);
        System.out.println("来上班的天数中，平均每天工作小时数" + minutes / 60.0 / comeDays);
        System.out.println("直到今天的工作天数:" + getDayOfWorkUntilToday());
        System.out.println("多出分钟数:" + (minutes - getDayOfWorkUntilToday() * 8 * 60));
        System.out.println("直到今天的平均每天工作小时数:" + minutes / 60.0 / getDayOfWorkUntilToday());
    }


    static int getDayOfWorkUntilToday() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int workDay = 0;
        while (true) {
            if (cal.get(Calendar.MONTH) != month || cal.get(Calendar.DAY_OF_MONTH) == dayOfMonth) {
                break;
            }
            int weekDay = cal.get(Calendar.DAY_OF_WEEK);
            if (weekDay != Calendar.SUNDAY && weekDay != Calendar.SATURDAY) {
                workDay++;
            }
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return workDay;
    }

    static int getMinute(String[] parts) throws ParseException {
        if (parts[1].trim().length() == 0 || parts[2].trim().length() == 0) {
            return 0;
        }
        Date start = stringDate(parts[1]);
        Date end = stringDate(parts[2]);

        int ret = 0;

        try {
            for (Date[] dates : se) {
                Date s = dates[0];
                Date e = dates[1];

                Date ps = start;
                Date pe = end;

                if (s.after(e) || pe.before(s)) {
                    continue;
                }

                if (ps.before(s)) {
                    ps = s;
                }

                if (pe.after(e)) {
                    pe = e;
                }
                ret += (pe.getTime() - ps.getTime()) / (60 * 1000);
            }

        } finally {
            System.out.println(parts[0] + "\t\t" + ret / 60.0);
        }

        return ret;

    }

    static Date stringDate(String dateString) {
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = fmt.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}
