package com.ctrip.clog.writer;

import com.ctrip.clog.domain.LogEntry;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTableFactory;
import org.apache.hadoop.hbase.client.HTableInterfaceFactory;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.util.PoolMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * User: mzang
 * Date: 11/8/13
 * Time: 5:37 PM
 */
public class CLogWriter {

    private static Logger logger = LoggerFactory.getLogger(CLogWriter.class);

    public static void storeLog(LogEntry logEntry) {

    }


}
