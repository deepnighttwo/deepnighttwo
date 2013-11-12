package com.ctrip.clog.util;

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
 * Date: 11/11/13
 * Time: 3:21 PM
 */
public class CLogHBaseConnHelper {

    private static Logger logger = LoggerFactory.getLogger(CLogHBaseConnHelper.class);


    public static final int TABLE_POOL_MAX_SIZE = 50;
    public static final String CONF_TABLE_POOL_MAX_SIZE = "hbase.table.pool.max.size";
    public static final String CONF_ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum";
    public static final String CONF_ZOOKEEPER_ZNODE = "zookeeper.znode.parent";
    public static final String CONF_TABLE_AUTO_FLUSH = "hbase.table.auto.flush";
    public static final String CONF_TABLE_THREADS_MAX = "hbase.htable.threads.max";
    public static final String CONF_TABLE_WRITER_BUFFER_SIZE = "hbase.client.write.buffer";
    public static final String CONF_SYNC_FLUSH_INTERVAL = "hbase.sync.flush.interval";
    public static final int DEFAULT_TABLE_THREADS_MAX = 500000;
    public static final int DEFAULT_TABLE_WRITER_BUFFER_SIZE = 1024 * 1024 * 6;


    private static HTableInterfaceFactory tableFactory = new HTableFactory();

    private static HTablePool hTablePool;

    public synchronized static HTablePool getHTablePool() {
        if (hTablePool == null) {
            try {
                String zkquorum = "192.168.81.176,192.168.81.177,192.168.81.178";
                String basePath = "/hbase";
                int threadMaxSize = 30;


                Configuration conf = new Configuration();
                conf.set(CONF_ZOOKEEPER_QUORUM, zkquorum);
                conf.set(CONF_ZOOKEEPER_ZNODE, basePath);
                int maxSize = TABLE_POOL_MAX_SIZE;
                int threadMax = DEFAULT_TABLE_THREADS_MAX;
                conf.setInt(CONF_TABLE_THREADS_MAX, threadMax);

                hTablePool = new HTablePool(conf, maxSize, tableFactory, PoolMap.PoolType.ThreadLocal);


            } catch (Throwable e) {
                try {
                    hTablePool.close();
                } catch (IOException e2) {
                    logger.error("Failed to close hbase table pool", e2);
                }
                logger.error("Failed to initialize hbase table pool", e);
                throw new RuntimeException("Initialization failed", e);
            }
        }
        return hTablePool;
    }

}
