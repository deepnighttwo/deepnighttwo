package com.test.redis;

import java.text.DecimalFormat;

import redis.clients.jedis.Jedis;

public class AppMainRead {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String host = System.getenv("redishost");

        if (host == null || host.trim().length() == 0) {
            host = "127.0.0.1";
        }

        log("Using redis host " + host);

        Jedis jedis = new Jedis(host);

        DecimalFormat df = new DecimalFormat("B000000000");

        String iog = "1";

        int colCount = Integer.parseInt(System.getenv("colCount"));

        log("ColumnCount=" + colCount);

        int asinCount = Integer.parseInt(System.getenv("asinCount"));

        log("asinCount=" + asinCount);

        int perfSize = asinCount * 10000;
        long start = System.nanoTime();
        long used;
        log("::::::::::::Redis Read Perf Test::::::::::::::::");
        for (int i = 0; i < perfSize; i++) {
            String asin = df.format(i);
            if (jedis.hgetAll((asin + ":" + iog).getBytes()).size() != colCount) {
                log("not tip & carton " + asin);
            }

            if (i % 100000 == 0) {
                used = (System.nanoTime() - start);
                log("Time Used=" + used + "ns for " + i + ". each used:" + (used * 1.0 / i));
            }
        }
        used = (System.nanoTime() - start);
        log("Time Used=" + used + "ns for " + perfSize + ". each used:" + (used * 1.0 / perfSize));

    }

    static void log(Object obj) {
        System.out.println(obj);
    }
}
