package com.test.redis;

import java.text.DecimalFormat;

import redis.clients.jedis.Jedis;

public class AppMain {

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

        jedis.set("testdelete", "vvv");

        jedis.del("*");

        jedis.flushAll();

        jedis.flushDB();

        log("Test delete all:" + jedis.get("testdelete"));

        int threadCount = Integer.parseInt(System.getenv("threadCount"));

        log("threadCount=" + threadCount);

        final char areaStart = 'A';
        for (int i = 0; i < threadCount; i++) {
            final char chuse = (char) (areaStart + i);
            new Thread() {
                public void run() {
                    doit((char) (chuse));
                }
            }.start();
        }

    }

    static void doit(char area) {

        String host = System.getenv("redishost");

        if (host == null || host.trim().length() == 0) {
            host = "127.0.0.1";
        }

        log("Using redis host " + host);

        Jedis jedis = new Jedis(host);

        int colCount = Integer.parseInt(System.getenv("colCount"));

        log("ColumnCount=" + colCount);

        int asinCount = Integer.parseInt(System.getenv("asinCount"));

        log("asinCount=" + asinCount);

        byte[][] cols = new byte[colCount][0];

        for (int i = 0; i < colCount; i++) {
            cols[i] = ("tip" + i).getBytes();
        }

        jedis.set("runId", "20121209");

        int perfSize = asinCount * 10000;

        String iog = "1";

        long start = System.currentTimeMillis();
        log("::::::::::::Base Test::::::::::::::::");
        DecimalFormat df = new DecimalFormat(area + "000000000");
        for (int i = 0; i < perfSize; i++) {
            df.format(i);
            for (int j = 0; j < colCount; j++) {
                String.valueOf((int) (Math.random() * 10000)).getBytes();
            }
        }
        long used = (System.currentTimeMillis() - start);
        log("Time Used=" + used + "ms for " + perfSize + ". each used:" + (used * 1.0 / perfSize));

        start = System.nanoTime();
        log("::::::::::::Redis Perf Test::::::::::::::::");
        for (int i = 0; i < perfSize; i++) {
            String asin = df.format(i);

            for (int j = 0; j < colCount; j++) {
                jedis.hset((asin + ":" + iog).getBytes(), cols[j], String.valueOf((int) (Math.random() * 10000))
                        .getBytes());
            }

            if (i % 10000 == 0) {
                used = (System.nanoTime() - start);
                log("Time Used=" + used + "ns for " + i + ". each used:" + (used * 1.0 / i));
            }
        }
        used = (System.nanoTime() - start);
        log("Final Time Used=" + used + "ns for " + perfSize + ". each used:" + (used * 1.0 / perfSize));

    }

    static void log(Object obj) {
        System.out.println(obj);
    }
}
