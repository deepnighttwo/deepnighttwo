package com.deepnighttwo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

/**
 * User: mzang
 * Date: 2014-03-31
 * Time: 15:40
 */
public class SLF4JWithLog4j1WriteLog {


    private static Logger logger = LoggerFactory.getLogger(SLF4JWithLog4j1WriteLog.class);

    public static void main(String... args) {
        SLF4JWithLog4j1WriteLog writer = new SLF4JWithLog4j1WriteLog();
        writer.writeLog();
    }

    public void writeLog() {
        logger.info(MarkerFactory.getMarker("ffffffffff"), "This is Log.");
    }
}
